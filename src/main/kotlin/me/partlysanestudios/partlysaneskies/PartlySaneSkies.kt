/*
 * Written by Su386.
 * See LICENSE for copyright and license notices.
 *
 *
 * Partly Sane Skies would not be possible with out the help of these projects:
 * (see CREDITS.md for more information)
 * Minecraft Forge
 * Skytils
 * Not Enough Updates
 * SkyHanni
 * GSON
 * Elementa
 * Vigilance
 * OneConfig
 * SkyCrypt
 */
package me.partlysanestudios.partlysaneskies

import gg.essential.elementa.ElementaVersion
import me.partlysanestudios.partlysaneskies.config.oneconfig.OneConfigScreen
import me.partlysanestudios.partlysaneskies.data.api.Request
import me.partlysanestudios.partlysaneskies.data.api.RequestsManager.newRequest
import me.partlysanestudios.partlysaneskies.data.cache.PetData
import me.partlysanestudios.partlysaneskies.data.pssdata.PublicDataManager
import me.partlysanestudios.partlysaneskies.data.pssdata.PublicDataManager.getRepoName
import me.partlysanestudios.partlysaneskies.data.pssdata.PublicDataManager.getRepoOwner
import me.partlysanestudios.partlysaneskies.data.skyblockdata.SkyblockDataManager
import me.partlysanestudios.partlysaneskies.features.chat.ChatAlertsManager
import me.partlysanestudios.partlysaneskies.features.chat.ChatManager
import me.partlysanestudios.partlysaneskies.features.chat.WordEditor
import me.partlysanestudios.partlysaneskies.features.commands.Crepes
import me.partlysanestudios.partlysaneskies.features.commands.Discord
import me.partlysanestudios.partlysaneskies.features.commands.HelpCommand
import me.partlysanestudios.partlysaneskies.features.commands.Version
import me.partlysanestudios.partlysaneskies.features.debug.DebugKey
import me.partlysanestudios.partlysaneskies.features.discord.DiscordRPC
import me.partlysanestudios.partlysaneskies.features.dungeons.AutoGG
import me.partlysanestudios.partlysaneskies.features.dungeons.PearlRefill
import me.partlysanestudios.partlysaneskies.features.dungeons.RequiredSecretsFound
import me.partlysanestudios.partlysaneskies.features.dungeons.WatcherReady
import me.partlysanestudios.partlysaneskies.features.dungeons.party.PartyFriendManager
import me.partlysanestudios.partlysaneskies.features.dungeons.party.partymanager.PartyManager
import me.partlysanestudios.partlysaneskies.features.dungeons.party.permpartyselector.PermPartyManager
import me.partlysanestudios.partlysaneskies.features.dungeons.playerrating.PlayerRating
import me.partlysanestudios.partlysaneskies.features.economy.BitsShopValue
import me.partlysanestudios.partlysaneskies.features.economy.CoinsToBoosterCookieConversion
import me.partlysanestudios.partlysaneskies.features.economy.NoCookieWarning
import me.partlysanestudios.partlysaneskies.features.economy.auctionhousemenu.AuctionHouseGui
import me.partlysanestudios.partlysaneskies.features.economy.minioncalculator.ProfitMinionCalculator
import me.partlysanestudios.partlysaneskies.features.farming.MathematicalHoeRightClicks
import me.partlysanestudios.partlysaneskies.features.farming.VisitorLogbookStats
import me.partlysanestudios.partlysaneskies.features.farming.endoffarmnotifer.EndOfFarmNotifier
import me.partlysanestudios.partlysaneskies.features.farming.endoffarmnotifer.RangeHighlight
import me.partlysanestudios.partlysaneskies.features.farming.garden.CompostValue
import me.partlysanestudios.partlysaneskies.features.farming.garden.GardenTradeValue
import me.partlysanestudios.partlysaneskies.features.farming.garden.SkymartValue
import me.partlysanestudios.partlysaneskies.features.gui.RefreshKeybinds
import me.partlysanestudios.partlysaneskies.features.gui.custommainmenu.CustomMainMenu
import me.partlysanestudios.partlysaneskies.features.gui.hud.LocationBannerDisplay
import me.partlysanestudios.partlysaneskies.features.gui.hud.rngdropbanner.DropBannerDisplay
import me.partlysanestudios.partlysaneskies.features.information.WikiArticleOpener
import me.partlysanestudios.partlysaneskies.features.mining.MiningEvents
import me.partlysanestudios.partlysaneskies.features.mining.Pickaxes
import me.partlysanestudios.partlysaneskies.features.mining.WormWarning
import me.partlysanestudios.partlysaneskies.features.misc.SanityCheck
import me.partlysanestudios.partlysaneskies.features.security.modschecker.ModChecker
import me.partlysanestudios.partlysaneskies.features.skills.PetAlert
import me.partlysanestudios.partlysaneskies.features.skills.SkillUpgradeRecommendation
import me.partlysanestudios.partlysaneskies.features.sound.Prank
import me.partlysanestudios.partlysaneskies.features.sound.enhancedsound.EnhancedSound
import me.partlysanestudios.partlysaneskies.features.themes.ThemeManager
import me.partlysanestudios.partlysaneskies.gui.hud.BannerRenderer
import me.partlysanestudios.partlysaneskies.gui.hud.cooldown.CooldownManager
import me.partlysanestudios.partlysaneskies.utils.ChatUtils.sendClientMessage
import me.partlysanestudios.partlysaneskies.utils.SystemUtils.log
import net.minecraft.client.Minecraft
import net.minecraft.event.ClickEvent
import net.minecraft.event.HoverEvent
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import net.minecraftforge.common.MinecraftForge.EVENT_BUS
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException
import java.net.MalformedURLException

