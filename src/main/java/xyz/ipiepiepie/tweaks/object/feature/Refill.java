package xyz.ipiepiepie.tweaks.object.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import xyz.ipiepiepie.tweaks.TweaksManager;
import xyz.ipiepiepie.tweaks.object.Feature;

/**
 * Items refill feature.
 *
 * @author pie
 */
public class Refill extends Feature {

	/**
	 * Refill {@link ItemStack}.
	 * @param stack stack to refill
	 * @return {@code true} if item refilled, otherwise {@code false}
	 */
	public boolean refill(ItemStack stack) {
		Player player = Minecraft.getMinecraft().thePlayer;
		int currentIndex = player.inventory.getCurrentItemIndex();

		// check if we are dealing with tool
		boolean isTool = stack.getItem() instanceof ItemTool;

		// try to refill
		for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
			ItemStack another = player.inventory.mainInventory[slot];

			// refill if we have similar itemstack
			if (another != null && slot != currentIndex && (isTool ? stack.itemID == another.itemID : another.isItemEqual(stack))) {
				// swap items in slots
				TweaksManager.swapSlots(currentIndex, slot);

				return true;
			}
		}

		return false;
	}

}
