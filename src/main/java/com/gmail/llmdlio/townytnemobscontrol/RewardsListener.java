package com.gmail.llmdlio.townytnemobscontrol;

import java.math.BigDecimal;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;

import net.tnemc.core.TNE;
import net.tnemc.core.event.module.impl.AsyncMobRewardEvent;

public class RewardsListener implements Listener {

    @SuppressWarnings("unused")
    private TownyTNEMobsControl plugin;

    public RewardsListener(TownyTNEMobsControl instance) {
        this.plugin = instance;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void rewardEvent (AsyncMobRewardEvent event) throws NotRegisteredException {
        Location loc = event.getEntity().getLocation();
        if (TownyTNEMobsControl.disabledWorlds.contains(loc.getWorld().getName()))
            return;

        if (!TownyAPI.getInstance().isWilderness(loc)) {
            Town town = TownyAPI.getInstance().getTownBlock(loc).getTown();
            if (TownyTNEMobsControl.exemptedTowns.contains(town.getName()))
                return;
            if (TownyTNEMobsControl.insideTownMultiplier == 0.0)
                event.setCancelled(true);
            else {
                int decimals = TNE.manager().currencyManager().get(loc.getWorld().getName(), event.getCurrency()).getDecimalPlaces();
                BigDecimal reward = new BigDecimal(TownyTNEMobsControl.insideTownMultiplier).multiply(event.getReward()).setScale(decimals, BigDecimal.ROUND_HALF_DOWN);                
                event.setReward(reward);
            }
        }
    }
}
