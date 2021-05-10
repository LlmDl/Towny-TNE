package com.gmail.llmdlio.townytne;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.utils.ShopPlotUtil;

import net.tnemc.core.event.module.TNEModuleDataEvent;

public class ShopListener implements Listener{

    @SuppressWarnings("unused")
	private TownyTNE plugin;
    
    public ShopListener (TownyTNE instance) {
        this.plugin = instance;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onShopCreate (TNEModuleDataEvent event) {

    	if (!event.getEventName().equals("SignCreate"))
    		return;
    	if (!event.getData().get("type").equals("item"))
    		return;
        Location loc = (Location) event.getData().get("location");
        if (TownyTNE.disabledWorlds.contains(loc.getWorld().getName()) || !TownyAPI.getInstance().isTownyWorld(loc.getWorld()))
        	return;
        Player player = Bukkit.getPlayer((UUID) event.getData().get("creator"));
        boolean allowedShopCreation = false;
        if (TownyTNE.requireShopPlotOwnership)
            allowedShopCreation = ShopPlotUtil.doesPlayerOwnShopPlot(player, loc);
        else 
            allowedShopCreation = ShopPlotUtil.doesPlayerHaveAbilityToEditShopPlot(player, loc);

        if (!allowedShopCreation) {
        	player.sendMessage(ChatColor.DARK_RED + "Your shop must be made inside of a Shop plot " + (TownyTNE.requireShopPlotOwnership ? "that you own." : "where you can build."));
            event.setCancelled(true);
        }
    }
}
