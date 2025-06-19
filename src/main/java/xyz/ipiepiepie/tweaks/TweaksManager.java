package xyz.ipiepiepie.tweaks;

public class TweaksManager {
	private static TweaksManager manager;
	// offhand //
	private int offhandSlot = -1;
	// refill //
	private boolean refillEnabled = false;


	public static TweaksManager getInstance() {
		if (manager == null) manager = new TweaksManager();

		return manager;
	}

	// OFFHAND //

	public boolean isOffhandEnabled() {
		return offhandSlot >= 0;
	}

	public int getOffhandSlot() {
		return offhandSlot;
	}

	public void setOffhandSlot(int slot) {
		this.offhandSlot = slot;
	}

	// REFILL //

	public boolean isRefillEnabled() {
		return refillEnabled;
	}

	public void setRefillEnabled(boolean enabled) {
		this.refillEnabled = enabled;
	}
}
