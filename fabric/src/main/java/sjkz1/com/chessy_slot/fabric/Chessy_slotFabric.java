package sjkz1.com.chessy_slot.fabric;

import sjkz1.com.chessy_slot.Chessy_slot;
import net.fabricmc.api.ModInitializer;

public class Chessy_slotFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chessy_slot.init();
    }
}