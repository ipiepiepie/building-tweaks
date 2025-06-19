package xyz.ipiepiepie.tweaks.mixin.hud;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentHotbar;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.ipiepiepie.tweaks.TweaksManager;

@Mixin(value = HudComponentHotbar.class, remap = false)
public abstract class ScreenInventoryMixin {

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;getCurrentItemIndex()I", shift = At.Shift.BEFORE))
	private void renderMixin(Minecraft mc, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick, CallbackInfo ci, @Local(name = "x") int x, @Local(name = "y") int y, @Local(name = "inv") ContainerInventory inventory) {
		// skip if offhand is selected or isn't enabled
		if (!TweaksManager.getInstance().isOffhandEnabled() || inventory.getCurrentItemIndex() == TweaksManager.getInstance().getOffhandSlot()) return;

		// draw offhand icon
		hud.drawGuiIcon(x + 4 + TweaksManager.getInstance().getOffhandSlot() % 9 * 20, y - 1, 24, 24, TextureRegistry.getTexture("buildingtweaks:gui/offhand_hotbar_selection"));
	}



}
