package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
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
import org.spongepowered.asm.mixin.Mixin;
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

    protected MixinAbstractContainerScreen()
    {
        super(null);
    }

    @Inject(method = "renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;II)V", at = @At(value = "TAIL")
    )
    public void showKeybinds$renderSlot(GuiGraphics guiGraphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci)
    {
        if (!Showkeybinds.CONFIG.container.enableContainerText) return;

        // Cache Minecraft instance ครั้งเดียว
        Minecraft mc = Minecraft.getInstance();
        Screen screen = mc.screen;
        KeyMapping[] keyMappingList = mc.options.keyHotbarSlots;
        KeyMapping offhandKey = mc.options.keySwapOffhand;

        float scale = Showkeybinds.CONFIG.container.containerScale;
        int textYOffsets = slot.getItem().is(Items.LIGHT) ? 8 : 0;
        boolean showOffHandText = Showkeybinds.CONFIG.general.offHandText;

        // FIX: ไม่ใช้ Math.abs() + ใส่ alpha=255 อย่างชัดเจน
        int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
        int containerColor = Showkeybinds.CONFIG.container.rainBowText
                ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                : Showkeybinds.CONFIG.container.containerTextColor;

        boolean isCreativeOrMerchant = screen instanceof CreativeModeInventoryScreen
                || screen instanceof MerchantScreen;

        // เลือก slot Y/X ตาม screen type
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

        guiGraphics.pose().pushMatrix();
        // FIX: ลบ translate(0,0) ที่ไม่มีผล
        guiGraphics.pose().scale(scale, scale);

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

                    guiGraphics.drawString(this.font,
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
            guiGraphics.drawString(this.font,
                    offhandKey.getTranslatedKeyMessage(),
                    (int) (slot.x / scale),
                    (int) (slot.y / scale) + textYOffsets,
                    containerColor,
                    Showkeybinds.CONFIG.container.shadowedText);
        }

        // Debug mode
        if (Showkeybinds.DEBUG)
        {
            guiGraphics.drawString(this.font,
                    String.valueOf(slot.x),
                    (int) (slot.x / scale),
                    (int) (slot.y / scale) + textYOffsets,
                    containerColor,
                    Showkeybinds.CONFIG.container.shadowedText);
        }

        guiGraphics.pose().popMatrix();
    }
}
