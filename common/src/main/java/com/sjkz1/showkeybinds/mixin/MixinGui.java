package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Arrays;

@Mixin(Gui.class)
public abstract class MixinGui {


    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Shadow
    public abstract Font getFont();

    @Inject(method = "renderItemHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Showkeybinds.CONFIG.general.enableHotBarText) {
            var mat = new Matrix3x2f();
            Player player = this.getCameraPlayer();
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0f, 0f);
            float scale = Showkeybinds.CONFIG.general.hotBarScale;
            guiGraphics.pose().scale(scale, scale);
            int i = guiGraphics.guiWidth() / 2;
            ItemStack itemStack = player.getOffhandItem();
            var humanoidArm = player.getMainArm().getOpposite();
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            var offHandKey = Minecraft.getInstance().options.keySwapOffhand;
            int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F));
            var hotBarColor = Showkeybinds.CONFIG.general.rainBowText ? rainbow : Showkeybinds.CONFIG.general.hotBarTextColor;

            for (int j = 0; j < Arrays.stream(list).toList().size(); j++) {
                final var v = (i - 92 - 15 + (j + 1) * 20) / scale;
                int textY = player.inventoryMenu.slots.get(j + 36).getItem().is(Items.LIGHT) ? 7 : 0;
                guiGraphics.drawString(this.getFont(),
                        Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(),
                        (int) v,
                        (int) ((guiGraphics.guiHeight() - (21) + 3 + textY) / scale),
                        hotBarColor,
                        Showkeybinds.CONFIG.general.shadowedText);
            }
            if (!itemStack.isEmpty() && Showkeybinds.CONFIG.general.offHandText) {
                int textY = player.inventoryMenu.slots.get(45).getItem().is(Items.LIGHT) ? 8 : 0;
                if (humanoidArm == HumanoidArm.LEFT) {
                    guiGraphics.drawString(this.getFont(),
                            offHandKey.getTranslatedKeyMessage(),
                            (int) ((i - 87 - 29) / scale),
                            (int) ((guiGraphics.guiHeight() - (19) + textY) / scale),
                            hotBarColor,
                            Showkeybinds.CONFIG.general.shadowedText);
                } else {
                    guiGraphics.drawString(this.getFont(),
                            offHandKey.getTranslatedKeyMessage(),
                            (int) ((i + 102) / scale),
                            (int) ((guiGraphics.guiHeight() - (19) + textY) / scale),
                            hotBarColor,
                            Showkeybinds.CONFIG.general.shadowedText);
                }
            }
            guiGraphics.pose().popMatrix();
        }

    }
}
