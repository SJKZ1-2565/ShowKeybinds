package sjkz1.com.cheesy_slot.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Shadow
    @Final
    private ItemRenderer itemRenderer;

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
                    this.getFont().drawShadow(poseStack, Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(), v, (int) ((this.screenHeight - (22 + CheesySlot.CONFIG.general.hotBarHeight) + 3) / scale), hotBarColor);
                } else {
                    this.getFont().draw(poseStack, Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(), v, (int) ((this.screenHeight - (22 + CheesySlot.CONFIG.general.hotBarHeight) + 3) / scale), hotBarColor);
                }
            }
            if (!itemStack.isEmpty()) {
                if (humanoidArm == HumanoidArm.LEFT) {
                    if (CheesySlot.CONFIG.general.shadowedText) {
                        this.getFont().drawShadow(poseStack, offHandKey.getTranslatedKeyMessage(), ((i - 87 - 29) / scale), (int) ((this.screenHeight - (19 + CheesySlot.CONFIG.general.hotBarHeight)) / scale), hotBarColor);
                    } else {
                        this.getFont().draw(poseStack, offHandKey.getTranslatedKeyMessage(), ((i - 87 - 29) / scale), (int) ((this.screenHeight - (19 + CheesySlot.CONFIG.general.hotBarHeight)) / scale), hotBarColor);
                    }
                } else {
                    if (CheesySlot.CONFIG.general.shadowedText) {
                        this.getFont().drawShadow(poseStack, offHandKey.getTranslatedKeyMessage(), ((i + 102) / scale), (int) ((this.screenHeight - (19 + CheesySlot.CONFIG.general.hotBarHeight)) / scale), hotBarColor);
                    } else {
                        this.getFont().draw(poseStack, offHandKey.getTranslatedKeyMessage(), ((i + 102) / scale), (int) ((this.screenHeight - (19 + CheesySlot.CONFIG.general.hotBarHeight)) / scale), hotBarColor);
                    }
                }
            }
            poseStack.popPose();
        }
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 0))
    public void renderHotbar(Gui instance, PoseStack poseStack, int screenWidth, int screenHeight, int w, int x, int y, int z) {
        int i = this.screenWidth / 2;
        this.blit(poseStack, i - 91, this.screenHeight - (22 + CheesySlot.CONFIG.general.hotBarHeight), 0, 0, 182, 22);
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 1))
    public void renderHotbar1(Gui instance, PoseStack poseStack, int screenWidth, int screenHeight, int w, int x, int y, int z) {
        int i = this.screenWidth / 2;
        this.blit(poseStack, i - 91 - 1 + getCameraPlayer().getInventory().selected * 20, this.screenHeight - (22 + CheesySlot.CONFIG.general.hotBarHeight) - 1, 0, 22, 24, 22);
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 2))
    public void renderHotbar2(Gui instance, PoseStack poseStack, int screenWidth, int screenHeight, int w, int x, int y, int z) {
        int i = this.screenWidth / 2;
        this.blit(poseStack, i - 91 - 29, this.screenHeight - (23 + CheesySlot.CONFIG.general.hotBarHeight), 24, 22, 29, 24);
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 3))
    public void renderHotbar3(Gui instance, PoseStack poseStack, int screenWidth, int screenHeight, int w, int x, int y, int z) {
        int i = this.screenWidth / 2;
        this.blit(poseStack, i + 91, this.screenHeight - (23 + CheesySlot.CONFIG.general.hotBarHeight), 53, 22, 29, 24);
    }

    @Redirect(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/network/chat/Component;FFI)I"))
    public int renderHotbar3(Font instance, PoseStack poseStack, Component component, float f, float g, int i) {
        return this.getFont().drawShadow(poseStack, component, (float) f, (float) (g - CheesySlot.CONFIG.general.hotBarHeight), 0xFFFFFF + (i << 24));
    }

    @Redirect(method = "renderSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderAndDecorateItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;III)V"))
    public void renderSlot(ItemRenderer instance, LivingEntity livingEntity, ItemStack itemStack, int i, int j, int k) {
        this.itemRenderer.renderAndDecorateItem(livingEntity, itemStack, i, (j - CheesySlot.CONFIG.general.hotBarHeight), k);
    }

    @Redirect(method = "renderSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"))
    public void renderSlot(ItemRenderer instance, Font font, ItemStack itemStack, int i, int j) {
        this.itemRenderer.renderGuiItemDecorations(font, itemStack, i, (j - CheesySlot.CONFIG.general.hotBarHeight));
    }

}
