package sjkz1.com.chessy_slot.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sjkz1.com.chessy_slot.Chessy_slot;

@Mod(Chessy_slot.MOD_ID)
public class Chessy_slotForge {
    public Chessy_slotForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Chessy_slot.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Chessy_slot.init();
    }
}