@Mod(modid = PartlySaneSkies.MODID, version = PartlySaneSkies.VERSION, name = PartlySaneSkies.NAME)
class PartlySaneSkies {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
        }

        var LOGGER = LogManager.getLogger("Partly Sane Skies")
        const val MODID = "@MOD_ID@"
        const val NAME = "@MOD_NAME@"
        const val VERSION = "@MOD_VERSION@"

        //    -----------------------CHANGE TO FALSE BEFORE RELEASING
        val DOGFOOD = "@DOGFOOD@".toBoolean()
        const val CHAT_PREFIX = "§r§b§lPartly Sane Skies§r§7>> §r"
        var discordCode = "v4PU3WeH7z"
        val config: OneConfigScreen
            get() {
                return oneConfig?: OneConfigScreen()
            }
        private var oneConfig: OneConfigScreen? = null

        val minecraft: Minecraft
            get() {
                return pssMinecraft?: Minecraft.getMinecraft()
            }
        private var pssMinecraft: Minecraft? = null

        // Names of all the ranks to remove from people's names
        val RANK_NAMES = arrayOf(
            "[VIP]", "[VIP+]", "[MVP]", "[MVP+]", "[MVP++]", "[YOUTUBE]", "[MOJANG]",
            "[EVENTS]", "[MCP]", "[PIG]", "[PIG+]", "[PIG++]", "[PIG+++]", "[GM]", "[ADMIN]", "[OWNER]", "[NPC]"
        )
        val time: Long
            // Returns the time in milliseconds
            get() = System.currentTimeMillis()
        val isLatestVersion: Boolean
            get() = if (DOGFOOD) {
                true
            } else VERSION == CustomMainMenu.latestVersion
    }

    // Method runs at mod initialization
    @Mod.EventHandler
    fun init(evnt: FMLInitializationEvent?) {
        log(Level.INFO, "Hallo World!")
        pssMinecraft = Minecraft.getMinecraft()

        // Creates the partly-sane-skies directory if not already made
        File("./config/partly-sane-skies/").mkdirs()

        // Loads the config files and options
        oneConfig = OneConfigScreen()
        var mainMenuRequest: Request? = null
        mainMenuRequest =
            Request("https://raw.githubusercontent.com/" + getRepoOwner() + "/" + getRepoName() + "/main/data/main_menu.json",
                { request: Request? ->
                    CustomMainMenu.setMainMenuInfo(
                        request
                    )
                }, false, false
            )
        newRequest(mainMenuRequest)
        var funFactRequest: Request? = null
        funFactRequest = Request(CustomMainMenu.funFactApi,
            { request: Request? ->
                CustomMainMenu.setFunFact(
                    request
                )
            }, false, false
        )
        newRequest(funFactRequest)
        trackLoad()

        // Loads extra json data
        Thread {
            try {
                PermPartyManager.load()
                PermPartyManager.loadFavoriteParty()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                ChatAlertsManager.load()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                PetData.load()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                EndOfFarmNotifier.load()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                WordEditor.load()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()


        // Registers all the events
        EVENT_BUS.register(this)
        EVENT_BUS.register(DropBannerDisplay())
        EVENT_BUS.register(PartyManager())
        EVENT_BUS.register(WatcherReady())
        EVENT_BUS.register(WormWarning())
        EVENT_BUS.register(CustomMainMenu(ElementaVersion.V2))
        EVENT_BUS.register(PartyFriendManager())
        EVENT_BUS.register(WikiArticleOpener())
        EVENT_BUS.register(NoCookieWarning())
        EVENT_BUS.register(GardenTradeValue())
        EVENT_BUS.register(CompostValue())
        EVENT_BUS.register(EnhancedSound())
        EVENT_BUS.register(BitsShopValue())
        EVENT_BUS.register(PlayerRating())
        EVENT_BUS.register(SkymartValue())
        EVENT_BUS.register(PetAlert())
        EVENT_BUS.register(RequiredSecretsFound())
        EVENT_BUS.register(Pickaxes())
        EVENT_BUS.register(MathematicalHoeRightClicks())
        EVENT_BUS.register(MiningEvents())
        EVENT_BUS.register(AuctionHouseGui)
        EVENT_BUS.register(ChatManager)
        EVENT_BUS.register(RangeHighlight)
        EVENT_BUS.register(BannerRenderer)
        EVENT_BUS.register(VisitorLogbookStats)
        EVENT_BUS.register(CoinsToBoosterCookieConversion)
        EVENT_BUS.register(EndOfFarmNotifier)
        EVENT_BUS.register(Prank)
        EVENT_BUS.register(RefreshKeybinds)
        EVENT_BUS.register(AutoGG)
        EVENT_BUS.register(CooldownManager)
        EVENT_BUS.register(PetData)
        EVENT_BUS.register(PearlRefill)
        EVENT_BUS.register(SanityCheck)


        // Registers all client side commands
        HelpCommand.registerPSSCommand()
        HelpCommand.registerHelpCommand()
        HelpCommand.registerConfigCommand()
        Crepes.registerCrepesCommand()
        Version.registerVersionCommand()
        Discord.registerDiscordCommand()
        PublicDataManager.registerDataCommand()
        PartyManager.registerCommand()
        SkillUpgradeRecommendation.registerCommand()
        PermPartyManager.registerCommand()
        PartyFriendManager.registerCommand()
        ChatAlertsManager.registerCommand()
        PetAlert.registerCommand()
        EndOfFarmNotifier.registerPos1Command()
        EndOfFarmNotifier.registerPos2Command()
        EndOfFarmNotifier.registerCreateRangeCommand()
        EndOfFarmNotifier.registerFarmNotifierCommand()
        EndOfFarmNotifier.registerWandCommand()
        CoinsToBoosterCookieConversion.registerCommand()
        ProfitMinionCalculator.registerCommand()
        MathematicalHoeRightClicks.registerCommand()
        WordEditor.registerWordEditorCommand()
        PlayerRating.registerReprintCommand()
        ModChecker.registerModCheckCommand()
        PearlRefill.registerCommand()
        CooldownManager.init()
        DebugKey.init()

        // Initializes skill upgrade recommendation
        SkillUpgradeRecommendation.populateSkillMap()
        try {
            SkyblockDataManager.initItems()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        SkyblockDataManager.updateAll()
        try {
            SkyblockDataManager.initSkills()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        PublicDataManager.initAllPublicData()

        // Loads user player data for PartyManager
        Thread({
            try {
                SkyblockDataManager.getPlayer(
                    me.partlysanestudios.partlysaneskies.PartlySaneSkies.Companion.minecraft?.session?.username ?: ""
                )
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }, "Init Data").start()
        Thread { DiscordRPC.init() }.start()
        // Finished loading
        log(Level.INFO, "Partly Sane Skies has loaded.")
    }

    // Method runs every tick
    @SubscribeEvent
    fun clientTick(evnt: ClientTickEvent?) {
        // Checks if the current location is the same as the previous location for the location banner display
        LocationBannerDisplay.checkLocation()
        EndOfFarmNotifier.run()
        SkyblockDataManager.runUpdater()

        // Checks if the player is collecting minions
        PetAlert.runPetAlert()
        ThemeManager.run()
        config!!.resetBrokenStrings()
        ThemeManager.run()
        PetData.tick()
    }

    @SubscribeEvent
    fun onClientConnectedToServer(event: ClientConnectedToServerEvent?) {
        if (DOGFOOD) {
            Thread {
                try {
                    Thread.sleep(4000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                val discordMessage: IChatComponent =
                    ChatComponentText("§9The Partly Sane Skies Discord server: https://discord.gg/" + discordCode)
                discordMessage.chatStyle.setChatClickEvent(
                    ClickEvent(
                        ClickEvent.Action.OPEN_URL,
                        "https://discord.gg/" + discordCode
                    )
                )
                sendClientMessage("§b§m--------------------------------------------------", true)
                sendClientMessage("§cWe noticed you're using a dogfood version of Partly Sane Skies.", false)
                sendClientMessage("§c§lThis version may be unstable.", true)
                sendClientMessage("§cOnly use it when told to do so by a Partly Sane Skies admin.", true)
                sendClientMessage("§cReport any bugs to Partly Sane Skies admins in a private ticket.", true)
                sendClientMessage(
                    "§7Version ID: §d" + VERSION,
                    true
                )
                sendClientMessage("§7Latest non-dogfood version: §d" + CustomMainMenu.latestVersion, true)
                sendClientMessage(discordMessage)
                sendClientMessage("§b§m--------------------------------------------------", true)
            }.start()
        }
        ModChecker.runOnStartup()
        if (!isLatestVersion) {
            Thread {
                try {
                    Thread.sleep(4000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                sendClientMessage("§b§m--------------------------------------------------", true)
                sendClientMessage("§cWe have detected a new version of Partly Sane Skies.")
                sendClientMessage("§cYou are currently using version §d" + VERSION + "§c, the latest version is §d" + CustomMainMenu.latestVersion + "§c.")
                val skyclientMessage =
                    ChatComponentText("§aIf you are using SkyClient, make sure you update when prompted.")
                minecraft!!.ingameGUI
                    .chatGUI
                    .printChatMessage(skyclientMessage)
                val githubMessage =
                    ChatComponentText("§9If you are not using SkyClient, click here go to the github and download the latest version.")
                githubMessage.chatStyle.setChatClickEvent(
                    ClickEvent(
                        ClickEvent.Action.OPEN_URL,
                        "https://github.com/PartlySaneStudios/partly-sane-skies/releases"
                    )
                )
                githubMessage.chatStyle.setChatHoverEvent(
                    HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        ChatComponentText("Click here to open the downloads page")
                    )
                )
                minecraft!!.ingameGUI
                    .chatGUI
                    .printChatMessage(githubMessage)
                sendClientMessage("§b§m--------------------------------------------------", true)
            }.start()
        }
    }

    // Sends a ping to the count API to track the number of users per day
    fun trackLoad() {}

}
