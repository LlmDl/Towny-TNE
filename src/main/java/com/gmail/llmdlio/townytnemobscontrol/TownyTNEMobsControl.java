package com.gmail.llmdlio.townytnemobscontrol;

import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.llmdlio.townytnemobscontrol.config.TownyTNEMobsControlConfig;

import net.tnemc.core.TNE;

public class TownyTNEMobsControl extends JavaPlugin {
    private TownyTNEMobsControlConfig config = new TownyTNEMobsControlConfig(this);
    public final RewardsListener rewardsListener = new RewardsListener(this);
    public final CurrencyNoteListener currencyNoteListener = new CurrencyNoteListener(this);
    public static List<String> disabledWorlds;
    public static List<String> exemptedTowns;
    public static double insideTownMultiplier;
    public static boolean denyNoteUseOutsideBankPlots;
    public static TNE tne;

    @Override
    public void onEnable() {
        if (!checkTNEVersion())
            onDisable();
        reloadConfig();
        if (!loadSettings())
            onDisable();        
    }

    private void constructStartupMessage() {
        String message = "";
        message += "Welcome to Towny-TNEMobs Control, version " + this.getDescription().getVersion() +". ";
        message += "Created by LlmDl, developer of Towny, with very dear help from creatorfromhell, author of The New Economy. ";
        if (!disabledWorlds.isEmpty())
            message += "The following worlds are exempt from the effects of this plugin: " + disabledWorlds.toString().replace("[", "").replace("]", "") + ". ";
        if (insideTownMultiplier != 1.0 ) {
            if (!exemptedTowns.isEmpty())
                message += "The following towns are exempt from the penalties against money rewards: " + exemptedTowns.toString().replace("[", "").replace("]", "") + ". ";
            message += "The multiplier against currency drops is set to " + insideTownMultiplier + ". ";
        }
        message += "Currency notes are " + (denyNoteUseOutsideBankPlots ? "" : "not ") + "restricted to Towny bank plots."; 
        String split = "  " + WordUtils.wrap(message, 45, System.lineSeparator() + "  ", true); 
        for (String line : split.split(System.lineSeparator()))
            System.out.println(line);
            //getLogger().info(line);
    }

    @Override
    public void onDisable() {
        if (!HandlerList.getRegisteredListeners(this).isEmpty()) {
            HandlerList.unregisterAll(rewardsListener);
            HandlerList.unregisterAll(currencyNoteListener);
        }
        getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("TownyTNEMobsControl"));
        
    }

    private boolean checkTNEVersion() {
        
        tne = (TNE)Bukkit.getPluginManager().getPlugin("TheNewEconomy");
        if (TNE.loader().hasModuleEvent("AsyncMobRewardEvent"))
            return true;
        else {
            getLogger().severe("TNE Version insufficient, go download TNE 0.1.1.8M4");
            return false;
        }
    }

    private boolean loadSettings() {
        try {

            disabledWorlds = config.getConfig().getStringList("Disabled_Worlds");
            exemptedTowns = config.getConfig().getStringList("Exempted_Towns");
            insideTownMultiplier = config.getConfig().getDouble("Inside_Town_Multiplier");
            denyNoteUseOutsideBankPlots = config.getConfig().getBoolean("Deny_Currency_Note_Claiming_Outside_Bank_Plots");
            if (!HandlerList.getRegisteredListeners(this).isEmpty()) {
                HandlerList.unregisterAll(rewardsListener);
                HandlerList.unregisterAll(currencyNoteListener);
            }
            if (insideTownMultiplier != 1.0)
                getServer().getPluginManager().registerEvents(rewardsListener, this);
            if (denyNoteUseOutsideBankPlots)
                getServer().getPluginManager().registerEvents(currencyNoteListener, this);
            constructStartupMessage();            
            return true;
        } catch (Exception e) {
            getLogger().severe("Failed to load settings from config.yml! Disabling TownyTNEMobsControl.");
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
                sender.sendMessage(this.getDescription().getPrefix() + "Version " + this.getDescription().getVersion() + " by LlmDl");
                if (sender.hasPermission("townymobs.reload"))
                    sender.sendMessage(" /townymobs reload - reloads config.yml");                
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                if (!sender.hasPermission("townymobs.reload")) {
                    sender.sendMessage( this.getDescription().getPrefix() + "PermissionDenied.");
                    return true;
                }
                config.reload();
                loadSettings();
                sender.sendMessage(this.getDescription().getPrefix() + "Config.yml reloaded");
                return true;
            }
        }
        return false;
    }
}
