package sjkz1.com.cheesy_slot;

import com.google.gson.JsonParser;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import sjkz1.com.cheesy_slot.config.CheesySlotConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CheesySlot {
    public static final String MOD_ID = "cheesy_slot";
    public static final String MOD_NAME = "Cheesy Slot";
    public static final String VERSION = "2.0.0";
    public static CheesySlotConfig CONFIG;

    public static void init() {
        AutoConfig.register(CheesySlotConfig.class, GsonConfigSerializer::new);
        CheesySlot.CONFIG = AutoConfig.getConfigHolder(CheesySlotConfig.class).getConfig();
    }

    public static String getVersion() throws IOException {
        URL url1 = new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/cheesy_slot.json");
        InputStreamReader reader1 = new InputStreamReader(url1.openStream());
        return JsonParser.parseReader(reader1).getAsJsonObject().get("1.19_latest").getAsString();
    }
}