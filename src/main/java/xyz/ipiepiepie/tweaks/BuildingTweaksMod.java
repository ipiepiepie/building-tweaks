package xyz.ipiepiepie.tweaks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import xyz.ipiepiepie.tweaks.config.BuildingTweaksOptions;

import java.io.IOException;
import java.net.URISyntaxException;

public class BuildingTweaksMod implements ModInitializer, ClientStartEntrypoint {
    public static final String MOD_ID = "buildingtweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Pie's Building Tweaks initialized.");
    }

	@Override
	public void beforeClientStart() {
		try {
			TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.guiSpriteAtlas, true);
		} catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void afterClientStart() {
		BuildingTweaksOptions.initialise();
	}


}
