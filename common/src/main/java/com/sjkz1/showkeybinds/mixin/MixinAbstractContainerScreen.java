package com.sjkz1.showkeybinds.mixin;

import com.google.common.collect.Maps;
import com.sjkz1.showkeybinds.Showkeybinds;
import com.sjkz1.showkeybinds.utils.WordUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Map;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T>
{
    @Unique
    private static final int[] SURVIVAL_SLOT_Y = {142, 143, 197, 109, 195};
    @Unique
    private static final int[] CREATIVE_SLOT_Y = {112, 142, 20};
    @Unique
    private static final int[] SURVIVAL_SLOT_X = {8, 26, 44, 62, 80, 98, 116, 134, 152};
    @Unique
    private static final int[] CREATIVE_SLOT_X = {9, 27, 45, 63, 81, 99, 117, 135, 153, 35};
    @Unique
    private static final int SURVIVAL_OFFHAND_X = 77;
    @Unique
    private static final int SURVIVAL_OFFHAND_Y = 62;

    @Unique
    private static Map<Integer, Float> showkeybinds$creativeKeyIndexScaleMap = Maps.newHashMap();
    @Unique
    private static Map<Integer, Float> showkeybinds$survivalKeyIndexScaleMap = Maps.newHashMap();

    @Shadow
    @Final
    protected T menu;

    protected MixinAbstractContainerScreen()
    {
        super(null);
    }

    @Unique
    private static void showkeybinds$loadScale()
    {
        showkeybinds$creativeKeyIndexScaleMap.put(0, Showkeybinds.CONFIG.general.keySlot1Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(1, Showkeybinds.CONFIG.general.keySlot2Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(2, Showkeybinds.CONFIG.general.keySlot3Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(3, Showkeybinds.CONFIG.general.keySlot4Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(4, Showkeybinds.CONFIG.general.keySlot5Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(5, Showkeybinds.CONFIG.general.keySlot6Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(6, Showkeybinds.CONFIG.general.keySlot7Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(7, Showkeybinds.CONFIG.general.keySlot8Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(8, Showkeybinds.CONFIG.general.keySlot9Scale);
        showkeybinds$creativeKeyIndexScaleMap.put(9, Showkeybinds.CONFIG.general.offHandKeySlotScale);

        showkeybinds$survivalKeyIndexScaleMap.put(0, Showkeybinds.CONFIG.general.keySlot1Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(1, Showkeybinds.CONFIG.general.keySlot2Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(2, Showkeybinds.CONFIG.general.keySlot3Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(3, Showkeybinds.CONFIG.general.keySlot4Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(4, Showkeybinds.CONFIG.general.keySlot5Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(5, Showkeybinds.CONFIG.general.keySlot6Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(6, Showkeybinds.CONFIG.general.keySlot7Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(7, Showkeybinds.CONFIG.general.keySlot8Scale);
        showkeybinds$survivalKeyIndexScaleMap.put(8, Showkeybinds.CONFIG.general.keySlot9Scale);
    }

    @Inject(method = "renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;II)V", at = @At(value = "TAIL"))
    public void showKeybinds$renderSlot(GuiGraphics guiGraphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci)
    {
        if (!Showkeybinds.CONFIG.container.enableContainerText) return;

        Minecraft mc = Minecraft.getInstance();
        Screen screen = mc.screen;
        showkeybinds$loadScale();
        boolean isCreativeOrMerchant = screen instanceof CreativeModeInventoryScreen
                || screen instanceof MerchantScreen;
        int[] slotX = isCreativeOrMerchant ? CREATIVE_SLOT_X : SURVIVAL_SLOT_X;

        for (int index = 0; index < slotX.length; index++)
        {
            this.showkeybinds$drawSlotKeybind(guiGraphics, index, isCreativeOrMerchant);
        }

        if (!isCreativeOrMerchant && Showkeybinds.CONFIG.general.offHandText)
        {
            this.showkeybinds$drawSurvivalOffhand(guiGraphics);
        }

        if (Showkeybinds.DEBUG)
        {
            int textYOffsets = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
            int debugColor = Showkeybinds.CONFIG.container.rainBowText
                    ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                    : Showkeybinds.CONFIG.container.containerTextColor;
            guiGraphics.drawString(this.font,
                    String.valueOf(slot.x),
                    slot.x,
                    slot.y + textYOffsets,
                    debugColor,
                    Showkeybinds.CONFIG.container.shadowedText);
        }
    }

    @Unique
    private void showkeybinds$drawSlotKeybind(GuiGraphics graphics, int index, boolean isCreativeOrMerchant)
    {
        Minecraft mc = Minecraft.getInstance();
        KeyMapping[] keyMappingList = mc.options.keyHotbarSlots;
        KeyMapping offhandKey = mc.options.keySwapOffhand;
        boolean isOffhandIndex = isCreativeOrMerchant && index >= 9;
        if (isOffhandIndex && !Showkeybinds.CONFIG.general.offHandText) return;

        float scale = isCreativeOrMerchant
                ? showkeybinds$creativeKeyIndexScaleMap.get(index)
                : showkeybinds$survivalKeyIndexScaleMap.get(index);

        int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
        int containerColor = Showkeybinds.CONFIG.container.rainBowText
                ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                : Showkeybinds.CONFIG.container.containerTextColor;


        int[] validSlotY = isCreativeOrMerchant ? CREATIVE_SLOT_Y : SURVIVAL_SLOT_Y;
        int[] slotX = isCreativeOrMerchant ? CREATIVE_SLOT_X : SURVIVAL_SLOT_X;

        for (Slot slot : this.menu.slots)
        {
            boolean isValidY = false;
            for (int y : validSlotY)
            {
                if (slot.y == y)
                {
                    isValidY = true;
                    break;
                }
            }

            if (!isValidY || slot.x != slotX[index]) continue;

            int textYOffsets = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            Component keyMessage = isOffhandIndex
                    ? offhandKey.getTranslatedKeyMessage()
                    : keyMappingList[index].getTranslatedKeyMessage();
            String key = WordUtils.capitalize(WordUtils.convert(keyMessage.getString()));

            graphics.pose().pushMatrix();
            graphics.pose().scale(scale, scale);
            graphics.drawString(this.font,
                    key,
                    (int) (slot.x / scale),
                    (int) (slot.y / scale) + textYOffsets,
                    containerColor,
                    Showkeybinds.CONFIG.container.shadowedText);
            graphics.pose().popMatrix();
        }
    }

    @Unique
    private void showkeybinds$drawSurvivalOffhand(GuiGraphics graphics)
    {
        // FIX 4: Original used (index-based) `scale` for pose().scale() but
        // `offHandKeySlotScale` for coordinate division — a mismatched pair that
        // distorted text placement. Both now consistently use offHandKeySlotScale.
        float offhandScale = Showkeybinds.CONFIG.general.offHandKeySlotScale;
        KeyMapping offhandKey = Minecraft.getInstance().options.keySwapOffhand;

        int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
        int containerColor = Showkeybinds.CONFIG.container.rainBowText
                ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                : Showkeybinds.CONFIG.container.containerTextColor;

        for (Slot slot : this.menu.slots)
        {
            if (slot.x != SURVIVAL_OFFHAND_X || slot.y != SURVIVAL_OFFHAND_Y) continue;

            int textYOffsets = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            String key = WordUtils.capitalize(WordUtils.convert(offhandKey.getTranslatedKeyMessage().getString()));

            graphics.pose().pushMatrix();
            graphics.pose().scale(offhandScale, offhandScale);
            graphics.drawString(this.font,
                    key,
                    (int) (slot.x / offhandScale),
                    (int) (slot.y / offhandScale) + textYOffsets,
                    containerColor,
                    Showkeybinds.CONFIG.container.shadowedText);
            graphics.pose().popMatrix();
            break; // Only one offhand slot exists; stop scanning.
        }
    }
}
