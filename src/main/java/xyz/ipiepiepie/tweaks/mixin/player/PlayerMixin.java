package xyz.ipiepiepie.tweaks.mixin.player;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.ipiepiepie.tweaks.TweaksManager;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {

	@Inject(method = "setHotbarOffset", at = @At(value = "HEAD"))
	private void onHotbarOffset(int offset, CallbackInfo ci) {
		// reset offhand on changing hotbar
		TweaksManager.getInstance().setOffhandSlot(-1);
	}

}
