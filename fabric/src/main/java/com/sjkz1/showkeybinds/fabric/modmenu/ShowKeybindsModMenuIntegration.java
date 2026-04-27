package com.sjkz1.showkeybinds.fabric.modmenu;

import com.sjkz1.showkeybinds.config.ShowKeybindsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;

public class ShowKeybindsModMenuIntegration implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return parent -> AutoConfigClient.getConfigScreen(ShowKeybindsConfig.class, parent).get();
    }
}
