package xyz.ipiepiepie.tweaks.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.lwjgl.opengl.GL11;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;
import xyz.ipiepiepie.tweaks.object.Feature;

public class HudComponentFeature extends HudComponentMovable {
	private final Feature feature;

	public HudComponentFeature(String key, Feature feature, Layout layout) {
		super(key, feature.getIcon().getWidth(), feature.getIcon().getHeight() + 1, layout);
		this.feature = feature;
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return BuildingTweaksOptions.groupFeatureIcons().value == 1 && feature.isEnabled() && minecraft.gameSettings.immersiveMode.drawOverlays();
	}

	@Override
	public void render(Minecraft minecraft, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(minecraft, this, xSizeScreen);
		int y = this.getLayout().getComponentY(minecraft, this, ySizeScreen);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);

		Feature.Icon icon = feature.getIcon();

		hud.drawGuiIcon(x, y, icon.getWidth(), icon.getHeight(), TextureRegistry.getTexture(icon.getTexture()));
	}

	@Override
	public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		if (BuildingTweaksOptions.groupFeatureIcons().value == 0) return;

		int x = layout.getComponentX(minecraft, this, xSizeScreen);
		int y = layout.getComponentY(minecraft, this, ySizeScreen);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);

		Feature.Icon icon = feature.getIcon();

		gui.drawGuiIcon(x, y, icon.getWidth(), icon.getHeight(), TextureRegistry.getTexture(icon.getTexture()));
	}
}
