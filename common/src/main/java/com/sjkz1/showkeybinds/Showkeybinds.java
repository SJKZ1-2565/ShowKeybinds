package com.sjkz1.showkeybinds;

import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Showkeybinds {
    public static final String MOD_ID = "showkeybinds";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ShowKeybindsConfig CONFIG;
    public static boolean DEBUG = false;

    public static void registerConfig() {
        AutoConfig.register(ShowKeybindsConfig.class, GsonConfigSerializer::new);
        Showkeybinds.CONFIG = AutoConfig.getConfigHolder(ShowKeybindsConfig.class).getConfig();
    }

    public static void init() {
        String platformType = Platform.isFabric() ? "Fabric" : Platform.isMinecraftForge() ? "Forge" : Platform.isNeoForge() ? "NeoForge" : "";
        LOGGER.info("Hello {} user!", platformType);
    }
}
