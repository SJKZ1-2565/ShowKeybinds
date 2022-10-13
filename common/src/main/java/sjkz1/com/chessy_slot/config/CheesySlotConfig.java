package sjkz1.com.chessy_slot.config;

import blue.endless.jankson.Comment;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import sjkz1.com.chessy_slot.Chessy_slot;

@Config(name = Chessy_slot.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/structure_block_corner.png")
public final class CheesySlotConfig implements ConfigData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general;

    public CheesySlotConfig()
    {
        this.general = new General();
    }

    public static class General
    {
        @ConfigEntry.Gui.Tooltip
        @Comment("Rainbow color")
        public boolean rainbowColor = false;
    }
}
