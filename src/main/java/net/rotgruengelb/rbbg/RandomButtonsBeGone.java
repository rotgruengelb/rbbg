package net.rotgruengelb.rbbg;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.rotgruengelb.rbbg.config.ModConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class RandomButtonsBeGone implements ClientModInitializer {

	/** <b>R</b>andom <b>B</b>uttons <b>B</b>e <b>G</b>one */
	public static final String MOD_ID = "rbbg";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		ModConfigManager.initConfig();
	}

	public static File getConfigFile() {
		return FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "/config.json").toFile();
	}
}