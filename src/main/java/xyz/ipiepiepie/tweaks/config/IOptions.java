package xyz.ipiepiepie.tweaks.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;

@Environment(EnvType.CLIENT)
public interface IOptions {

	OptionBoolean buildingtweaks$getResetOffhandOnEmptyBoolean();

	KeyBinding buildingtweaks$getRandomizeKey();

	KeyBinding buildingtweaks$getOffhandKey();

	KeyBinding buildingtweaks$getRefillKey();

	OptionBoolean buildingtweaks$usingDoubleShift();

	KeyBinding buildingtweaks$getShiftLockKey();

	OptionBoolean buildingtweaks$affectsClimbing();

	KeyBinding buildingtweaks$getAutoToolKey();

	OptionRange buildingtweaks$featureIconsMode();

}
