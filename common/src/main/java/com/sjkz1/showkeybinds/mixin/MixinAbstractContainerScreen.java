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
public abstract class MixinAbstractContainerScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {


    protected MixinAbstractContainerScreen() {
        super(null);
    }

    @Inject(method = "renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;II)V", at = @At(value = "TAIL"))
    public void showKeybinds$renderSlot(GuiGraphics guiGraphics, Slot slot, int moseX, int mouseY, CallbackInfo ci) {
        if (Showkeybinds.CONFIG.container.enableContainerText) {
            Screen screen = Minecraft.getInstance().screen;
            KeyMapping[] keyMappingList = Minecraft.getInstance().options.keyHotbarSlots;
            float scale = Showkeybinds.CONFIG.container.containerScale;
            int textYOffsets = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500F, 0.8F, 0.8F));
            int containerColor = Showkeybinds.CONFIG.container.rainBowText ? ARGB.color(ARGB.red(rainbow), ARGB.green(rainbow), ARGB.blue(rainbow)) : Showkeybinds.CONFIG.container.containerTextColor;
            boolean showOffHandText = Showkeybinds.CONFIG.general.offHandText;
            boolean isCreativeOrMerchantScreen = screen instanceof CreativeModeInventoryScreen || screen instanceof MerchantScreen;
            boolean isSpecialSlotY = isCreativeOrMerchantScreen ? (slot.y == 112 || slot.y == 142 || slot.y == 20) : (slot.y == 142 || slot.y == 143 || slot.y == 197 || slot.y == 109 || slot.y == 195);

            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0f, 0f);
            guiGraphics.pose().scale(scale, scale);

            if (isSpecialSlotY) {
                int[] slotX = isCreativeOrMerchantScreen
                        ? new int[]{9, 27, 45, 63, 81, 99, 117, 135, 153, 35}
                        : new int[]{8, 26, 44, 62, 80, 98, 116, 134, 152};

                for (int index = 0; index < slotX.length; index++) {
                    if (slot.x == slotX[index]) {
                        Component keyMessage = index < 9 ? keyMappingList[index].getTranslatedKeyMessage() : Minecraft.getInstance().options.keySwapOffhand.getTranslatedKeyMessage();
                        guiGraphics.drawString(this.font,
                                keyMessage,
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textYOffsets,
                                containerColor,
                                Showkeybinds.CONFIG.container.shadowedText);
                    }
                }
            }
            //This fixed off-hand key render when it's survival inventory
            if (slot.x == 77 && slot.y == 62 && showOffHandText) {
                Component keyMessage = Minecraft.getInstance().options.keySwapOffhand.getTranslatedKeyMessage();
                guiGraphics.drawString(this.font,
                        keyMessage,
                        (int) (slot.x / scale),
                        (int) ((slot.y) / scale) + textYOffsets,
                        containerColor,
                        Showkeybinds.CONFIG.container.shadowedText);
            }
            if (Showkeybinds.DEBUG) {
                guiGraphics.drawString(this.font,
                        String.valueOf(slot.x),
                        (int) (slot.x / scale),
                        (int) ((slot.y) / scale) + textYOffsets,
                        containerColor,
                        Showkeybinds.CONFIG.container.shadowedText);
            }

            guiGraphics.pose().popMatrix();
        }

    }
}
