package xyz.ipiepiepie.tweaks.mixin.player;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.ipiepiepie.tweaks.TweaksManager;

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

}
