package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;


@Mixin(GuiGraphicsExtractor.class)
public class MixinGuiGraphics
{

    @ModifyArgs(method = "itemCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)V"))
    public void renderGuiItemDecorations$Colored(Args args)
    {
        int rainbow = Math.abs(Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500F, 0.8F, 0.8F));
        int itemColor = Showkeybinds.CONFIG.general.rainBowItemText ? ARGB.color(255, ARGB.red(rainbow), ARGB.green(rainbow), ARGB.blue(rainbow)) : Showkeybinds.CONFIG.general.enableItemCountColor ? Showkeybinds.CONFIG.general.itemCountColor : args.get(4);
        args.set(4, itemColor);
    }
}
