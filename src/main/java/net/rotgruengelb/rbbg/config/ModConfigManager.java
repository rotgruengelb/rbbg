package net.rotgruengelb.rbbg.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.rotgruengelb.rbbg.RandomButtonsBeGone;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class ModConfigManager {
	private static final File CONFIG_FILE = RandomButtonsBeGone.getConfigFile();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static Config config;

	public static void initConfig() {
		loadConfig();
		try {
			startFileWatcher();
		} catch (IOException e) {
			RandomButtonsBeGone.LOGGER.error("IO Exception occurred while initializing config. The Config Background Update Checker might not work.", e);
		}
	}

	public static void loadConfig() {
		if (CONFIG_FILE.exists()) {
			try (FileReader reader = new FileReader(CONFIG_FILE)) {
				config = GSON.fromJson(reader, Config.class);
			} catch (IOException e) {
				RandomButtonsBeGone.LOGGER.error("IO Exception occurred while loading config file", e);
				config = new Config();
			}
		} else {
			config = new Config();
			saveConfig();
		}
	}

	public static void saveConfig() {
		CONFIG_FILE.getParentFile().mkdirs();
		try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
			GSON.toJson(config, writer);
		} catch (IOException e) {
			RandomButtonsBeGone.LOGGER.error("IO Exception occurred while saving config file", e);
		}
	}

	private static void startFileWatcher() throws IOException {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path configPath = CONFIG_FILE.toPath();
		configPath.getParent().register(watchService, ENTRY_MODIFY);

		Thread watcherThread = new Thread(() -> {
			while (true) {
				WatchKey key;
				try {
					key = watchService.take();
				} catch (InterruptedException ex) { return; }
				for (WatchEvent<?> event : key.pollEvents()) {
					if (event.kind() != ENTRY_MODIFY) { continue; }
					if (event.context().equals(configPath.getFileName())) { loadConfig(); }
				}
				if (!key.reset()) { break; }
			}
		});

		watcherThread.setDaemon(true);
		watcherThread.start();
	}

	public static Config getConfig() {
		return config;
	}

	public static class Config {
		public boolean physicsmod = true;
		public boolean stutterfix = true;
	}
}
