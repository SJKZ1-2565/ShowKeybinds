package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;


@Mixin(GuiGraphics.class)
public class MixinGuiGraphics {

    @ModifyArgs(method = "renderItemCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V"))
    public void renderGuiItemDecorations$Colored(Args args) {
        int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500F, 0.8F, 0.8F));
        int itemColor = Showkeybinds.CONFIG.general.rainBowItemText ? ARGB.color(ARGB.red(rainbow), ARGB.green(rainbow), ARGB.blue(rainbow)) : Showkeybinds.CONFIG.general.itemCountColor;
        args.set(4, itemColor);
    }
}
