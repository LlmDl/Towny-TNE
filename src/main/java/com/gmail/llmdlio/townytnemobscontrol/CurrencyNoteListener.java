package com.gmail.llmdlio.townytnemobscontrol;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;

import net.tnemc.core.event.currency.TNECurrencyNoteClaimedEvent;

public class CurrencyNoteListener implements Listener{

    @SuppressWarnings("unused")
    private TownyTNEMobsControl plugin;
    
    public CurrencyNoteListener(TownyTNEMobsControl instance) {
        this.plugin = instance;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void cashedNoteEvent (TNECurrencyNoteClaimedEvent event) {
        Location loc = event.getPlayer().getLocation();
        if (TownyTNEMobsControl.disabledWorlds.contains(loc.getWorld().getName()))
            return;

        if (TownyAPI.getInstance().isWilderness(loc)) {
            event.getPlayer().sendMessage("You cannot deposit your currency note outside of a town's Bank plot.");
            event.setCancelled(true);
            return;
        } else {
            TownBlock townBlock = TownyAPI.getInstance().getTownBlock(loc);
            if (!townBlock.getType().equals(TownBlockType.BANK)) {
                event.getPlayer().sendMessage("You cannot deposit your currency note outside of a town's Bank plot.");
                event.setCancelled(true);
                return;
            } else
                return;
        }
    }
}
