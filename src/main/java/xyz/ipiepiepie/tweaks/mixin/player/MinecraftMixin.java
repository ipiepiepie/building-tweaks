package xyz.ipiepiepie.tweaks.mixin.player;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.input.InputDevice;
import net.minecraft.core.item.ItemStack;
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

	// TODO change logic
	@Unique
	private int cachedSlot = -1;

	// OFFHAND //

	@Inject(method = "clickMouse", at = @At(value = "HEAD"))
	private void rightClickHeadMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci) {
		// return if we didn't select offhand
		if (!TweaksManager.getOffhand().isEnabled() || clickType != 1 || thePlayer.inventory.getCurrentItemIndex() == TweaksManager.getOffhand().getSlot()) return;

		// don't use offhand when slot is locked
		if (thePlayer.inventory.currentItemLocked()) return;

		// skip if we shouldn't use offhand
		if (!TweaksManager.getOffhand().shouldUse()) return;

		// cache main hand slot index to move cursor back later
		cachedMainSlot = thePlayer.inventory.getCurrentItemIndex();
		// temporally move cursor to offhand
		thePlayer.inventory.setCurrentItemIndex(TweaksManager.getOffhand().getSlot(), false);
	}

	@Inject(method = "clickMouse", at = @At(value = "TAIL"))
	private void rightClickTailMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci) {
		// prevent switching main hand and offhand when it wasn't switched
		if (cachedMainSlot < 0) return;

		// return cursor to main hand
		thePlayer.inventory.setCurrentItemIndex(cachedMainSlot, true);
		// reset cache
		cachedMainSlot = -1;
	}

	// AUTO TOOL //

	@Inject(method = "clickMouse", at = @At(value = "HEAD"))
	private void leftClickHeadMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci) {
		// return if we didn't select offhand
		if (!TweaksManager.getAutoTool().isEnabled() || clickType != 0) return;

		// don't use offhand when slot is locked
		if (thePlayer.inventory.currentItemLocked()) return;

		int slot = TweaksManager.getAutoTool().tryToUse();

		// skip if we don't have any tool for this block
		if (slot == -1 || slot == thePlayer.inventory.getCurrentItemIndex()) return;

		int cache = cachedSlot;

		// try to reset auto tool
		resetAutoTool();

		// return if needed tool lays in swapped slot
		if (cache == slot) return;

		// cache item slot, since we put here our main hand item
		cachedSlot = slot;

		// swap items
		TweaksManager.swapSlots(thePlayer.inventory.getCurrentItemIndex(), slot);
	}

	@Unique
	private void resetAutoTool() {
		if (cachedSlot >= 0) {
			TweaksManager.swapSlots(thePlayer.inventory.getCurrentItemIndex(), cachedSlot);
			// reset cache
			cachedSlot = -1;
		}
	}

	@Inject(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/player/inventory/container/ContainerInventory;getCurrentItemIndex()I", shift = At.Shift.BY, by = 3))
	private void placeRefillMixin(int clickType, boolean attack, boolean repeat, CallbackInfo ci, @Local(name = "stack") ItemStack stack) {
		boolean refilled = false;

		// skip if player still has items in stack
		if (thePlayer.inventory.currentItemLocked() || stack == null || stack.stackSize > 0) return;

		// refill if enabled
		if (TweaksManager.getRefill().isEnabled())
			refilled = TweaksManager.getRefill().refill(stack);

		// if refill failed, reset offhand
		if (!refilled && TweaksManager.getOffhand().isEnabled() && BuildingTweaksOptions.isResetOffhandOnEmpty().value && cachedMainSlot >= 0)
			TweaksManager.getOffhand().resetSlot();
	}

	@Inject(method = "clickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/controller/PlayerController;useItemStackOnNothing(Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/World;Lnet/minecraft/core/item/ItemStack;)Z", shift = At.Shift.AFTER))
	private void useRefillMixin(CallbackInfo ci, @Local ItemStack stack) {
		boolean refilled = false;

		// skip if player still has items in stack
		if (thePlayer.inventory.currentItemLocked() || stack == null || stack.stackSize > 0) return;

		// refill if enabled
		if (TweaksManager.getRefill().isEnabled())
			refilled = TweaksManager.getRefill().refill(stack);

		// if refill failed, reset offhand
		if (!refilled && TweaksManager.getOffhand().isEnabled() && BuildingTweaksOptions.isResetOffhandOnEmpty().value && cachedMainSlot >= 0)
			TweaksManager.getOffhand().resetSlot();
	}

	// INPUTS //

	@Inject(method = "checkBoundInputs", at = @At("HEAD"), cancellable = true)
	private void checkModdedBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable<Boolean> cir) {
		// swap auto tool slots back
		if (Minecraft.getMinecraft().gameSettings.keyAttack.isReleaseEvent(currentInputDevice)) {
			if (cachedSlot >= 0) {
				TweaksManager.swapSlots(thePlayer.inventory.getCurrentItemIndex(), cachedSlot);
				// reset cache
				cachedSlot = -1;
			}
		}

		if (BuildingTweaksOptions.getOffhandKey().isPressEvent(currentInputDevice)) {
			// reset offhand if its already enabled
			if (TweaksManager.getOffhand().isEnabled()) {
				TweaksManager.getOffhand().resetSlot();
				return;
			}

			// can't do anything if current item locked
			if (thePlayer.inventory.currentItemLocked()) return;

			// set slot as offhand
			TweaksManager.getOffhand().setSlot(thePlayer.inventory.getCurrentItemIndex());

			cir.setReturnValue(true);
		} else if (BuildingTweaksOptions.getRefillKey().isPressEvent(currentInputDevice)) {
			// switch refill
			TweaksManager.getRefill().setEnabled(!TweaksManager.getRefill().isEnabled());

			cir.setReturnValue(true);
		} else if (BuildingTweaksOptions.getRandomizeKey().isPressEvent(currentInputDevice)) {
			// enable randomize
			TweaksManager.getRandomize().setEnabled(!TweaksManager.getRandomize().isEnabled());

			cir.setReturnValue(true);
		} else if (BuildingTweaksOptions.getShiftLockKey().isPressEvent(currentInputDevice)) {
			// enable shift lock
			TweaksManager.getShiftLock().setEnabled(!TweaksManager.getShiftLock().isEnabled());

			cir.setReturnValue(true);
		} else if (BuildingTweaksOptions.getAutoToolKey().isPressEvent(currentInputDevice)) {
			// enable auto tool
			TweaksManager.getAutoTool().setEnabled(!TweaksManager.getAutoTool().isEnabled());

			cir.setReturnValue(true);
		}
	}

}
