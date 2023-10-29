package sjkz1.com.show_keybinds;

import com.google.gson.JsonParser;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import sjkz1.com.show_keybinds.config.ShowKeybindsConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ShowKeybinds {
    public static final String MOD_ID = "show_keybinds";
    public static final String MOD_NAME = "Show Keybinds";
    public static final String VERSION = "3.2.0";
    public static ShowKeybindsConfig CONFIG;

    public static void init() {

    }

    public static void registerConfig() {
        AutoConfig.register(ShowKeybindsConfig.class, GsonConfigSerializer::new);
        ShowKeybinds.CONFIG = AutoConfig.getConfigHolder(ShowKeybindsConfig.class).getConfig();
    }


    public static String getVersion() throws IOException {
        URL url1 = new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/show_keybinds.json");
        InputStreamReader reader1 = new InputStreamReader(url1.openStream());
        return JsonParser.parseReader(reader1).getAsJsonObject().get("1.20_latest").getAsString();
    }
}