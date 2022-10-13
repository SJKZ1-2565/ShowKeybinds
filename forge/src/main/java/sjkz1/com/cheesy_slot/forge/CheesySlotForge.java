package sjkz1.com.cheesy_slot.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sjkz1.com.cheesy_slot.CheesySlot;

@Mod(CheesySlot.MOD_ID)
public class CheesySlotForge {
    public CheesySlotForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(CheesySlot.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        CheesySlot.init();
    }
}