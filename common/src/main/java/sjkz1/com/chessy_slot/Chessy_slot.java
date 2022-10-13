package sjkz1.com.chessy_slot;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import sjkz1.com.chessy_slot.config.CheesySlotConfig;

public class Chessy_slot {
    public static final String MOD_ID = "chessy_slot";

    public static void init() {
        AutoConfig.register(CheesySlotConfig.class, GsonConfigSerializer::new);
    }
}