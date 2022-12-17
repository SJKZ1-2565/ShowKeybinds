package sjkz1.com.cheesy_slot.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import sjkz1.com.cheesy_slot.CheesySlot;

@Config(name = CheesySlot.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/bamboo_mosaic.png")
public final class CheesySlotConfig implements ConfigData {
    @ConfigEntry.Category("hotbar")
    @ConfigEntry.Gui.TransitiveObject
    public General general;
    @ConfigEntry.Category("container")
    @ConfigEntry.Gui.TransitiveObject
    public Container container;

    public CheesySlotConfig() {
        this.general = new General();
        this.container = new Container();
    }

    public static class General {
        public boolean loggedInToasts = true;
        public int hotBarHeight = 0;
        public boolean enableHotBarText = true;
        public boolean shadowedText = true;
        public boolean rainBowText = false;
        public long rainbowColorSpeed = 2500L;
        @ConfigEntry.ColorPicker
        public int hotBarTextColor = 0xFFFFFF;
        @Comment("Recommend min = 0.5,max = 1.0\nJust recommend...")
        public float hotBarScale = 1.0f;
    }

    public static class Container {
        public boolean enableContainerText = true;
        public boolean shadowedText = true;
        public boolean rainBowText = false;
        @ConfigEntry.ColorPicker
        public int containerTextColor = 0xFFFF55;
        @Comment("Recommend min = 0.5,max = 1.0\nJust recommend...")
        public float containerScale = 1.0f;
    }
}
