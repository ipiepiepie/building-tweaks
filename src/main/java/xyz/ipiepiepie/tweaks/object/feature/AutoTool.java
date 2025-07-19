package xyz.ipiepiepie.tweaks.object.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.*;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import xyz.ipiepiepie.tweaks.mixin.player.ItemToolAccessor;
import xyz.ipiepiepie.tweaks.object.Feature;

/**
 * Auto tool feature.
 */
public class AutoTool extends Feature {

	public AutoTool() {
		super(new Icon("buildingtweaks:gui/autotool_icon", 17, 11));
	}

	/**
	 * Check if we should use auto-tool.
	 *
	 * @return {@code tool id} if suitable tool is found, otherwise {@code -1}
	 */
	public int tryToUse() {
		Player player = Minecraft.getMinecraft().thePlayer;
		World world = player.world;
		// raytrace //
		HitResult raytrace = Minecraft.getMinecraft().objectMouseOver;

		// where tf is your world man
		if (world == null) return -1;

		Block<?> block;
		if (raytrace != null && raytrace.hitType == HitResult.HitType.TILE && (block = world.getBlock(raytrace.x, raytrace.y, raytrace.z)) != null) {
			return getSuitableTool(player, block);
		}

		return -1;
	}

	private int getSuitableTool(Player player, Block<?> block) {
		boolean useSilk = block.getLogic() instanceof BlockLogicStone || block.getLogic() instanceof BlockLogicGlowStone || block.getLogic() instanceof BlockLogicGlass || block.getLogic() instanceof BlockLogicGrass || block.getLogic() instanceof BlockLogicMobSpawner;

		int bestSlot = -1;
		float bestEffectiveness = 1;
		// iterate over items in inventory
		for (int slot = 0; slot < player.inventory.mainInventory.length; slot++) {
			ItemStack itemStack = player.inventory.mainInventory[slot];

			// skip if it isn't tool
			if (itemStack == null || !(itemStack.getItem() instanceof ItemTool)) continue;

			// get item as tool
			ItemTool tool = (ItemTool) itemStack.getItem();

			// check if we have right tool
			if (block.hasTag(((ItemToolAccessor) tool).getTagEffectiveAgainst())) {
				// if we are search for silk touch and found one, return it
				if (useSilk && tool.isSilkTouch()) return slot;

				// store tool's effectiveness
				float effectiveness = tool.getStrVsBlock(null, block);
				// set current slot as candidate, if effectiveness is better
				if (effectiveness > bestEffectiveness) {
					bestEffectiveness = effectiveness;
					bestSlot = slot;
				}
			}
		}

		return bestSlot;
	}

}


