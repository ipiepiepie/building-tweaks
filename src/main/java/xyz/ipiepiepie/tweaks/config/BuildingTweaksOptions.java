package xyz.ipiepiepie.tweaks.config;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.*;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import xyz.ipiepiepie.tweaks.BuildingTweaksMod;

import java.util.function.Function;

public class BuildingTweaksOptions implements ModMenuApi {
	private static OptionsPage options;

	public static void initialise() {
		options = OptionsPages.register(
			new OptionsPage("options.buildingtweaks", new ItemStack(Blocks.PILLAR_MARBLE))
				.withComponent(
					new OptionsCategory("options.buildingtweaks.offhand")
						.withComponent(new KeyBindingComponent(getOffhandKey()))
						.withComponent(new BooleanOptionComponent(isResetOffhandOnEmpty()))
				)
				.withComponent(
					new OptionsCategory("options.buildingtweaks.randomize")
						.withComponent(new KeyBindingComponent(getRandomizeKey()))
				)
				.withComponent(
					new OptionsCategory("options.buildingtweaks.refill")
						.withComponent(new KeyBindingComponent(getRefillKey()))
						.withComponent(new BooleanOptionComponent(refillDrops()))
				)
				.withComponent(
					new OptionsCategory("options.buildingtweaks.shiftlock")
						.withComponent(new BooleanOptionComponent(usingDoubleShift()))
						.withComponent(new KeyBindingComponent(getShiftLockKey()))
						.withComponent(new BooleanOptionComponent(affectsClimbing()))
				)
				.withComponent(
					new OptionsCategory("options.buildingtweaks.autotool")
						.withComponent(new KeyBindingComponent(getAutoToolKey()))
						//.withComponent(new KeyBindingComponent(getSilkAutoToolKey()))
				)
				.withComponent(
					new OptionsCategory("options.buildingtweaks.ui")
						.withComponent(new ToggleableOptionComponent<>(groupFeatureIcons()))
				)
		);
	}

	@Override
	public String getModId() {
		return BuildingTweaksMod.MOD_ID;
	}

	@Override
	public Function<Screen, ScreenOptions> getConfigScreenFactory() {
		return (screen) -> new ScreenOptions(screen, options);
	}

	// KEY BINDINGS //

	public static KeyBinding getOffhandKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getOffhandKey();
	}

	public static OptionBoolean isResetOffhandOnEmpty() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getResetOffhandOnEmpty();
	}

	public static KeyBinding getRefillKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getRefillKey();
	}

	public static OptionBoolean refillDrops() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$refillDrops();
	}

	public static OptionBoolean usingDoubleShift() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$usingDoubleShift();
	}

	public static KeyBinding getRandomizeKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getRandomizeKey();
	}

	public static KeyBinding getShiftLockKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getShiftLockKey();
	}

	public static OptionBoolean affectsClimbing() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$affectsClimbing();
	}

	public static KeyBinding getAutoToolKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getAutoToolKey();
	}

	public static OptionRange groupFeatureIcons() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$featureIconsMode();
	}

}
