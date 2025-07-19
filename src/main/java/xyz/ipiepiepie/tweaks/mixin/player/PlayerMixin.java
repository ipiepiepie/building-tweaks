package xyz.ipiepiepie.tweaks.mixin.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocalMultiplayer;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.net.packet.PacketPlayerAction;
import net.minecraft.core.util.helper.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.ipiepiepie.tweaks.TweaksManager;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {
	@Unique
	private final Player self = (Player) (Object) this;

	@Inject(method = "setHotbarOffset", at = @At(value = "HEAD"))
	private void onHotbarOffset(int offset, CallbackInfo ci) {
		// reset offhand on changing hotbar
		TweaksManager.getOffhand().resetSlot();
	}

	@Inject(method = "destroyCurrentEquippedItem", at = @At(value = "HEAD"), cancellable = true)
	private void toolRefillMixin(CallbackInfo ci) {
		ItemStack currentItem = self.inventory.getCurrentItem();
		int currentIndex = self.inventory.getCurrentItemIndex();

		// return if no refill is enabled
		if (!TweaksManager.getRefill().isEnabled()) return;

		// do nothing if we not dealing with tool
		if (currentItem == null || !(currentItem.getItem() instanceof ItemTool)) return;

		// reset item in current hand
		self.inventory.setItem(currentIndex, null);

		// try to refill
		TweaksManager.getRefill().refill(currentItem);

		ci.cancel();
	}

	@Inject(method = "dropCurrentItem", at = @At(value = "HEAD"), cancellable = true)
	private void dropRefillMixin(boolean dropFullStack, CallbackInfo ci) {
		ItemStack itemstack = self.inventory.getCurrentItem();
		int currentIndex = self.inventory.getCurrentItemIndex();
		boolean refilled = false;

		// drop item
		if (!Minecraft.getMinecraft().isMultiplayerWorld())
			self.dropPlayerItemWithRandomChoice(self.inventory.removeItem(currentIndex, dropFullStack ? 64 : 1), false);
		else
			((PlayerLocalMultiplayer) self).sendQueue.addToSendQueue(new PacketPlayerAction(dropFullStack ? 5 : 4, 0, 0, 0, Side.NONE, 0.0, 0.0));

		// cancel, since we dropped items
		ci.cancel();

		// do nothing if we not deal with item or isn't run out of items in stack
		if (itemstack == null || (!dropFullStack && itemstack.stackSize > 1)) return;

		// refill item
		if (TweaksManager.getRefill().isEnabled() && BuildingTweaksOptions.refillDrops().value)
			refilled = TweaksManager.getRefill().refill(itemstack);

		// if refill failed, reset offhand
		if (!refilled && TweaksManager.getOffhand().isEnabled() && BuildingTweaksOptions.isResetOffhandOnEmpty().value && TweaksManager.getOffhand().getSlot() == currentIndex)
			TweaksManager.getOffhand().resetSlot();
	}

}
