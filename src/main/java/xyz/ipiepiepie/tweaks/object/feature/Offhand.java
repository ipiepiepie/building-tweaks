package xyz.ipiepiepie.tweaks.object.feature;

import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicBrazier;
import net.minecraft.core.block.BlockLogicFarmland;
import net.minecraft.core.block.BlockLogicGrass;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemFireStriker;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemSeeds;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.tool.ItemToolHoe;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;
import xyz.ipiepiepie.tweaks.object.Feature;

import java.util.Random;

/**
 * Offhand feature
 *
 * @author pie
 */
public class Offhand extends Feature {
	private final Random random = new Random();
	private int slot = -1;

	/**
	 * Check if we should use offhand.
	 *
	 * @return {@code true} if offhand should be used, otherwise {@code false}
	 */
	public boolean shouldUse() {
		Player player = Minecraft.getMinecraft().thePlayer;
		World world = player.world;
		// items //
		ItemStack mainHand = player.inventory.getCurrentItem();
		ItemStack offhand = getItem();
		// raytrace //
		HitResult raytrace = Minecraft.getMinecraft().objectMouseOver;

		// where tf is your world man
		if (world == null) return false;

		// cancel if we don't have any item in offhand
		if (offhand == null) return false;

		// use offhand if we don't have anything in mainhand
		if (mainHand == null) return true;

		// cancel if we need to eat
		if (mainHand.getItem() instanceof ItemFood && player.getHealth() != player.getMaxHealth())
			return false;

		Block<?> block;
		if (raytrace != null && raytrace.hitType == HitResult.HitType.TILE && (block = world.getBlock(raytrace.x, raytrace.y, raytrace.z)) != null) {
			// check if player right click brazier
			if (block.getLogic() instanceof BlockLogicBrazier && !((BlockLogicBrazier) block.getLogic()).isBurning()) {
				if (mainHand.getItem() instanceof ItemFireStriker)
					return false;
				else if (offhand.getItem() instanceof ItemFireStriker)
					return true;
			}

			// check if player right click grass
			if (block.getLogic() instanceof BlockLogicGrass) {
				if (mainHand.getItem() instanceof ItemToolHoe)
					return false;
				else if (offhand.getItem() instanceof ItemToolHoe)
					return true;
			}

			// check if player look at farmland
			if (block.getLogic() instanceof BlockLogicFarmland) {
					if (mainHand.getItem() instanceof ItemSeeds)
						return false;
					else if (offhand.getItem() instanceof ItemSeeds)
						return true;
				}
		}

		// random block if we have both blocks in off-hand and main hand
		if (mainHand.getItem() instanceof ItemBlock) {
			if (!BuildingTweaksOptions.randomizeBlocks().value) return false;

			// randomize block
			return offhand.getItem() instanceof ItemBlock && random.nextBoolean();
		}

		return true;
	}

	// ITEM //

	public ItemStack getItem() {
		return slot >= 0 ? Minecraft.getMinecraft().thePlayer.inventory.getItem(slot) : null;
	}

	// SLOT //

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
		this.setEnabled(true);
	}

	public void resetSlot() {
		this.slot = -1;
		this.setEnabled(false);
	}

}
