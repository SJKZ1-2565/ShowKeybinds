package com.sjkz1.showkeybinds.fabric;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.fabricmc.api.ModInitializer;

public final class ShowkeybindsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Showkeybinds.init();
        Showkeybinds.registerConfig();
    }
}
