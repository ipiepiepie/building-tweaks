package xyz.ipiepiepie.tweaks.mixin.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.*;
import net.minecraft.core.lang.I18n;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.ipiepiepie.tweaks.config.IOptions;

@Environment(EnvType.CLIENT)
@Mixin(value = GameSettings.class, remap = false)
public abstract class GameSettingsMixin implements IOptions {
	@Unique
	private final GameSettings self = (GameSettings) (Object) this;

	// OFFHAND //

	@Unique
	private static final KeyBinding keyOffhand = new KeyBinding("options.buildingtweaks.offhand.keybind").setDefault(InputDevice.keyboard, Keyboard.KEY_F);

	@Unique
	private final OptionBoolean resetOffhandOnEmptyEnabled = new OptionBoolean(self, "buildingtweaks.offhand.resetOnEmpty", true);



	// RANDOMIZE //

	@Unique
	private final KeyBinding keyRandomize = new KeyBinding("options.buildingtweaks.randomize.keybind").setDefault(InputDevice.keyboard, Keyboard.KEY_J);

	// REFILL //

	@Unique
	private static final KeyBinding keyRefill = new KeyBinding("options.buildingtweaks.refill.keybind").setDefault(InputDevice.keyboard, Keyboard.KEY_G);

	@Unique
	private final OptionBoolean refillDrops = new OptionBoolean(self, "buildingtweaks.refill.refillDrops", true);

	// SHIFT LOCK //

	@Unique
	private final OptionBoolean usingDoubleShift = new OptionBoolean(self, "buildingtweaks.shiftlock.usingDoubleShift", true);

	@Unique
	private static final KeyBinding keyShiftLock = new KeyBinding("options.buildingtweaks.shiftlock.keybind");

	@Unique
	private final OptionBoolean affectsClimbing = new OptionBoolean(self, "buildingtweaks.shiftlock.affectsClimbing", true);

	// AUTO TOOL //

	@Unique
	private static final KeyBinding keyAutoTool = new KeyBinding("options.buildingtweaks.autotool.keybind").setDefault(InputDevice.keyboard, Keyboard.KEY_H);

	// UI //

	@Unique
	private final OptionRange featureIconsMode = new OptionRange(self, "buildingtweaks.ui.iconsMode", 0, 0, 1);

	@Inject(method = "getDisplayString", at = @At("HEAD"), cancellable = true)
	private void customDisplayString(Option<?> option, CallbackInfoReturnable<String> cir) {
		if (option == featureIconsMode) {
			int value = (int) option.value;
			if (value == 0) {
				cir.setReturnValue(I18n.getInstance().translateKey("options.buildingtweaks.ui.iconsMode.grouped"));
				return;
			}
			if (value == 1) {
				cir.setReturnValue(I18n.getInstance().translateKey("options.buildingtweaks.ui.iconsMode.movable"));
				return;
			}
			cir.setReturnValue(value + "%");
		}
	}

	// CUSTOM KEY BINDINGS //

	@Override
	public OptionBoolean buildingtweaks$getResetOffhandOnEmpty() {
		return resetOffhandOnEmptyEnabled;
	}

	@Override
	public KeyBinding buildingtweaks$getOffhandKey() {
		return keyOffhand;
	}

	@Override
	public KeyBinding buildingtweaks$getRefillKey() {
		return keyRefill;
	}

	@Override
	public OptionBoolean buildingtweaks$refillDrops() {
		return refillDrops;
	}

	@Override
	public OptionBoolean buildingtweaks$usingDoubleShift() {
		return usingDoubleShift;
	}

	@Override
	public KeyBinding buildingtweaks$getShiftLockKey() {
		return keyShiftLock;
	}

	@Override
	public OptionBoolean buildingtweaks$affectsClimbing() {
		return affectsClimbing;
	}

	@Override
	public KeyBinding buildingtweaks$getAutoToolKey() {
		return keyAutoTool;
	}

	@Override
	public OptionRange buildingtweaks$featureIconsMode() {
		return featureIconsMode;
	}

	@Override
	public KeyBinding buildingtweaks$getRandomizeKey() {
		return keyRandomize;
	}

}
