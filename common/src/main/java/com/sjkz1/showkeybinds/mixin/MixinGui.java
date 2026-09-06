package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import com.sjkz1.showkeybinds.utils.WordUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Hud.class)
public abstract class MixinGui
{

    @Unique
    private static float showkeybinds$getScaleForIndex(int index)
    {
        return switch (index)
        {
            case 0 -> Showkeybinds.CONFIG.general.keySlot1Scale;
            case 1 -> Showkeybinds.CONFIG.general.keySlot2Scale;
            case 2 -> Showkeybinds.CONFIG.general.keySlot3Scale;
            case 3 -> Showkeybinds.CONFIG.general.keySlot4Scale;
            case 4 -> Showkeybinds.CONFIG.general.keySlot5Scale;
            case 5 -> Showkeybinds.CONFIG.general.keySlot6Scale;
            case 6 -> Showkeybinds.CONFIG.general.keySlot7Scale;
            case 7 -> Showkeybinds.CONFIG.general.keySlot8Scale;
            case 8 -> Showkeybinds.CONFIG.general.keySlot9Scale;
            // Defensive fallback — keyMappingList.length should always be 9.
            default -> 1.0f;
        };
    }

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Shadow
    public abstract Font getFont();

    @Inject(method = "extractItemHotbar", at = @At(value = "TAIL"))
    public void showkeybinds$extractItemHotbar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci)
    {
        Player player = getCameraPlayer();
        if (player == null)
        {
            return;
        }

        boolean drawHotbar = Showkeybinds.CONFIG.general.enableHotBarText;
        boolean drawOffhand = Showkeybinds.CONFIG.general.offHandText;
        if (!drawHotbar && !drawOffhand)
        {
            return;
        }


        KeyMapping[] keyMappingList = Minecraft.getInstance().options.keyHotbarSlots;
        int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
        int hotBarColor = Showkeybinds.CONFIG.general.rainBowText
                ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                : Showkeybinds.CONFIG.general.hotBarTextColor;
        int width = graphics.guiWidth() / 2;

        if (drawHotbar)
        {
            for (int j = 0; j < keyMappingList.length; j++)
            {
                this.showkeybinds$extractHotBarKey(graphics, j, width, hotBarColor, player);
            }
        }

        if (drawOffhand)
        {
            this.showkeybinds$extractOffHandKey(graphics, hotBarColor, width, player);
        }
    }

    @Unique
    private void showkeybinds$extractHotBarKey(GuiGraphicsExtractor graphics, int index, int width, int hotBarColor, Player player)
    {
        float scale = showkeybinds$getScaleForIndex(index);
        KeyMapping[] keyMappingList = Minecraft.getInstance().options.keyHotbarSlots;
        KeyMapping key = keyMappingList[index];

        int textY = player.inventoryMenu.slots.get(index + 36).getItem().is(Items.LIGHT) ? 7 : 0;
        String keyText = WordUtils.capitalize(WordUtils.convert(key.getTranslatedKeyMessage().getString()));

        graphics.pose().pushMatrix();
        graphics.pose().scale(scale, scale);
        graphics.text(this.getFont(),
                keyText,
                (int) ((width - 92 - 15 + (index + 1) * 20) / scale),
                (int) ((graphics.guiHeight() - 21 + 3 + textY) / scale),
                hotBarColor,
                Showkeybinds.CONFIG.general.shadowedText);
        graphics.pose().popMatrix();
    }

    @Unique
    private void showkeybinds$extractOffHandKey(GuiGraphicsExtractor graphics, int hotBarColor,
                                                int width, Player player)
    {
        ItemStack itemStack = player.getOffhandItem();

        if (itemStack.isEmpty())
        {
            return;
        }

        HumanoidArm humanoidArm = player.getMainArm().getOpposite();
        KeyMapping offHandKey = Minecraft.getInstance().options.keySwapOffhand;

        float offhandScale = Showkeybinds.CONFIG.general.offHandKeySlotScale;
        int offHandTextY = player.inventoryMenu.slots.get(45).getItem().is(Items.LIGHT) ? 8 : 0;
        String offHandKeyText = WordUtils.capitalize(WordUtils.convert(offHandKey.getTranslatedKeyMessage().getString()));

        graphics.pose().pushMatrix();
        graphics.pose().scale(offhandScale, offhandScale);

        if (humanoidArm == HumanoidArm.LEFT)
        {
            graphics.text(this.getFont(),
                    offHandKeyText,
                    (int) ((width - 87 - 29) / offhandScale),
                    (int) ((graphics.guiHeight() - 19 + offHandTextY) / offhandScale),
                    hotBarColor,
                    Showkeybinds.CONFIG.general.shadowedText);
        }
        else
        {
            graphics.text(this.getFont(),
                    offHandKeyText,
                    (int) ((width + 102) / offhandScale),
                    (int) ((graphics.guiHeight() - 19 + offHandTextY) / offhandScale),
                    hotBarColor,
                    Showkeybinds.CONFIG.general.shadowedText);
        }

        graphics.pose().popMatrix();
    }
}