package sjkz1.com.cheesy_slot.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sjkz1.com.cheesy_slot.CheesySlot;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements ResourceManagerReloadListener {
    @Redirect(method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I"))
    public int renderGuiItemDecorations$Colored(Font instance, String string, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource arg, boolean bl2, int j, int k)
    {
        return instance.drawInBatch(string,f,g,CheesySlot.CONFIG.general.itemCountColor,bl,matrix4f,arg,bl2,j, k);
    }
}
