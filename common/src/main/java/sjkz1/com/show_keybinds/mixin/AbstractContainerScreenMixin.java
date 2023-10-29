package sjkz1.com.show_keybinds.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.show_keybinds.ShowKeybinds;

import java.awt.*;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {

    protected AbstractContainerScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "renderSlot", at = @At(value = "TAIL"))
    public void renderSlot(GuiGraphics guiGraphics, Slot slot, CallbackInfo ci) {
        if (ShowKeybinds.CONFIG.container.enableContainerText) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0f, 0f, 350f);
            float scale = ShowKeybinds.CONFIG.container.containerScale;
            guiGraphics.pose().scale(scale, scale, scale);
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            int textY = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            var screen = this.minecraft.screen;
            int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500F, 0.8F,0.8F));
            var containerColor = ShowKeybinds.CONFIG.container.rainBowText ? rainbow : ShowKeybinds.CONFIG.container.containerTextColor;
            if (!(screen instanceof CreativeModeInventoryScreen || screen instanceof MerchantScreen)) {
                if (slot.y == 142 || slot.y == 143 || slot.y == 197 || slot.y == 109 || slot.y == 195) {
                    switch (slot.x) {
                        case 8, 36 -> guiGraphics.drawString(this.font,
                                list[0].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 26, 54 -> guiGraphics.drawString(this.font,
                                list[1].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 44, 72 -> guiGraphics.drawString(this.font,
                                list[2].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 62, 90 -> guiGraphics.drawString(this.font,
                                list[3].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 80, 108 -> guiGraphics.drawString(this.font,
                                list[4].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 98, 126 -> guiGraphics.drawString(this.font,
                                list[5].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 116, 144 -> guiGraphics.drawString(this.font,
                                list[6].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 134, 162 -> guiGraphics.drawString(this.font,
                                list[7].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 152, 180 -> guiGraphics.drawString(this.font,
                                list[8].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                    }
                }
            } else {
                if (slot.y == 112 || slot.y == 142 || slot.y == 20) {
                    switch (slot.x) {
                        case 9, 108 -> guiGraphics.drawString(this.font,
                                list[0].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 27, 126 -> guiGraphics.drawString(this.font,
                                list[1].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 45, 144 -> guiGraphics.drawString(this.font,
                                list[2].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 63, 162 -> guiGraphics.drawString(this.font,
                                list[3].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 81, 180 -> guiGraphics.drawString(this.font,
                                list[4].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 99, 198 -> guiGraphics.drawString(this.font,
                                list[5].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 117, 216 -> guiGraphics.drawString(this.font,
                                list[6].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 135, 234 -> guiGraphics.drawString(this.font,
                                list[7].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 153, 252 -> guiGraphics.drawString(this.font,
                                list[8].getTranslatedKeyMessage(),
                                (int) (slot.x / scale),
                                (int) ((slot.y) / scale) + textY,
                                containerColor,
                                ShowKeybinds.CONFIG.container.shadowedText);
                        case 35 ->
                                guiGraphics.drawString(this.font,
                                        Minecraft.getInstance().options.keySwapOffhand.getTranslatedKeyMessage(),
                                        (int) (slot.x / scale),
                                        (int) ((slot.y) / scale) + textY,
                                        containerColor,
                                        ShowKeybinds.CONFIG.container.shadowedText);
                    }
                }

            }
            guiGraphics.pose().popPose();
        }
    }
}

