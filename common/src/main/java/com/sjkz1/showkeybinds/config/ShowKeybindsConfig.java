package com.sjkz1.showkeybinds.config;

import com.sjkz1.showkeybinds.Showkeybinds;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Showkeybinds.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/cherry_planks.png")
public final class ShowKeybindsConfig implements ConfigData
{
    @ConfigEntry.Category("hotbar")
    @ConfigEntry.Gui.TransitiveObject
    public General general;
    @ConfigEntry.Category("container")
    @ConfigEntry.Gui.TransitiveObject
    public Container container;

    public ShowKeybindsConfig()
    {
        this.general = new General();
        this.container = new Container();
    }

    public static class General
    {
        public boolean enableHotBarText = true;
        public boolean offHandText = false;
        public boolean shadowedText = true;
        public boolean rainBowText = false;
        @ConfigEntry.ColorPicker(allowAlpha = true)
        public int hotBarTextColor = 0xFFFFFFFF;
        @ConfigEntry.ColorPicker(allowAlpha = true)
        public int itemCountColor = 0xFFFFFFFF;
        public boolean enableItemCountColor = false;
        public boolean rainBowItemText = false;
        public float keySlot1Scale = 1.0F;
        public float keySlot2Scale = 1.0F;
        public float keySlot3Scale = 1.0F;
        public float keySlot4Scale = 1.0F;
        public float keySlot5Scale = 1.0F;
        public float keySlot6Scale = 1.0F;
        public float keySlot7Scale = 1.0F;
        public float keySlot8Scale = 1.0F;
        public float keySlot9Scale = 1.0F;
        public float offHandKeySlotScale = 1.0F;
    }


    public static class Container
    {
        public boolean enableContainerText = true;
        public boolean shadowedText = true;
        public boolean rainBowText = false;
        @ConfigEntry.ColorPicker(allowAlpha = true)
        public int containerTextColor = 0xFFFF55FF;
        @Comment("Recommend min = 0.5,max = 1.0")
        public float containerScale = 1.0f;
    }
}
