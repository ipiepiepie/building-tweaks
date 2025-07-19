package xyz.ipiepiepie.tweaks.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.controller.PlayerController;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.ipiepiepie.tweaks.TweaksManager;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerController.class, remap = false)
public abstract class PlayerControllerMixin {

	@Redirect(method = "useOrPlaceItemStackOnTile", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;isSneaking()Z"))
	private boolean redirectShiftLock(Player player) {
		if (TweaksManager.getShiftLock().isEnabled()) return true;

		return player.isSneaking();
	}

}
