package sjkz1.com.cheesy_slot.fabric;

import net.fabricmc.api.ModInitializer;
import sjkz1.com.cheesy_slot.CheesySlot;

public class CheesySlotFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CheesySlot.init();
    }
}