package xyz.ipiepiepie.tweaks.config;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
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
					new OptionsCategory("options.buildingtweaks.refill")
						.withComponent(new KeyBindingComponent(getRefillKey()))
				)
				.withComponent(
					new OptionsCategory("options.buildingtweaks.shiftlock")
						.withComponent(new BooleanOptionComponent(usingDoubleShift()))
						.withComponent(new KeyBindingComponent(getShiftLockKey()))
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
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getResetOffhandOnEmptyBoolean();
	}

	public static KeyBinding getRefillKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getRefillKey();
	}

	public static OptionBoolean usingDoubleShift() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$usingDoubleShift();
	}

	public static KeyBinding getShiftLockKey() {
		return ((IOptions) Minecraft.getMinecraft().gameSettings).buildingtweaks$getShiftLockKey();
	}
}
