package xyz.ipiepiepie.tweaks.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.ipiepiepie.tweaks.TweaksManager;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerInput.class, remap = false)
public abstract class PlayerInputMixin {

	@Shadow
	public boolean sneak;
	@Shadow
	@Final
	public GameSettings gameSettings;

	@Unique
	private int sneakTicks = 0;
	@Unique
	private boolean climbing = false;

	/**
	 * Double shift click handler
	 */
	@Inject(method = "keyEvent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameSettings;sneakToggle:Lnet/minecraft/client/option/OptionBoolean;"))
	private void onShiftClick(int keyCode, boolean pressed, CallbackInfo ci) {
		// return if shift lock key is bound (double shift click is disabled)
		if (!pressed || !BuildingTweaksOptions.usingDoubleShift().value) return;

		// disable shift lock if needed
		if (TweaksManager.getInstance().isShiftLockEnabled() && !climbing) {
			TweaksManager.getInstance().setShiftLockEnabled(false);
			return;
		}

		// check if first shift click pressed
		if (sneakTicks > 0) {
			// enable shift lock
			TweaksManager.getInstance().setShiftLockEnabled(true);
			// reset sneak ticks
			sneakTicks = 0;
		} else {
			sneakTicks = 5;
		}
	}

	@Inject(method = "tick", at = @At(value = "HEAD"))
	private void onTick(Player player, CallbackInfo ci) {
		// decrease sneak ticks
		if (sneakTicks > 0) sneakTicks -= 1;
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void onTickTail(Player player, CallbackInfo ci) {
		// affect climbing by shift-lock
		if (TweaksManager.getInstance().isShiftLockEnabled() && BuildingTweaksOptions.affectsClimbing().value && Minecraft.getMinecraft().thePlayer.canClimb()) {
			// go down if player is sneaking, otherwise stop
			sneak = !gameSettings.keySneak.isPressed();
			// set climbing flag
			climbing = true;
		} else if (climbing) { // reset climbing flag if smth goes wrong
			climbing = sneak = false;
		}
	}

}
