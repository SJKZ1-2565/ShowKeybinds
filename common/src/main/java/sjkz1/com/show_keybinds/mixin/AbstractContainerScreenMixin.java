package sjkz1.com.show_keybinds.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
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
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu>
        extends Screen
        implements MenuAccess<T> {

    protected AbstractContainerScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "renderSlot", at = @At(value = "TAIL"))
    public void renderSlot(PoseStack poseStack, Slot slot, CallbackInfo ci) {
        if (ShowKeybinds.CONFIG.container.enableContainerText) {
            poseStack.pushPose();
            poseStack.translate(0f, 0f, this.getBlitOffset() + 350f);
            float scale = ShowKeybinds.CONFIG.container.containerScale;
            poseStack.scale(scale, scale, scale);
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            int textY = slot.getItem().is(Items.LIGHT) ? 8 : 0;
            var screen = this.minecraft.screen;
            int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F));
            var containerColor = ShowKeybinds.CONFIG.container.rainBowText ? rainbow : ShowKeybinds.CONFIG.container.containerTextColor;
            if (!(screen instanceof CreativeModeInventoryScreen || screen instanceof MerchantScreen)) {
                if (slot.y == 142 || slot.y == 143 || slot.y == 197 || slot.y == 109 || slot.y == 195) {
                    if (ShowKeybinds.CONFIG.container.shadowedText) {
                        switch (slot.x) {
                            case 8, 36 ->
                                    this.font.drawShadow(poseStack, list[0].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY + textY, containerColor);
                            case 26, 54 ->
                                    this.font.drawShadow(poseStack, list[1].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 44, 72 ->
                                    this.font.drawShadow(poseStack, list[2].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 62, 90 ->
                                    this.font.drawShadow(poseStack, list[3].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 80, 108 ->
                                    this.font.drawShadow(poseStack, list[4].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 98, 126 ->
                                    this.font.drawShadow(poseStack, list[5].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 116, 144 ->
                                    this.font.drawShadow(poseStack, list[6].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 134, 162 ->
                                    this.font.drawShadow(poseStack, list[7].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 152, 180 ->
                                    this.font.drawShadow(poseStack, list[8].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                        }
                    } else {
                        switch (slot.x) {
                            case 8, 36 ->
                                    this.font.draw(poseStack, list[0].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY + textY, containerColor);
                            case 26, 54 ->
                                    this.font.draw(poseStack, list[1].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 44, 72 ->
                                    this.font.draw(poseStack, list[2].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 62, 90 ->
                                    this.font.draw(poseStack, list[3].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 80, 108 ->
                                    this.font.draw(poseStack, list[4].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 98, 126 ->
                                    this.font.draw(poseStack, list[5].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 116, 144 ->
                                    this.font.draw(poseStack, list[6].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 134, 162 ->
                                    this.font.draw(poseStack, list[7].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 152, 180 ->
                                    this.font.draw(poseStack, list[8].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                        }
                    }
                }
            } else {
                if (slot.y == 112 || slot.y == 142) {
                    if (ShowKeybinds.CONFIG.container.shadowedText) {
                        switch (slot.x) {
                            case 9, 108 ->
                                    this.font.drawShadow(poseStack, list[0].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 27, 126 ->
                                    this.font.drawShadow(poseStack, list[1].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 45, 144 ->
                                    this.font.drawShadow(poseStack, list[2].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 63, 162 ->
                                    this.font.drawShadow(poseStack, list[3].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 81, 180 ->
                                    this.font.drawShadow(poseStack, list[4].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 99, 198 ->
                                    this.font.drawShadow(poseStack, list[5].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 117, 216 ->
                                    this.font.drawShadow(poseStack, list[6].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 135, 234 ->
                                    this.font.drawShadow(poseStack, list[7].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 153, 252 ->
                                    this.font.drawShadow(poseStack, list[8].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                        }
                    } else {
                        switch (slot.x) {
                            case 9, 108 ->
                                    this.font.draw(poseStack, list[0].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 27, 126 ->
                                    this.font.draw(poseStack, list[1].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 45, 144 ->
                                    this.font.draw(poseStack, list[2].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 63, 162 ->
                                    this.font.draw(poseStack, list[3].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 81, 180 ->
                                    this.font.draw(poseStack, list[4].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 99, 198 ->
                                    this.font.draw(poseStack, list[5].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 117, 216 ->
                                    this.font.draw(poseStack, list[6].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 135, 234 ->
                                    this.font.draw(poseStack, list[7].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                            case 153, 252 ->
                                    this.font.draw(poseStack, list[8].getTranslatedKeyMessage(), (int) (slot.x / scale), (int) ((slot.y) / scale) + textY, containerColor);
                        }
                    }
                }

            }
        }
        poseStack.popPose();
    }
}

