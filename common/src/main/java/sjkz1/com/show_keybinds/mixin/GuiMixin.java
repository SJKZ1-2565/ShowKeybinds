package sjkz1.com.show_keybinds.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import sjkz1.com.show_keybinds.ShowKeybinds;

import java.awt.*;
import java.util.Arrays;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;
    @Shadow
    public abstract Font getFont();
    @Shadow
    protected abstract Player getCameraPlayer();

    @Inject(method = "renderHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(float f, GuiGraphics guiGraphics, CallbackInfo ci) {
        if (ShowKeybinds.CONFIG.general.enableHotBarText) {
            Player player = this.getCameraPlayer();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0f, 0f, 350f);
            float scale = ShowKeybinds.CONFIG.general.hotBarScale;
            guiGraphics.pose().scale(scale, scale, scale);
            int i = this.screenWidth / 2;
            ItemStack itemStack = player.getOffhandItem();
            HumanoidArm humanoidArm = player.getMainArm().getOpposite();
            var list = Minecraft.getInstance().options.keyHotbarSlots;
            var offHandKey = Minecraft.getInstance().options.keySwapOffhand;
            int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % ShowKeybinds.CONFIG.general.rainbowColorSpeed,
                    0.8F,
                    0.8F));
            var hotBarColor = ShowKeybinds.CONFIG.general.rainBowText ? rainbow : ShowKeybinds.CONFIG.general.hotBarTextColor;
            for (int j = 0; j < Arrays.stream(list).toList().size(); j++) {
                final var v = (i - 91 - 15 + (j + 1) * 20) / scale;
                guiGraphics.drawString(this.getFont(),
                        Arrays.stream(list).toList().get(j).getTranslatedKeyMessage(),
                        (int) v,
                        (int) ((this.screenHeight - (22 + ShowKeybinds.CONFIG.general.hotBarHeight) + 3) / scale),
                        hotBarColor,
                        ShowKeybinds.CONFIG.general.shadowedText);
            }
            if (!itemStack.isEmpty() && ShowKeybinds.CONFIG.general.offHandText) {
                if (humanoidArm == HumanoidArm.LEFT) {
                    guiGraphics.drawString(this.getFont(),
                            offHandKey.getTranslatedKeyMessage(),
                            (int) ((i - 87 - 29) / scale),
                            (int) ((this.screenHeight - (19 + ShowKeybinds.CONFIG.general.hotBarHeight)) / scale),
                            hotBarColor,
                            ShowKeybinds.CONFIG.general.shadowedText);
                } else {
                    guiGraphics.drawString(this.getFont(),
                            offHandKey.getTranslatedKeyMessage(),
                            (int) ((i + 102) / scale),
                            (int) ((this.screenHeight - (19 + ShowKeybinds.CONFIG.general.hotBarHeight)) / scale),
                            hotBarColor,
                            ShowKeybinds.CONFIG.general.shadowedText);
                }
            }
            guiGraphics.pose().popPose();
        }

    }

    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    public void renderHotbar(Args args) {
        float a1 = args.get(1);
        args.set(1, a1 - ShowKeybinds.CONFIG.general.hotBarHeight);
    }

    @ModifyArgs(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I"))
    public void renderSelectedItemName(Args args) {
        int a1 = args.get(3);
        args.set(3, a1 - ShowKeybinds.CONFIG.general.hotBarHeight);
    }

    @ModifyArgs(method = "renderSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V"))
    public void renderSlot$renderItemDecorations(Args args) {
        int a1 = args.get(3);
        args.set(3, a1 - ShowKeybinds.CONFIG.general.hotBarHeight);
    }

    @ModifyArgs(method = "renderSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;III)V"))
    public void renderSlot$renderItem(Args args) {
        int a1 = args.get(3);
        args.set(3, a1 - ShowKeybinds.CONFIG.general.hotBarHeight);
    }

}
