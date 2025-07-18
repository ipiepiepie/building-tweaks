package xyz.ipiepiepie.tweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.Player;
import xyz.ipiepiepie.tweaks.object.feature.AutoTool;
import xyz.ipiepiepie.tweaks.object.feature.Offhand;
import xyz.ipiepiepie.tweaks.object.feature.Refill;
import xyz.ipiepiepie.tweaks.object.feature.ShiftLock;

public class TweaksManager {
	// offhand //
	private static final Offhand offhand = new Offhand();
	// refill //
	private static final Refill refill = new Refill();
	// shift lock //
	private static final ShiftLock shiftLock = new ShiftLock();
	// auto-tool //
	private static final AutoTool autoTool = new AutoTool();

	// OFFHAND //

	public static Offhand getOffhand() {
		return offhand;
	}

	// REFILL //

	public static Refill getRefill() {
		return refill;
	}

	// SHIFT LOCK //

	public static ShiftLock getShiftLock() {
		return shiftLock;
	}

	// AUTO-TOOL //

	public static AutoTool getAutoTool() {
		return autoTool;
	}

	// UTIL //

	public static void swapSlots(int first, int second) {
		Player player = Minecraft.getMinecraft().thePlayer;
		// swap items client-side
		player.swapItems(first, second);

		// check if playing on server
		if (Minecraft.getMinecraft().isMultiplayerWorld()) {
			// get hotbar slot id if another itemstack is in hotbar
			if (second < 9) second = player.inventorySlots.getHotbarSlotId(second + 1);
			// swap items server-side
			Minecraft.getMinecraft().playerController.handleInventoryMouseClick(player.inventorySlots.containerId, InventoryAction.HOTBAR_ITEM_SWAP, new int[]{second, first + 1}, player);
		}
	}

}
