package xyz.ipiepiepie.tweaks;

import xyz.ipiepiepie.tweaks.object.feature.Offhand;
import xyz.ipiepiepie.tweaks.object.feature.Refill;
import xyz.ipiepiepie.tweaks.object.feature.ShiftLock;

public class TweaksManager {
	// offhand //
	private static final Offhand offhand = new Offhand();
	// refill //
	private static final Refill refill = new Refill();
	// shift lock //
	private static final ShiftLock shiftLock = new ShiftLock();

	// OFFHAND //

	public static Offhand getOffhand() {
		return offhand;
	}

	// REFILL //

	public static Refill getRefill() {
		return refill;
	}

	// SHIFT LOCK //

	public static ShiftLock getShiftLock() {
		return shiftLock;
	}

}
