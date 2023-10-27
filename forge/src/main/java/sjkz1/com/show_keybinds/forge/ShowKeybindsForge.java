package sjkz1.com.show_keybinds.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sjkz1.com.show_keybinds.ShowKeybinds;

@Mod(ShowKeybinds.MOD_ID)
public class ShowKeybindsForge {
    public ShowKeybindsForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ShowKeybinds.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
//        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> AutoConfig.getConfigScreen(ShowKeybindsConfig.class, screen).get()));
//        ShowKeybinds.init();
    }
}