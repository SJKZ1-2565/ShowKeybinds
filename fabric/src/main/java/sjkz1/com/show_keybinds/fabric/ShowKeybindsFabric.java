package sjkz1.com.show_keybinds.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import sjkz1.com.show_keybinds.ShowKeybinds;

import java.io.IOException;

public class ShowKeybindsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ShowKeybinds.init();
        ShowKeybinds.registerConfig();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.player != null) {
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
                        Minecraft.getInstance().gui.getChat().addMessage(Component.literal("[CURSEFORGE]").withStyle(
                                style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                        "https://www.curseforge.com/minecraft/mc-mods/showkeybinds"))).withStyle(
                                ChatFormatting.BOLD,
                                ChatFormatting.DARK_PURPLE).append(Component.literal(" to get new version!").withStyle(
                                ChatFormatting.WHITE).withStyle(style -> style.withBold(false))));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}