package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T>
{

    private static final int[] SURVIVAL_SLOT_Y = {142, 143, 197, 109, 195};
    private static final int[] CREATIVE_SLOT_Y = {112, 142, 20};
    private static final int[] SURVIVAL_SLOT_X = {8, 26, 44, 62, 80, 98, 116, 134, 152};
    private static final int[] CREATIVE_SLOT_X = {9, 27, 45, 63, 81, 99, 117, 135, 153, 35};
    private static final int SURVIVAL_OFFHAND_X = 77;
    private static final int SURVIVAL_OFFHAND_Y = 62;
    @Shadow
    @Final
    protected T menu;

    protected MixinAbstractContainerScreen()
    {
        super(null);
    }

    @Inject(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;extractSlots(Lnet/minecraft/client/gui/GuiGraphicsExtractor;II)V", shift = At.Shift.AFTER))
    public void showKeybinds$extractKeybinding(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci)
    {
        this.showkeybinds$extractKeybinding(graphics);
    }

    @Unique
    private void showkeybinds$extractKeybinding(final GuiGraphicsExtractor graphics)
    {
        for (Slot slot : this.menu.slots)
        {

            if (!Showkeybinds.CONFIG.container.enableContainerText) return;

            Minecraft mc = Minecraft.getInstance();
            Screen screen = mc.screen;
            KeyMapping[] keyMappingList = mc.options.keyHotbarSlots;
            KeyMapping offhandKey = mc.options.keySwapOffhand;

            float scale = Showkeybinds.CONFIG.container.containerScale;
            int textYOffsets = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            boolean showOffHandText = Showkeybinds.CONFIG.general.offHandText;

            int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
            int containerColor = Showkeybinds.CONFIG.container.rainBowText
                    ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                    : Showkeybinds.CONFIG.container.containerTextColor;

            boolean isCreativeOrMerchant = screen instanceof CreativeModeInventoryScreen
                    || screen instanceof MerchantScreen;

            int[] validSlotY = isCreativeOrMerchant ? CREATIVE_SLOT_Y : SURVIVAL_SLOT_Y;
            int[] slotX = isCreativeOrMerchant ? CREATIVE_SLOT_X : SURVIVAL_SLOT_X;

            boolean isSpecialSlotY = false;
            for (int y : validSlotY)
            {
                if (slot.y == y)
                {
                    isSpecialSlotY = true;
                    break;
                }
            }

            graphics.pose().pushMatrix();
            graphics.pose().scale(scale, scale);

            if (isSpecialSlotY)
            {
                for (int index = 0; index < slotX.length; index++)
                {
                    if (slot.x == slotX[index])
                    {
                        boolean isOffhandSlot = index >= 9;

                        if (isOffhandSlot && !showOffHandText) break;

                        Component keyMessage = isOffhandSlot
                                ? offhandKey.getTranslatedKeyMessage()
                                : keyMappingList[index].getTranslatedKeyMessage();

                        graphics.text(this.font,
                                keyMessage,
                                (int) (slot.x / scale),
                                (int) (slot.y / scale) + textYOffsets,
                                containerColor,
                                Showkeybinds.CONFIG.container.shadowedText);
                        break;
                    }
                }
            }

            // Survival offhand slot (fixed position)
            if (slot.x == SURVIVAL_OFFHAND_X && slot.y == SURVIVAL_OFFHAND_Y && showOffHandText)
            {
                graphics.text(this.font,
                        offhandKey.getTranslatedKeyMessage(),
                        (int) (slot.x / scale),
                        (int) (slot.y / scale) + textYOffsets,
                        containerColor,
                        Showkeybinds.CONFIG.container.shadowedText);
            }

            // Debug mode
            if (Showkeybinds.DEBUG)
            {
                graphics.text(this.font,
                        String.valueOf(slot.x),
                        (int) (slot.x / scale),
                        (int) (slot.y / scale) + textYOffsets,
                        containerColor,
                        Showkeybinds.CONFIG.container.shadowedText);
            }

            graphics.pose().popMatrix();
        }
    }
}
