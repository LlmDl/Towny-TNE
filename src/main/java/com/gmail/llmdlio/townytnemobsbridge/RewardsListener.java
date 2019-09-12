package com.gmail.llmdlio.townytnemobsbridge;

import java.math.BigDecimal;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;

import net.tnemc.core.event.module.impl.AsyncMobRewardEvent;

public class RewardsListener implements Listener {

    @SuppressWarnings("unused")
    private TownyTNEMobsBridge plugin;

    public RewardsListener(TownyTNEMobsBridge instance) {
        this.plugin = instance;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void rewardEvent (AsyncMobRewardEvent event) throws NotRegisteredException {
        Location loc = event.getEntity().getLocation();
        if (TownyTNEMobsBridge.disabledWorlds.contains(loc.getWorld().getName()))
            return;

        if (!TownyAPI.getInstance().isWilderness(loc)) {
            Town town = TownyAPI.getInstance().getTownBlock(loc).getTown();
            if (TownyTNEMobsBridge.exemptedTowns.contains(town.getName()))
                return;
            if (TownyTNEMobsBridge.insideTownMultiplier == 0.0)
                event.setCancelled(true);
            else
                event.setReward(new BigDecimal(TownyTNEMobsBridge.insideTownMultiplier).multiply(event.getReward()));
        }
    }
}
