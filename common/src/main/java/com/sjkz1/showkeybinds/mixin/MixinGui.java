package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public abstract class MixinGui
{


    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Shadow
    public abstract Font getFont();

    @Inject(method = "renderItemHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci)
    {
        if (Showkeybinds.CONFIG.general.enableHotBarText)
        {

            Player player = this.getCameraPlayer();
            float scale = Showkeybinds.CONFIG.general.hotBarScale;
            int width = guiGraphics.guiWidth() / 2;
            ItemStack itemStack = player.getOffhandItem();
            HumanoidArm humanoidArm = player.getMainArm().getOpposite();
            KeyMapping[] keyMappingList = Minecraft.getInstance().options.keyHotbarSlots;
            KeyMapping offHandKey = Minecraft.getInstance().options.keySwapOffhand;
            int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
            int hotBarColor = Showkeybinds.CONFIG.general.rainBowText
                    ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                    : Showkeybinds.CONFIG.general.hotBarTextColor;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().scale(scale, scale);

            for (int index = 0; index < keyMappingList.length; index++)
            {
                KeyMapping key = keyMappingList[index];
                float widthOffsets = (width - 92 - 15 + (index + 1) * 20) / scale;
                int textY = player.inventoryMenu.slots.get(index + 36).getItem().is(Items.LIGHT) ? 7 : 0;
                guiGraphics.drawString(this.getFont(),
                        key.getTranslatedKeyMessage(),
                        (int) widthOffsets,
                        (int) ((guiGraphics.guiHeight() - (21) + 3 + textY) / scale),
                        hotBarColor,
                        Showkeybinds.CONFIG.general.shadowedText);
            }
            if (!itemStack.isEmpty() && Showkeybinds.CONFIG.general.offHandText)
            {
                int textY = player.inventoryMenu.slots.get(45).getItem().is(Items.LIGHT) ? 8 : 0;
                if (humanoidArm == HumanoidArm.LEFT)
                {
                    guiGraphics.drawString(this.getFont(),
                            offHandKey.getTranslatedKeyMessage(),
                            (int) ((width - 87 - 29) / scale),
                            (int) ((guiGraphics.guiHeight() - (19) + textY) / scale),
                            hotBarColor,
                            Showkeybinds.CONFIG.general.shadowedText);
                } else
                {
                    guiGraphics.drawString(this.getFont(),
                            offHandKey.getTranslatedKeyMessage(),
                            (int) ((width + 102) / scale),
                            (int) ((guiGraphics.guiHeight() - (19) + textY) / scale),
                            hotBarColor,
                            Showkeybinds.CONFIG.general.shadowedText);
                }
            }
            guiGraphics.pose().popMatrix();
        }

    }
}
