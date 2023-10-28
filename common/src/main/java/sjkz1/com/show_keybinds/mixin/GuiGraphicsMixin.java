package sjkz1.com.show_keybinds.mixin;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import sjkz1.com.show_keybinds.ShowKeybinds;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin{
    @ModifyArgs(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))
    public void renderGuiItemDecorations$Colored(Args args) {
        args.set(4, ShowKeybinds.CONFIG.general.itemCountColor);
    }
}
