package sjkz1.com.show_keybinds.forge.event;


import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sjkz1.com.show_keybinds.ShowKeybinds;

import java.io.IOException;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerLoggedInEvent {

    @SubscribeEvent
    public void loggedInEvent(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        var player = playerLoggedInEvent.getEntity();
        if (player != null) {
            if (ShowKeybinds.CONFIG.general.loggedInToasts) {
                Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                        Component.literal(ShowKeybinds.MOD_NAME),
                        Component.literal("Suggest your idea @GitHub").withStyle(ChatFormatting.GOLD)));
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal("[CLICK HERE]").withStyle(style -> style.withClickEvent(
                        new ClickEvent(ClickEvent.Action.OPEN_URL,
                                "https://github.com/SJKZ1-2565/ShowKeybinds/issues"))).withStyle(ChatFormatting.BOLD,
                        ChatFormatting.YELLOW).append(Component.literal(" to send your suggestion!").withStyle(
                        ChatFormatting.WHITE).withStyle(style -> style.withBold(false))));
            }
            try {
                if (!ShowKeybinds.VERSION.equals(ShowKeybinds.getVersion())) {
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT,
                            Component.literal(ShowKeybinds.MOD_NAME),
                            Component.literal("Outdated mod version!").withStyle(ChatFormatting.RED)));
                    Minecraft.getInstance().gui.getChat().addMessage(Component.literal("[MODRINTH]").withStyle(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.OPEN_URL,
                                    "https://modrinth.com/mod/showkeybinds"))).withStyle(ChatFormatting.BOLD,
                            ChatFormatting.DARK_GREEN).append(Component.literal(" to get new version!").withStyle(
                            ChatFormatting.WHITE).withStyle(style -> style.withBold(false))));
                    Minecraft.getInstance().gui.getChat().addMessage(Component.literal("[CURSEFORGE]").withStyle(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.OPEN_URL,
                                    "https://www.curseforge.com/minecraft/mc-mods/showkeybinds"))).withStyle(
                            ChatFormatting.BOLD,
                            ChatFormatting.DARK_PURPLE).append(Component.literal(" to get new version!").withStyle(
                            ChatFormatting.WHITE).withStyle(style -> style.withBold(false))));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
