package xyz.ipiepiepie.tweaks.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;

@Environment(EnvType.CLIENT)
public interface IOptions {

	OptionBoolean buildingtweaks$getResetOffhandOnEmptyBoolean();

	OptionBoolean buildingtweaks$randomizeBlocks();

	KeyBinding buildingtweaks$getOffhandKey();

	KeyBinding buildingtweaks$getRefillKey();

	OptionBoolean buildingtweaks$usingDoubleShift();

	KeyBinding buildingtweaks$getShiftLockKey();

}
