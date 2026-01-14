package com.sjkz1.showkeybinds.neoforge;

import com.sjkz1.showkeybinds.Showkeybinds;
import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(Showkeybinds.MOD_ID)
public final class ShowkeybindsNeoForge {
    public ShowkeybindsNeoForge() {
        Showkeybinds.init();
        Showkeybinds.registerConfig();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, screen) -> AutoConfigClient.getConfigScreen(ShowKeybindsConfig.class, screen).get());
    }
}
