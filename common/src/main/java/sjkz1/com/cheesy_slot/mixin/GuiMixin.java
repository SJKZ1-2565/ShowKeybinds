package sjkz1.com.cheesy_slot.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.cheesy_slot.CheesySlot;

import java.util.Arrays;

@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {
    @Shadow
    private int screenWidth;

    @Shadow
    public abstract Font getFont();

    @Shadow
    private int screenHeight;

    @Inject(method = "renderHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(float f, PoseStack poseStack, CallbackInfo ci) {
        if (CheesySlot.CONFIG.general.enableHotBarText) {
            poseStack.pushPose();
            poseStack.translate(0f, 0f, this.getBlitOffset() + 500f);
            float scale = CheesySlot.CONFIG.general.hotBarScale;
            poseStack.scale(scale, scale, scale);
            int i = this.screenWidth / 2;
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            for (int j = 0; j < Arrays.stream(list).toList().size(); j++) {
                this.getFont().drawShadow(poseStack, Arrays.stream(list).toList().get(j).getTranslatedKeyMessage().getString(), ((i - 91 - 15 + (j + 1) * 20) / scale), (int) ((this.screenHeight - 22 + 3) / scale), CheesySlot.CONFIG.general.hotBarTextColor);
            }
            poseStack.popPose();
        }
    }
}
