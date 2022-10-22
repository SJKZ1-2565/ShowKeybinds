package sjkz1.com.cheesy_slot.forge;

import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sjkz1.com.cheesy_slot.CheesySlot;
import sjkz1.com.cheesy_slot.config.CheesySlotConfig;

@Mod(CheesySlot.MOD_ID)
public class CheesySlotForge {
    public CheesySlotForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(CheesySlot.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> AutoConfig.getConfigScreen(CheesySlotConfig.class, screen).get()));
        CheesySlot.init();
    }
}