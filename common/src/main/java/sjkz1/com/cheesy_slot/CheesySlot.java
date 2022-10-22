package sjkz1.com.cheesy_slot;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import sjkz1.com.cheesy_slot.config.CheesySlotConfig;

public class CheesySlot {
    public static final String MOD_ID = "cheesy_slot";
    public static CheesySlotConfig CONFIG;

    public static void init() {
        AutoConfig.register(CheesySlotConfig.class, GsonConfigSerializer::new);
        CheesySlot.CONFIG = AutoConfig.getConfigHolder(CheesySlotConfig.class).getConfig();
    }
}