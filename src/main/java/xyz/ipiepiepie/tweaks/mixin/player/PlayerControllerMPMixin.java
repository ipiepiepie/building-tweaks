package xyz.ipiepiepie.tweaks.mixin.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.controller.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xyz.ipiepiepie.tweaks.TweaksManager;

@Environment(EnvType.CLIENT)
@Mixin(value = PlayerControllerMP.class, remap = false)
public class PlayerControllerMPMixin {

	@ModifyArg(method = "useOrPlaceItemStackOnTile", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/net/packet/PacketUseOrPlaceItemStack;<init>(IIILnet/minecraft/core/util/helper/Direction;Lnet/minecraft/core/item/ItemStack;DDB)V"), index = 7)
	private byte redirectShiftLock(byte type) {
		if (TweaksManager.getShiftLock().isEnabled()) return 2;

		return 0;
	}

}
