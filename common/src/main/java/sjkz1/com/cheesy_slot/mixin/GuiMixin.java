package sjkz1.com.cheesy_slot.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.cheesy_slot.CheesySlot;

import java.awt.*;
import java.util.Arrays;

@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {
    @Shadow
    private int screenWidth;

    @Shadow
    public abstract Font getFont();

    @Shadow
    private int screenHeight;

    @Shadow
    protected abstract Player getCameraPlayer();

    @Inject(method = "renderHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(float f, PoseStack poseStack, CallbackInfo ci) {
        if (CheesySlot.CONFIG.general.enableHotBarText) {
            Player player = this.getCameraPlayer();
            poseStack.pushPose();
            poseStack.translate(0f, 0f, this.getBlitOffset() + 350f);
            float scale = CheesySlot.CONFIG.general.hotBarScale;
            poseStack.scale(scale, scale, scale);
            int i = this.screenWidth / 2;
            ItemStack itemStack = player.getOffhandItem();
            HumanoidArm humanoidArm = player.getMainArm().getOpposite();
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            var offHandKey = Minecraft.getInstance().options.keySwapOffhand;
            int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F));
            var hotBarColor = CheesySlot.CONFIG.general.rainBowText ? rainbow : CheesySlot.CONFIG.general.hotBarTextColor;
            for (int j = 0; j < Arrays.stream(list).toList().size(); j++) {
                final var v = (i - 91 - 15 + (j + 1) * 20) / scale;
                if (CheesySlot.CONFIG.general.shadowedText) {
                    this.getFont().drawShadow(poseStack, Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(), v, (int) ((this.screenHeight - 22 + 3) / scale), hotBarColor);
                } else {
                    this.getFont().draw(poseStack, Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(), v, (int) ((this.screenHeight - 22 + 3) / scale), hotBarColor);
                }
            }
            if (!itemStack.isEmpty()) {
                if (humanoidArm == HumanoidArm.LEFT) {
                    if (CheesySlot.CONFIG.general.shadowedText) {
                        this.getFont().drawShadow(poseStack, offHandKey.getTranslatedKeyMessage(), ((i - 87 - 29) / scale), (int) ((this.screenHeight - 19) / scale), hotBarColor);
                    } else {
                        this.getFont().draw(poseStack, offHandKey.getTranslatedKeyMessage(), ((i - 87 - 29) / scale), (int) ((this.screenHeight - 19) / scale), hotBarColor);
                    }
                } else {
                    if (CheesySlot.CONFIG.general.shadowedText) {
                        this.getFont().drawShadow(poseStack, offHandKey.getTranslatedKeyMessage(), ((i + 102) / scale), (int) ((this.screenHeight - 19) / scale), hotBarColor);
                    } else {
                        this.getFont().draw(poseStack, offHandKey.getTranslatedKeyMessage(), ((i + 102) / scale), (int) ((this.screenHeight - 19) / scale), hotBarColor);
                    }
                }
            }
            poseStack.popPose();
        }
    }
}
