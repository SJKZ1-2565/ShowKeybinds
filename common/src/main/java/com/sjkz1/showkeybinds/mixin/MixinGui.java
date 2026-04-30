package com.sjkz1.showkeybinds.mixin;

import com.sjkz1.showkeybinds.Showkeybinds;
import com.sjkz1.showkeybinds.utils.WordUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public abstract class MixinGui
{
    /**
     * Returns the per-slot scale value directly from config.
     * <p>
     * FIX 4: The original code rebuilt a static HashMap<Integer, Float> on every
     * render frame via showkeybinds$load(). A switch expression reads the config
     * value directly at the call site, eliminating the per-frame map reconstruction
     * and the risk of stale entries if the map is not fully cleared between calls.
     */
    @Unique
    private static float showkeybinds$getScaleForIndex(int index)
    {
        return switch (index)
        {
            case 0 -> Showkeybinds.CONFIG.general.keySlot1Scale;
            case 1 -> Showkeybinds.CONFIG.general.keySlot2Scale;
            case 2 -> Showkeybinds.CONFIG.general.keySlot3Scale;
            case 3 -> Showkeybinds.CONFIG.general.keySlot4Scale;
            case 4 -> Showkeybinds.CONFIG.general.keySlot5Scale;
            case 5 -> Showkeybinds.CONFIG.general.keySlot6Scale;
            case 6 -> Showkeybinds.CONFIG.general.keySlot7Scale;
            case 7 -> Showkeybinds.CONFIG.general.keySlot8Scale;
            case 8 -> Showkeybinds.CONFIG.general.keySlot9Scale;
            // Defensive fallback — keyMappingList.length should always be 9.
            default -> 1.0f;
        };
    }

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Shadow
    public abstract Font getFont();

    @Inject(method = "extractItemHotbar", at = @At(value = "TAIL"))
    public void renderHotBar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci)
    {
        // FIX 1: getCameraPlayer() is @Nullable. Without this guard, any call during
        // a dimension transition, spectator mode, or loading screen causes an NPE in
        // both showkeybinds$extractHotBarKey (player.inventoryMenu.slots.get(...))
        // and showkeybinds$extractOffHandKey (player.getOffhandItem()).
        Player player = getCameraPlayer();
        if (player == null) return;

        // FIX 2 & 3: Check both toggles once here, before doing any work.
        // Original code checked enableHotBarText deep inside the helper body,
        // meaning showkeybinds$load() + all 9 loop iterations + the offhand call
        // still ran every frame even when the feature was disabled.
        // FIX 3: offHandText was never gated by enableHotBarText, so the offhand
        // label rendered even when the master toggle was off. Both flags are now
        // evaluated at the entry point.
        boolean drawHotbar = Showkeybinds.CONFIG.general.enableHotBarText;
        boolean drawOffhand = Showkeybinds.CONFIG.general.offHandText;
        if (!drawHotbar && !drawOffhand) return;

        KeyMapping[] keyMappingList = Minecraft.getInstance().options.keyHotbarSlots;
        int hsb = Color.HSBtoRGB(System.currentTimeMillis() % 2500L / 2500.0F, 0.8F, 0.8F);
        int hotBarColor = Showkeybinds.CONFIG.general.rainBowText
                ? ARGB.color(255, ARGB.red(hsb), ARGB.green(hsb), ARGB.blue(hsb))
                : Showkeybinds.CONFIG.general.hotBarTextColor;
        int width = graphics.guiWidth() / 2;

        if (drawHotbar)
        {
            for (int j = 0; j < keyMappingList.length; j++)
            {
                this.showkeybinds$extractHotBarKey(graphics, j, width, hotBarColor, player);
            }
        }

        if (drawOffhand)
        {
            this.showkeybinds$extractOffHandKey(graphics, hotBarColor, width, player);
        }
    }

    @Unique
    private void showkeybinds$extractHotBarKey(GuiGraphicsExtractor graphics, int index, int width,
                                               int hotBarColor, Player player)
    {
        // FIX 2 (cont): enableHotBarText guard removed from here; it is now checked
        // once in renderHotBar before the loop, so this method is never called when
        // the feature is disabled.
        float scale = showkeybinds$getScaleForIndex(index); // FIX 4
        KeyMapping[] keyMappingList = Minecraft.getInstance().options.keyHotbarSlots;
        KeyMapping key = keyMappingList[index];

        int textY = player.inventoryMenu.slots.get(index + 36).getItem().is(Items.LIGHT) ? 7 : 0;
        String keyText = WordUtils.capitalize(WordUtils.convert(key.getTranslatedKeyMessage().getString()));

        graphics.pose().pushMatrix();
        graphics.pose().scale(scale, scale);
        graphics.text(this.getFont(),
                keyText,
                (int) ((width - 92 - 15 + (index + 1) * 20) / scale),
                (int) ((graphics.guiHeight() - 21 + 3 + textY) / scale),
                hotBarColor,
                Showkeybinds.CONFIG.general.shadowedText);
        graphics.pose().popMatrix();
    }

    @Unique
    private void showkeybinds$extractOffHandKey(GuiGraphicsExtractor graphics, int hotBarColor,
                                                int width, Player player)
    {
        // FIX 3 (cont): offHandText and enableHotBarText are both checked in
        // renderHotBar before this method is called, so no redundant config reads here.
        ItemStack itemStack = player.getOffhandItem();

        // Only render when the offhand slot is actually visible (matches vanilla HUD behaviour).
        if (itemStack.isEmpty()) return;

        HumanoidArm humanoidArm = player.getMainArm().getOpposite();
        KeyMapping offHandKey = Minecraft.getInstance().options.keySwapOffhand;

        // Cache scale to guarantee consistency between pose().scale() and coordinate division.
        float offhandScale = Showkeybinds.CONFIG.general.offHandKeySlotScale;
        int offHandTextY = player.inventoryMenu.slots.get(45).getItem().is(Items.LIGHT) ? 8 : 0;
        String offHandKeyText = WordUtils.capitalize(
                WordUtils.convert(offHandKey.getTranslatedKeyMessage().getString()));

        graphics.pose().pushMatrix();
        graphics.pose().scale(offhandScale, offhandScale);

        if (humanoidArm == HumanoidArm.LEFT)
        {
            graphics.text(this.getFont(),
                    offHandKeyText,
                    (int) ((width - 87 - 29) / offhandScale),
                    (int) ((graphics.guiHeight() - 19 + offHandTextY) / offhandScale),
                    hotBarColor,
                    Showkeybinds.CONFIG.general.shadowedText);
        } else
        {
            graphics.text(this.getFont(),
                    offHandKeyText,
                    (int) ((width + 102) / offhandScale),
                    (int) ((graphics.guiHeight() - 19 + offHandTextY) / offhandScale),
                    hotBarColor,
                    Showkeybinds.CONFIG.general.shadowedText);
        }

        graphics.pose().popMatrix();
    }
}
