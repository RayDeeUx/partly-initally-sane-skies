package me.partlysanestudios.partlysaneskies.dungeons;

import java.awt.Color;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.universal.UMatrixStack;
import me.partlysanestudios.partlysaneskies.Main;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WatcherReady {

    public WatcherReady() {

    }

    Window window= new Window(ElementaVersion.V2);

    UIComponent watcherReadyUIText = new UIText("")
        .setTextScale(new PixelConstraint(2))
        .setX(new CenterConstraint())
        .setY(new PixelConstraint(window.getHeight()*.333f))
        .setColor(Color.white)
        .setChildOf(window);

    String watcherReadyString = "";
    long watcherReadyBannerTime;
    @SubscribeEvent
    public void watcherReadyChatEvent(ClientChatReceivedEvent event) {

        if(event.message.getUnformattedText().startsWith("[BOSS] The Watcher: That will be enough for now.")) { 
            if (Main.config.watcherReadyBanner) {
                watcherReadyBannerTime = Minecraft.getSystemTime();
                watcherReadyString = "Watcher Ready!";
            }
            if(Main.config.watcherReadyChatMessage) {
                Main.minecraft.thePlayer.sendChatMessage("/pc " + Main.config.watcherChatMessage);
            }
            if(Main.config.watcherReadySound) {
                Main.minecraft.thePlayer.playSound("partlysaneskies:bell", 100, 1);
            }
            if(Main.config.watcherReadyAirRaidSiren) {
                Main.minecraft.thePlayer.playSound("partlysaneskies:airraidsiren", 100, 1);
            }
        }
    }


    @SubscribeEvent
	public void renderText(RenderGameOverlayEvent.Text event) { 
        ((UIText) watcherReadyUIText).setText(watcherReadyString).setColor(Main.config.watcherReadyBannerColor);
        window.draw(new UMatrixStack());
        if(watcherReadyBannerTime+Main.config.watcherReadyBannerTime*1000 < Minecraft.getSystemTime()) watcherReadyString = "";
    }
}
