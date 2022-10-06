package sjkz1.com.chessy_slot.fabric;

import net.fabricmc.api.ModInitializer;
import sjkz1.com.chessy_slot.Chessy_slot;

public class Chessy_slotFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chessy_slot.init();
    }
}