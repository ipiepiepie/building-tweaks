package xyz.ipiepiepie.tweaks.mixin.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionBoolean;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

	@Unique
	private final OptionBoolean randomizeBlocks = new OptionBoolean(self, "buildingtweaks.offhand.randomizeBlocks", true);

	// REFILL //

	@Unique
	private static final KeyBinding keyRefill = new KeyBinding("options.buildingtweaks.refill.keybind").setDefault(InputDevice.keyboard, Keyboard.KEY_G);

	// SHIFT LOCK KEY //

	@Unique
	private final OptionBoolean usingDoubleShift = new OptionBoolean(self, "buildingtweaks.shiftlock.usingDoubleShift", true);

	@Unique
	private static final KeyBinding keyShiftLock = new KeyBinding("options.buildingtweaks.shiftlock.keybind");

	@Unique
	private final OptionBoolean affectsClimbing = new OptionBoolean(self, "buildingtweaks.shiftlock.affectsClimbing", true);

	// CUSTOM KEY BINDINGS //

	@Override
	public OptionBoolean buildingtweaks$getResetOffhandOnEmptyBoolean() {
		return resetOffhandOnEmptyEnabled;
	}

	@Override
	public OptionBoolean buildingtweaks$randomizeBlocks() {
		return randomizeBlocks;
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

}
