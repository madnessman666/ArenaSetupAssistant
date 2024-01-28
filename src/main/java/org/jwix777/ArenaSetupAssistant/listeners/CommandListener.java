package org.jwix777.ArenaSetupAssistant.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.PlayerInventory;
import org.jwix777.ArenaSetupAssistant.ArenaSetupAssistant;
import org.jwix777.ArenaSetupAssistant.events.BedwarsGameDisabledEvent;
import org.jwix777.ArenaSetupAssistant.events.BedwarsGameEnabledEvent;
import org.screamingsandals.bedwars.api.game.Game;


public class CommandListener implements Listener {

    @EventHandler
    public void CmdPre(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        String[] msg = message.split(" ");
        Player player = e.getPlayer();
        if (msg.length > 0) {
            if (msg.length > 3) {
                switch (msg[3]) {
                    case "edit": {
                        if (!ArenaSetupAssistant.api.isPlayerPlayingAnyGame(player)) {
                            PlayerInventory inventory = player.getInventory();
                            inventory.clear();
                            Game game = ArenaSetupAssistant.api.getGameByName(msg[2]);
                            if (game != null) {
                                Location loc = game.getSpectatorSpawn();
                                player.setGameMode(GameMode.CREATIVE);
                                player.teleport(loc);
                                Bukkit.getPluginManager().callEvent(new BedwarsGameDisabledEvent(game));
                            }
                        }
                        break;
                    }
                    case "save": {
                        if (!ArenaSetupAssistant.api.isPlayerPlayingAnyGame(player)) {
                            PlayerInventory inventory = player.getInventory();
                            inventory.clear();
                            Game game = ArenaSetupAssistant.api.getGameByName(msg[2]);
                            if (game != null) {
                                Bukkit.getPluginManager().callEvent(new BedwarsGameEnabledEvent(game));
                            }
                        }
                    }
                }
            }
        }
    }
}
