package org.jwix777.ArenaSetupAssistant;

import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.screamingsandals.bedwars.api.events.BedWarsGameDisabledEvent;
import org.screamingsandals.bedwars.api.events.BedWarsGameEnabledEvent;
import org.screamingsandals.bedwars.api.game.Game;

import java.util.Objects;

public class hz implements  Listener {
    @EventHandler
    public void onArenaDisable(BedWarsGameDisabledEvent e){
        Bukkit.getLogger().info("Fired");
        Game game = e.getGame();
        ArenaSetupAssistant.holos.forEach((Hologram holo) -> {
            if(Objects.equals(game.getGameWorld().getName(), holo.getPosition().getWorldName())) {
                holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.VISIBLE);
            }
        });
    }
    @EventHandler
    public void onArenaEnabled(BedWarsGameEnabledEvent e) {
        Game game = e.getGame();
        ArenaSetupAssistant.holos.forEach((Hologram holo) -> {
                if(Objects.equals(game.getGameWorld().getName(), holo.getPosition().getWorldName())) {
                    holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
                }
        });
    }
}
