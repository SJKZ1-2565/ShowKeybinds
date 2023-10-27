package sjkz1.com.show_keybinds.forge.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sjkz1.com.show_keybinds.SKRegistry;

@Mixin(BuiltInRegistries.class)
public class BuiltInMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        SKRegistry.forge();
    }
}
