package sjkz1.com.show_keybinds.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
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

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements ResourceManagerReloadListener {
    @Shadow @Final private PoseStack pose;

    @ModifyArgs(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))
    public void renderGuiItemDecorations$Colored(Args args) {
        args.set(4, ShowKeybinds.CONFIG.general.itemCountColor);
    }
    @ModifyArgs(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))
    public void renderGuiItemDecorations$Colored1(Args args) {
        int a3 = args.get(2);
        args.set(2, a3 / (int) ShowKeybinds.CONFIG.general.hotBarScale);
    }
    @ModifyArgs(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))
    public void renderGuiItemDecorations$Colored2(Args args) {
        int a3 = args.get(3);
        args.set(3, a3 / (int) ShowKeybinds.CONFIG.general.hotBarScale);
    }
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V",shift = At.Shift.AFTER))
    public void scale(Font font, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
        float scale = ShowKeybinds.CONFIG.general.hotBarScale;
        this.pose.scale(scale,scale,scale);
    }
}
