package sjkz1.com.chessy_slot.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {
    @Shadow protected int screenWidth;

    @Shadow public abstract Font getFont();

    @Shadow protected int screenHeight;

    @Inject(method = "renderHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(float f, PoseStack poseStack, CallbackInfo ci) {
        poseStack.pushPose();
        poseStack.translate(0.0f, 0.0f, 1000f);
        int i = this.screenWidth / 2;
        var list = Minecraft.getInstance().options.keyHotbarSlots;
        for (int j = 0; j < Arrays.stream(list).toList().size(); j++) {
            this.getFont().draw(poseStack, Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(), i - 91 - 15 + (j + 1) * 20, this.screenHeight - 22 + 3, 16777215);
        }
        poseStack.popPose();
    }
}
