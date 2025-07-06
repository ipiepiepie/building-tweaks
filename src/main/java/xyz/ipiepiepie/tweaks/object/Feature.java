package xyz.ipiepiepie.tweaks.object;

/**
 * Represents some toggleable feature.
 */
public abstract class Feature {
	private boolean enabled = false;

	/**
	 * Check if current feature is enabled.
	 * @return {@code true} if enabled, otherwise {@code false}
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set current feature enabled
	 * @param enabled boolean determines if feature enabled or not
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
