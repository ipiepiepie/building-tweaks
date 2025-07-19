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
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;
import xyz.ipiepiepie.tweaks.object.Feature;

import java.util.List;

@Environment(EnvType.CLIENT)
public class HudComponentFeatures extends HudComponentMovable {
	private static final int scale = 3;

	public HudComponentFeatures(String key, Layout layout) {
		super(
			key,
			TweaksManager.getFeatures().stream().filter(Feature::hasIcon).mapToInt(feature -> feature.getIcon().getWidth()).max().orElse(-1) + scale + 1,
			TweaksManager.getFeatures().stream().filter(Feature::hasIcon).mapToInt(feature -> feature.getIcon().getHeight()).sum() + (TweaksManager.getFeatures().size() * scale) + 1,
			layout
		);
	}

	@Override
	public boolean isVisible(Minecraft minecraft) {
		return BuildingTweaksOptions.groupFeatureIcons().value == 0;
	}

	@Override
	public void render(Minecraft minecraft, HudIngame hud, int xSizeScreen, int ySizeScreen, float partialTick) {
		int x = this.getLayout().getComponentX(minecraft, this, xSizeScreen);
		int y = this.getLayout().getComponentY(minecraft, this, ySizeScreen);
		int yBottom = y + this.getYSize(minecraft) - 1;
		int xRight = x + this.getXSize(minecraft);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);

		// get list of all features
		List<Feature> features = TweaksManager.getFeatures();

		// search for features to display
		for (Feature feature : features) {
			// skip feature if it's disabled or don't have any icon
			if (!feature.isEnabled() || !feature.hasIcon()) continue;

			Feature.Icon icon = feature.getIcon();

			int width = icon.getWidth() + scale;
			int height = icon.getHeight() + scale;

			// draw icon
			hud.drawGuiIcon(xRight - width - 1, yBottom - height, width, height, TextureRegistry.getTexture(icon.getTexture()));

			// adjust y
			yBottom -= height + 1;
		}
	}

	@Override
	public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xSizeScreen, int ySizeScreen) {
		if (BuildingTweaksOptions.groupFeatureIcons().value == 1) return;

		int x = this.getLayout().getComponentX(minecraft, this, xSizeScreen);
		int y = this.getLayout().getComponentY(minecraft, this, ySizeScreen);
		int yBottom = y + this.getYSize(minecraft) - 1;
		int xRight = x + this.getXSize(minecraft);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);


		// get list of all features
		List<Feature> features = TweaksManager.getFeatures();

		// search for features to display
		for (Feature feature : features) {
			// skip feature if it's disabled or don't have any icon
			if (!feature.hasIcon()) continue;

			Feature.Icon icon = feature.getIcon();

			int width = icon.getWidth() + scale;
			int height = icon.getHeight() + scale;

			// draw icon
			gui.drawGuiIcon(xRight - width - 1, yBottom - height, width, height, TextureRegistry.getTexture(icon.getTexture()));

			// adjust y
			yBottom -= height + 1;
		}

	}
}
