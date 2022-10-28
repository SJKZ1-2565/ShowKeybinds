package sjkz1.com.cheesy_slot.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.cheesy_slot.CheesySlot;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu>
        extends Screen
        implements MenuAccess<T> {

    protected AbstractContainerScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "renderSlot", at = @At(value = "TAIL"))
    public void renderSlot(PoseStack poseStack, Slot slot, CallbackInfo ci) {
        if (CheesySlot.CONFIG.container.enableContainerText) {
            poseStack.pushPose();
            poseStack.translate(0f, 0f, this.getBlitOffset() + 200.0f);
            float scale = CheesySlot.CONFIG.container.containerScale;
            poseStack.scale(scale, scale, scale);
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            if (slot.y == 142) {
                switch (slot.x) {
                    case 8 ->
                            this.font.draw(poseStack, list[0].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 26 ->
                            this.font.draw(poseStack, list[1].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 44 ->
                            this.font.draw(poseStack, list[2].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 62 ->
                            this.font.draw(poseStack, list[3].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 80 ->
                            this.font.draw(poseStack, list[4].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 98 ->
                            this.font.draw(poseStack, list[5].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 116 ->
                            this.font.draw(poseStack, list[6].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 134 ->
                            this.font.draw(poseStack, list[7].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                    case 152 ->
                            this.font.draw(poseStack, list[8].getTranslatedKeyMessage().getString(), (int) (slot.x / scale), (int) ((slot.y) / scale), CheesySlot.CONFIG.container.containerTextColor);
                }
            }
            poseStack.popPose();
        }
    }
}
