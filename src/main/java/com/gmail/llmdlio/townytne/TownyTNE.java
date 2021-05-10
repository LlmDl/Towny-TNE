package com.gmail.llmdlio.townytne;

import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.llmdlio.townytne.config.TownyTNEConfig;
import com.palmergames.bukkit.util.Version;

import net.tnemc.core.TNE;

public class TownyTNE extends JavaPlugin {
    private TownyTNEConfig config = new TownyTNEConfig(this);
    public final RewardsListener rewardsListener = new RewardsListener(this);
    public final CurrencyNoteListener currencyNoteListener = new CurrencyNoteListener(this);
    public final ShopListener shopListener = new ShopListener(this);
    public static List<String> disabledWorlds;
    public static List<String> exemptedTowns;
    public static double insideTownMultiplier;
    public static boolean denyNoteUseOutsideBankPlots;
    public static boolean enforceShopPlots;
    public static boolean requireShopPlotOwnership;
    public static TNE tne;

    private static Version requiredTownyVersion = Version.fromString("0.97.0.0"); 
    
    @Override
    public void onEnable() {
    	Plugin towny = getServer().getPluginManager().getPlugin("Towny");
		if (!townyVersionCheck(towny.getDescription().getVersion())) {
			getLogger().severe("Towny version does not meet required version: " + requiredTownyVersion.toString());
			onDisable();
		} else
			getLogger().info("Towny version " + towny.getDescription().getVersion() + " found.");

    	if (!checkTNEVersion())
            onDisable();
        reloadConfig();
        if (!loadSettings())
            onDisable();
        else {
            killListeners();
            registerListeners();
            constructStartupMessage(); 
            getLogger().info("Towny-TNE has enabled successfully!");
        }
    }

    @Override
    public void onDisable() {
        killListeners();
        getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("Towny-TNE"));
        
    }
	
    private boolean townyVersionCheck(String version) {
		Version ver = Version.fromString(version);
		
		return ver.compareTo(requiredTownyVersion) >= 0;
    }

    private boolean checkTNEVersion() {
        
        tne = (TNE)Bukkit.getPluginManager().getPlugin("TheNewEconomy");
        if (TNE.loader().hasModuleEvent("AsyncMobRewardEvent"))
            return true;
        else {
            getLogger().severe("TNE Version insufficient, go download TNE 0.1.1.9 or newer.");
            return false;
        }
    }

    private boolean loadSettings() {
        try {
            disabledWorlds = config.getConfig().getStringList("Disabled_Worlds");
            exemptedTowns = config.getConfig().getStringList("Exempted_Towns");
            insideTownMultiplier = config.getConfig().getDouble("Inside_Town_Multiplier");
            denyNoteUseOutsideBankPlots = config.getConfig().getBoolean("Deny_Currency_Note_Claiming_Outside_Bank_Plots");
            enforceShopPlots = config.getConfig().getBoolean("Enforce_Shop_Plots");
            requireShopPlotOwnership = config.getConfig().getBoolean("Require_Shop_Plot_Ownershop");
      
            return true;
        } catch (Exception e) {
            getLogger().severe("Failed to load settings from config.yml! Disabling Towny-TNE.");
            return false;
        }
    }
    
    private void killListeners() {
        if (!HandlerList.getRegisteredListeners(this).isEmpty()) {
            HandlerList.unregisterAll(rewardsListener);
            HandlerList.unregisterAll(currencyNoteListener);
            HandlerList.unregisterAll(shopListener);
        }
    }
    
    private void registerListeners() { 
        if (insideTownMultiplier != 1.0)
            getServer().getPluginManager().registerEvents(rewardsListener, this);
        if (denyNoteUseOutsideBankPlots)
            getServer().getPluginManager().registerEvents(currencyNoteListener, this);
        if (enforceShopPlots)
            getServer().getPluginManager().registerEvents(shopListener, this);
    }

    private void constructStartupMessage() {
        String message = "";
        message += "Welcome to Towny-TNE, version " + this.getDescription().getVersion() +". ";
        message += "Created by LlmDl, developer of Towny, with very dear help from creatorfromhell, author of The New Economy. ";
        if (!disabledWorlds.isEmpty())
            message += "The following worlds are exempt from the effects of this plugin: " + disabledWorlds.toString().replace("[", "").replace("]", "") + ". ";
        if (insideTownMultiplier != 1.0 ) {
            if (!exemptedTowns.isEmpty())
                message += "The following towns are exempt from the penalties against money rewards: " + exemptedTowns.toString().replace("[", "").replace("]", "") + ". ";
            message += "The multiplier against currency drops in non-exempted towns is set to " + insideTownMultiplier + ". ";
        }
        message += "Currency notes are " + (denyNoteUseOutsideBankPlots ? "" : "not ") + "restricted to Towny bank plots. "; 
        message += "Shops are " + (enforceShopPlots ? "" : "not ") + "restricted to Towny shop plots. ";
        if (enforceShopPlots)
            message += (requireShopPlotOwnership ? "Players must own the shop plots personally to create a shop. " : "Players must be able to build in the shop plot to create a shop. ");
        
        String split = WordUtils.wrap(message, 44, System.lineSeparator(), true); 
        for (String line : split.split(System.lineSeparator()))
            System.out.println("* " + line.trim());
    }

    public void reloadConfig() { 
        if (!getDataFolder().exists())  
            getDataFolder().mkdirs(); 
        config.reload();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("towny-tne")) {
            if (args.length == 0) {
                if (!sender.hasPermission("townytne.version")) {
                    return true;
                }
                sender.sendMessage(this.getDescription().getPrefix() + "Version " + this.getDescription().getVersion() + " by LlmDl");
                if (sender.hasPermission("townytne.reload"))
                    sender.sendMessage(" /towny-tne reload - reloads config.yml");                
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")){
                if (!sender.hasPermission("townytne.reload")) {
                    return true;
                }
                config.reload();
                if (loadSettings())
                    sender.sendMessage(this.getDescription().getPrefix() + "Config.yml reloaded");
                else
                    onDisable();
                return true;
            }
        }
        return false;
    }
}
