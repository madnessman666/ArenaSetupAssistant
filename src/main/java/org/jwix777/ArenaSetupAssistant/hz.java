package org.jwix777.ArenaSetupAssistant;

import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jwix777.ArenaSetupAssistant.events.BedwarsGameDisabledEvent;
import org.jwix777.ArenaSetupAssistant.events.BedwarsGameEnabledEvent;
import org.screamingsandals.bedwars.api.game.Game;

public class hz implements  Listener {
    @EventHandler
    public void onArenaDisable(BedwarsGameDisabledEvent e) {
        Bukkit.getLogger().info("Fired");
        Game game = e.getGame();
        ArenaSetupAssistant.holos.forEach((Hologram holo) -> {
            if (game.getGameWorld().getName().equals(holo.getPosition().getWorldName())) {
                holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.VISIBLE);
            }
        });
    }
    @EventHandler
    public void onArenaEnabled(BedwarsGameEnabledEvent e) {
        Game game = e.getGame();
        ArenaSetupAssistant.holos.forEach((Hologram holo) -> {
            if (game.getGameWorld().getName().equals(holo.getPosition().getWorldName())) {
                    holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
                }
        });
    }
}
