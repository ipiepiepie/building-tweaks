package xyz.ipiepiepie.tweaks.mixin.player;

import net.minecraft.client.Minecraft;
import net.minecraft.core.InventoryAction;
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
		TweaksManager.getInstance().setOffhandSlot(-1);
	}

	@Inject(method = "destroyCurrentEquippedItem", at = @At(value = "HEAD"), cancellable = true)
	private void toolRefillMixin(CallbackInfo ci) {
		ItemStack currentItem = self.inventory.getCurrentItem();
		int currentIndex = self.inventory.getCurrentItemIndex();

		// return if no refill is enabled
		if (!TweaksManager.getInstance().isRefillEnabled()) return;

		// do nothing if we not dealing with tool
		if (currentItem == null || !(currentItem.getItem() instanceof ItemTool)) return;

		// reset item in current hand
		self.inventory.setItem(currentIndex, null);

		// try to refill
		for (int slot = 0; slot < self.inventory.mainInventory.length; slot++) {
			ItemStack another = self.inventory.mainInventory[slot];

			// refill if we have similar itemstack
			if (another != null && slot != currentIndex && another.itemID == currentItem.itemID) {
				// swap items client-side
				self.swapItems(currentIndex, slot);

				// check if playing on server
				if (Minecraft.getMinecraft().isMultiplayerWorld()) {
					// get hotbar slot id if another itemstack is in hotbar
					if (slot < 9) slot = self.inventorySlots.getHotbarSlotId(slot + 1);
					// swap items server-side
					Minecraft.getMinecraft().playerController.handleInventoryMouseClick(self.inventorySlots.containerId, InventoryAction.HOTBAR_ITEM_SWAP, new int[]{slot, currentIndex + 1}, self);
				}

				break;
			}
		}

		ci.cancel();
	}

}
