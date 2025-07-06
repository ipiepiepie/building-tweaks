package xyz.ipiepiepie.tweaks.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.lwjgl.opengl.GL11;
import xyz.ipiepiepie.tweaks.TweaksManager;

@Environment(EnvType.CLIENT)
public class HudComponentShiftLock extends HudComponentMovable {

	public HudComponentShiftLock(String key, Layout layout) {
		super(key, 13, 13, layout);
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return TweaksManager.getShiftLock().isEnabled() && minecraft.gameSettings.immersiveMode.drawOverlays();
	}

	@Override
	public void render(Minecraft minecraft, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(minecraft, this, xSizeScreen);
		int y = this.getLayout().getComponentY(minecraft, this, ySizeScreen);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);

		hud.drawGuiIcon(x, y, 13, 11, TextureRegistry.getTexture("buildingtweaks:gui/shiftlock_icon"));
	}

	@Override
	public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		int x = layout.getComponentX(minecraft, this, xSizeScreen);
		int y = layout.getComponentY(minecraft, this, ySizeScreen);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);

		gui.drawGuiIcon(x, y, 13, 11, TextureRegistry.getTexture("buildingtweaks:gui/shiftlock_icon"));
	}
}
