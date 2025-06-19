package xyz.ipiepiepie.tweaks.mixin.hud;

import net.minecraft.client.gui.HeldItemTooltipElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = HeldItemTooltipElement.class, remap = false)
public abstract class HeldItemTooltipMixin {

	@ModifyVariable(method = "render(Lnet/minecraft/client/Minecraft;II)V", at = @At(value = "HEAD"), ordinal = 1, argsOnly = true)
	private int render(int y) {
		// move item tooltip a bit higher
		return y - 2;
	}

}
