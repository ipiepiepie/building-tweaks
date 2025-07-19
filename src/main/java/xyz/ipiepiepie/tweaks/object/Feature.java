package xyz.ipiepiepie.tweaks.object;

import xyz.ipiepiepie.tweaks.TweaksManager;

/**
 * Represents some toggleable feature.
 */
public abstract class Feature {
	private boolean enabled = false;
	private final Icon icon;

	public Feature(Icon icon) {
		this.icon = icon;
	}

	// ICON //

	/**
	 * Get icon of feature.
	 * @return feature icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Check if feature has icon.
	 * @return {@code true} if it has icon, otherwise {@code false}
	 */
	public boolean hasIcon() {
		return icon != null;
	}

	// ENABLED //

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

	/**
	 * Icon of feature displayed in GUI
	 */
	public static class Icon {
		private final String texture;
		private final int width, height;

		public Icon(String texture, int width, int height) {
			this.texture = texture;
			this.width = width;
			this.height = height;
		}

		public String getTexture() {
			return texture;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}

}
