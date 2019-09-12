package com.gmail.llmdlio.townytnemobsbridge;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.llmdlio.townytnemobsbridge.config.TownyTNEMobsBridgeConfig;

import net.tnemc.core.TNE;

public class TownyTNEMobsBridge extends JavaPlugin {
    private TownyTNEMobsBridgeConfig config = new TownyTNEMobsBridgeConfig(this);
    public final RewardsListener rewardsListener = new RewardsListener(this);
    public static List<String> disabledWorlds;
    public static List<String> exemptedTowns;
    public static double insideTownMultiplier;

    @Override
    public void onEnable() {
        if (!checkTNEVersion())
            onDisable();

        getServer().getPluginManager().registerEvents(rewardsListener, this);
        reloadConfig();

        if (!loadSettings())
            onDisable();
    }

    @Override
    public void onDisable() {
    }

    @SuppressWarnings("static-access")
    private boolean checkTNEVersion() {
        
        TNE tne = (TNE)Bukkit.getPluginManager().getPlugin("TheNewEconomy");
        if (tne.loader().hasModuleEvent("AsyncMobRewardEvent"))
            return true;
        else {
            getLogger().severe("[TownyTNEMobsBridge] Couldn't find TNE's Mobs module version 0.1.2.0 or greater");
            return false;
        }
    }

    private boolean loadSettings() {
        try {
            disabledWorlds = config.getConfig().getStringList("Disabled_Worlds");
            exemptedTowns = config.getConfig().getStringList("Exempted_Towns");
            insideTownMultiplier = config.getConfig().getDouble("Inside_Town_Multiplier");
            return true;
        } catch (Exception e) {
            getLogger().severe("[TownyTNEMobsBridge] Failed to load settings from config.yml! Disabling TownyTNEMobsBridge.");
            return false;
        }
    }

    public void reloadConfig() { 
        if (!getDataFolder().exists())  
            getDataFolder().mkdirs(); 
        config.reload();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("townymobs")) {
            if (args.length == 0) {
                sender.sendMessage("[TownyTNEMobsBridge] Version " + this.getDescription().getVersion() + " by LlmDl");
                if (sender.hasPermission("townymobs.reload"))
                    sender.sendMessage(" /townymobs reload - reloads config.yml");                
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                if (!sender.hasPermission("townymobs.reload")) {
                    sender.sendMessage( "[TownyTNEMobsBridge] PermissionDenied.");
                    return true;
                }
                config.reload();
                loadSettings();
                sender.sendMessage("[TownyTNEMobsBridge] Config.yml reloaded");
                return true;
            }
        }
        return false;
    }
}
