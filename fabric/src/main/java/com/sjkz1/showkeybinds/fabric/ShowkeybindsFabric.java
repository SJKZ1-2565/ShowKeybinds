package com.sjkz1.showkeybinds.fabric;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.fabricmc.api.ModInitializer;

public final class ShowkeybindsFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        Showkeybinds.init();
        Showkeybinds.registerConfig();
    }
}
