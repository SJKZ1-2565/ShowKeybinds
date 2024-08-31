package com.sjkz1.showkeybinds.fabric.modmenu;

import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ShowKeybindsModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ShowKeybindsConfig.class, parent).get();
    }
}
