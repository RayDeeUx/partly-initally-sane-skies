package me.partlysanestudios.partlysaneskies;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import gg.essential.elementa.ElementaVersion;
import me.partlysanestudios.partlysaneskies.dungeons.WatcherReady;
import me.partlysanestudios.partlysaneskies.dungeons.partymanager.PartyManager;
import me.partlysanestudios.partlysaneskies.dungeons.partymanager.PartyManagerCommand;
import me.partlysanestudios.partlysaneskies.dungeons.permpartyselector.PermPartyManager;
import me.partlysanestudios.partlysaneskies.general.WormWarning;
import me.partlysanestudios.partlysaneskies.general.rngdropbanner.Drop;
import me.partlysanestudios.partlysaneskies.general.rngdropbanner.DropBannerDisplay;
import me.partlysanestudios.partlysaneskies.general.skillupgrade.SkillUpgradeCommand;
import me.partlysanestudios.partlysaneskies.help.Help;
import me.partlysanestudios.partlysaneskies.help.HelpCommand;
import me.partlysanestudios.partlysaneskies.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;


@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main
{
    public static final String MODID = "partlysaneskies";
    public static final String NAME = "Partly Sane Skies";
    public static final String VERSION = "1.0";
    public static String CHAT_PREFIX = Utils.colorCodes("&r&b&lPartly Sane Skies&r&7>> &r");

    public static ConfigScreen config;
    public static Minecraft minecraft;

    public static boolean isHypixel;
    public static boolean isSkyblock;
    public static boolean isDebugMode;


    @EventHandler
    public void init(FMLInitializationEvent evnt) {
        
        
        

        System.out.println("Hallo World!");
        Main.isHypixel = false;
        Main.isSkyblock = false;
        Main.isDebugMode = false;
        Main.minecraft = Minecraft.getMinecraft();

       

        Main.config = new ConfigScreen();
        new File("./config/partly-sane-skies/").mkdirs();
        try {
            PermPartyManager.permPartyMap = PermPartyManager.load();
            Utils.visPrint(PermPartyManager.permPartyMap.get("test").name);
        } catch (IOException e) {

            e.printStackTrace();
        }

        try {
            PartyManager.loadPersonalPlayerData();
        } catch (IOException e) {
            System.out.println("Partly Sane Skies: Unable to load player data.");
            e.printStackTrace();
        }

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new DropBannerDisplay());
        MinecraftForge.EVENT_BUS.register(new PartyManager());
        MinecraftForge.EVENT_BUS.register(new WatcherReady());
        MinecraftForge.EVENT_BUS.register(new WormWarning());
        MinecraftForge.EVENT_BUS.register(new PartlySaneSkiesMainMenu(ElementaVersion.V2));

        ClientCommandHandler.instance.registerCommand(new PartyManagerCommand());
        ClientCommandHandler.instance.registerCommand(new HelpCommand());
        ClientCommandHandler.instance.registerCommand(new SkillUpgradeCommand());
        KeyInit.init();
        Utils.init();

        System.out.println("Partly Sane Skies has loaded.");
    }





    @SubscribeEvent
    public void joinServerEvent(ClientConnectedToServerEvent evnt) {
        if(minecraft.getCurrentServerData() == null || minecraft.getCurrentServerData().serverIP == null) return;
        if(minecraft.getCurrentServerData().serverIP.contains(".hypixel.net")) {
            Main.isHypixel = true;
        }
    }

    @SubscribeEvent
    public void clientTick(ClientTickEvent evnt) {
        if(KeyInit.debugKey.isPressed()) {
            Main.isDebugMode = !Main.isDebugMode;
            Utils.visPrint("Debug mode: " + Main.isDebugMode);
            DropBannerDisplay.drop = new Drop("test", "RARE DROP!", 1, 1, Minecraft.getSystemTime(), new Color(255, 170, 0), new Color(255, 85, 85));
            Main.minecraft.thePlayer.playSound("partlysaneskies:rngdropjingle", 100, 1);
            Main.minecraft.thePlayer.playSound("partlysaneskies:airraidsiren", 100, 1);
        }
        if(KeyInit.configKey.isPressed()) {
            minecraft.displayGuiScreen(Main.config.gui());
        }
        if(KeyInit.partyManagerKey.isPressed()) {
            PartyManager.startPartyManager();
        }
        if(KeyInit.helpKey.isPressed()) {
            Help.printHelpMessage();
        }
        if(KeyInit.craftKeybind.isPressed()) {
            minecraft.thePlayer.sendChatMessage("/craft");
        }
        if(KeyInit.petKeybind.isPressed()) {
            minecraft.thePlayer.sendChatMessage("/pets");
        }
        if(KeyInit.wardrobeKeybind.isPressed()) {
            minecraft.thePlayer.sendChatMessage("/wardrobe");
        }
        if(KeyInit.storageKeybind.isPressed()) {
            minecraft.thePlayer.sendChatMessage("/storage");
        }

        try {
            Main.isSkyblock = Utils.detectScoreboardName("§lSKYBLOCK");
            Main.isHypixel = minecraft.getCurrentServerData().serverIP.contains(".hypixel.net");
        }
        catch(NullPointerException expt) {}
        finally {}
    }

    @SubscribeEvent
    public void newApiKey(ClientChatReceivedEvent event) {
        if(event.message.getUnformattedText().startsWith("Your new API key is ")) { 
            config.apiKey = event.message.getUnformattedText().replace("Your new API key is ", "");
            Utils.sendClientMessage(Utils.colorCodes("Saved new API key!"));
            config.writeData();
        }
    }

    @SubscribeEvent
    public void chatAnalyzer(ClientChatReceivedEvent evnt) {
        
        if(Main.isDebugMode) System.out.println(evnt.message.getFormattedText());
    }
}
