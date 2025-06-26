package xyz.ipiepiepie.tweaks.mixin.player;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.input.InputDevice;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.ipiepiepie.tweaks.TweaksManager;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Shadow
	public PlayerLocal thePlayer;

	@Unique
	private int cachedMainSlot = -1;

	// OFFHAND //

	@Inject(method = "clickMouse", at = @At(value = "HEAD"))
	private void clickHeadMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci) {
		ItemStack mainHand = thePlayer.inventory.getCurrentItem();
		ItemStack offhand = TweaksManager.getInstance().isOffhandEnabled() ? thePlayer.inventory.getItem(TweaksManager.getInstance().getOffhandSlot()) : null;

		// return if we didn't select offhand
		if (offhand == null || clickType != 1 || thePlayer.inventory.getCurrentItemIndex() == TweaksManager.getInstance().getOffhandSlot()) return;

		// return if we can place or eat item in main hand
		if (mainHand != null && (mainHand.getItem() instanceof ItemBlock || mainHand.getItem() instanceof ItemFood)) return;

		// don't use offhand when slot is locked
		if (thePlayer.inventory.currentItemLocked()) return;

		// cache main hand slot index to move cursor back later
		cachedMainSlot = thePlayer.inventory.getCurrentItemIndex();
		// temporally move cursor to offhand
		thePlayer.inventory.setCurrentItemIndex(TweaksManager.getInstance().getOffhandSlot(), false);
	}

	@Inject(method = "clickMouse", at = @At(value = "TAIL"))
	private void clickTailMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci) {
		// prevent switching main hand and offhand when it wasn't switched
		if (cachedMainSlot < 0) return;

		// return cursor to main hand
		thePlayer.inventory.setCurrentItemIndex(cachedMainSlot, true);
		// reset cache
		cachedMainSlot = -1;
	}

	@Inject(method = "checkBoundInputs", at = @At("HEAD"), cancellable = true)
	private void checkModdedBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir) {
		if (BuildingTweaksOptions.getOffhandKey().isPressEvent(currentInputDevice)) {
			// reset offhand if its already enabled
			if (TweaksManager.getInstance().isOffhandEnabled()) {
				TweaksManager.getInstance().setOffhandSlot(-1);
				return;
			}

			// can't do anything if current item locked
			if (thePlayer.inventory.currentItemLocked()) return;

			// set slot as offhand
			TweaksManager.getInstance().setOffhandSlot(thePlayer.inventory.getCurrentItemIndex());

			cir.setReturnValue(true);
		} else if (BuildingTweaksOptions.getRefillKey().isPressEvent(currentInputDevice)) {
			// switch refill
			TweaksManager.getInstance().setRefillEnabled(!TweaksManager.getInstance().isRefillEnabled());

			cir.setReturnValue(true);
		} else if (BuildingTweaksOptions.getShiftLockKey().isPressEvent(currentInputDevice)) {
			// enable shift lock
			TweaksManager.getInstance().setShiftLockEnabled(!TweaksManager.getInstance().isShiftLockEnabled());

			cir.setReturnValue(true);
		}
	}

	// REFILL //

	@Inject(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;getCurrentItemIndex()I", shift = At.Shift.BY, by = 3))
	private void placeRefillMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci, @Local(name = "stack") ItemStack stack) {
		TweaksManager.getInstance().refill(thePlayer, stack);
	}

	@Inject(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/controller/PlayerController;useItemStackOnNothing(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;)Z", shift = At.Shift.AFTER))
	private void useRefillMixin(CallbackInfo ci, @Local ItemStack stack) {
		TweaksManager.getInstance().refill(thePlayer, stack);
	}

}
