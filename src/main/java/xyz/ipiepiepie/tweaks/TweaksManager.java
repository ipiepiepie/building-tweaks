package xyz.ipiepiepie.tweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;

public class TweaksManager {
	private static TweaksManager manager;
	// offhand //
	private int offhandSlot = -1;
	// refill //
	private boolean refillEnabled = false;


	public static TweaksManager getInstance() {
		if (manager == null) manager = new TweaksManager();

		return manager;
	}

	// OFFHAND //

	public boolean isOffhandEnabled() {
		return offhandSlot >= 0;
	}

	public int getOffhandSlot() {
		return offhandSlot;
	}

	public void setOffhandSlot(int slot) {
		this.offhandSlot = slot;
	}

	// REFILL //

	public boolean isRefillEnabled() {
		return refillEnabled;
	}

	public void setRefillEnabled(boolean enabled) {
		this.refillEnabled = enabled;
	}

	/**
	 * Refill current player's itemstack
	 * @param player player
	 * @param stack stack to refill
	 */
	public void refill(Player player, ItemStack stack) {
		int currentIndex = player.inventory.getCurrentItemIndex();

		// can't do anything id current item locked or we don't need any refill
		if (player.inventory.currentItemLocked() || stack == null || stack.stackSize > 0) return;

		// don't refill if it's disabled
		if (!TweaksManager.getInstance().isRefillEnabled()) {
			// reset offhand if enabled
			if (TweaksManager.getInstance().isOffhandEnabled() && BuildingTweaksOptions.isResetOffhandOnEmpty().value && currentIndex == TweaksManager.getInstance().getOffhandSlot()) {
				TweaksManager.getInstance().setOffhandSlot(-1);
			}

			return;
		}

		// check if we are dealing with tool
		boolean isTool = stack.getItem() instanceof ItemTool;

		// try to refill
		for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
			ItemStack another = player.inventory.mainInventory[slot];

			// refill if we have similar itemstack
			if (another != null && slot != currentIndex && (isTool ? stack.itemID == another.itemID : another.isItemEqual(stack))) {
				// swap items client-side
				player.swapItems(currentIndex, slot);

				// check if playing on server
				if (Minecraft.getMinecraft().isMultiplayerWorld()) {
					// get hotbar slot id if another itemstack is in hotbar
					if (slot < 9) slot = player.inventorySlots.getHotbarSlotId(slot + 1);
					// swap items server-side
					Minecraft.getMinecraft().playerController.handleInventoryMouseClick(player.inventorySlots.containerId, InventoryAction.HOTBAR_ITEM_SWAP, new int[]{slot, currentIndex + 1}, player);
				}

				return;
			}
		}

		// reset offhand if enabled
		if (TweaksManager.getInstance().isOffhandEnabled() && BuildingTweaksOptions.isResetOffhandOnEmpty().value && currentIndex == TweaksManager.getInstance().getOffhandSlot()) {
			TweaksManager.getInstance().setOffhandSlot(-1);
		}
	}

}
