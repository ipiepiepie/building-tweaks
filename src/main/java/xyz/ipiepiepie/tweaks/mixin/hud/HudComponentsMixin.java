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
import xyz.ipiepiepie.tweaks.hud.HudComponentRefill;
import xyz.ipiepiepie.tweaks.hud.HudComponentShiftLock;

import static net.minecraft.client.gui.hud.component.HudComponents.register;

@Mixin(value = HudComponents.class, remap = false)
public abstract class HudComponentsMixin {
	@Final
	@Shadow
	public static HudComponent HOTBAR;

	@Unique
	private static final HudComponent REFILL = register(new HudComponentRefill("refill", new LayoutSnap(HOTBAR, ComponentAnchor.TOP_CENTER, ComponentAnchor.BOTTOM_CENTER)));

	@Unique
	private static final HudComponent SHIFT_LOCK = register(new HudComponentShiftLock("shiftlock", new LayoutAbsolute(1.0F, 1.0F, ComponentAnchor.BOTTOM_RIGHT)));



}
