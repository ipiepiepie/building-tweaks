package xyz.ipiepiepie.tweaks.mixin.hud;

import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutAbsolute;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import xyz.ipiepiepie.tweaks.TweaksManager;
import xyz.ipiepiepie.tweaks.hud.HudComponentFeature;
import xyz.ipiepiepie.tweaks.hud.HudComponentFeatures;

import static net.minecraft.client.gui.hud.component.HudComponents.register;

@Mixin(value = HudComponents.class, remap = false)
public abstract class HudComponentsMixin {
	@Final
	@Shadow
	public static HudComponent HOTBAR;

	@Unique
	private static final HudComponent REFILL = register(new HudComponentFeature("refill", TweaksManager.getRefill(), new LayoutSnap(HOTBAR, ComponentAnchor.TOP_CENTER, ComponentAnchor.BOTTOM_CENTER)));

	@Unique
	private static final HudComponent AUTO_TOOL = register(new HudComponentFeature("autotool", TweaksManager.getAutoTool(), new LayoutAbsolute(1.0F, 1.0F,ComponentAnchor.BOTTOM_RIGHT)));

	@Unique
	private static final HudComponent RANDOMIZE = register(new HudComponentFeature("randomize", TweaksManager.getRandomize(), new LayoutSnap(AUTO_TOOL, ComponentAnchor.TOP_CENTER, ComponentAnchor.BOTTOM_CENTER)));

	@Unique
	private static final HudComponent SHIFT_LOCK = register(new HudComponentFeature("shiftlock", TweaksManager.getShiftLock(), new LayoutSnap(RANDOMIZE, ComponentAnchor.TOP_CENTER, ComponentAnchor.BOTTOM_CENTER)));

	@Unique
	private static final HudComponent FEATURES = register(new HudComponentFeatures("features", new LayoutAbsolute(1.0F, 1.0F, ComponentAnchor.BOTTOM_RIGHT)));



}
