package xyz.ipiepiepie.tweaks.mixin.hud;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.ipiepiepie.tweaks.TweaksManager;

import java.awt.*;

@Mixin(value = HudIngame.class, remap = false)
public abstract class HudMixin {

	@Shadow
	protected Minecraft mc;

	@Inject(method = "renderGameOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameSettings;heldItemCountOverlay:Lnet/minecraft/client/option/OptionBoolean;", shift = At.Shift.AFTER))
	private void addOffhandItemCounter(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci, @Local(name = "width") int width, @Local(name = "height") int height, @Local(name = "sp") int sp, @Local Font font) {
		// skip if offhand is disabled
		if (!TweaksManager.getOffhand().isEnabled()) return;

		ItemStack offhandItem = mc.thePlayer.inventory.getItem(TweaksManager.getOffhand().getSlot());
		int offhandItemCount = offhandItem != null ? offhandItem.stackSize : 0;

		// skip if we don't have any item in offhand
		if (offhandItemCount <= 0) return;

		int line = width / 2 - 106;
		int y = height - sp - 19;
		boolean clock;
		int textWidth;
		if (offhandItem.isItemStackDamageable()) {
			clock = true;
			float durability = (float) (offhandItem.getMaxDamage() - offhandItem.getMetadata()) / (float) offhandItem.getMaxDamage();
			int stackCount = Color.HSBtoRGB(durability / 3.0F, 1.0F, 1.0F);
			int blockX = offhandItem.getMaxDamage() - offhandItem.getMetadata() + 1;
			textWidth = String.valueOf(blockX).length();
			font.drawStringWithShadow("" + blockX, line - (textWidth * 6), y + 4, stackCount);
		} else {
			int itemCount = 0;
			int stackCount = 0;

			for(int index = 0; index < this.mc.thePlayer.inventory.getContainerSize(); ++index) {
				ItemStack item = this.mc.thePlayer.inventory.getItem(index);
				if (item != null && item.itemID == offhandItem.itemID && item.getMetadata() == offhandItem.getMetadata()) {
					itemCount += item.stackSize;
					++stackCount;
				}
			}

			textWidth = String.valueOf(itemCount).length() - 1;

			clock = stackCount >= 1;
			if (clock) {
				font.drawStringWithShadow("" + itemCount, line - (textWidth * 6), y + 4, 16777215);
			}
		}

		if (clock) {
			GL11.glEnable(2929);
			GL11.glDisable(3042);
			GL11.glEnable(32826);
			Lighting.enableInventoryLight();
			ItemModelDispatcher.getInstance().getDispatch(offhandItem).renderItemIntoGui(Tessellator.instance, font, this.mc.textureManager, offhandItem, line - 18 - (textWidth * 6), y, 1.0F);
			Lighting.disable();
		}
    }

}
