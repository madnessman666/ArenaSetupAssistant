package org.jwix777.ArenaSetupAssistant.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.PlayerInventory;
import org.jwix777.ArenaSetupAssistant.ArenaSetupAssistant;
import org.jwix777.ArenaSetupAssistant.events.BedwarsGameDisabledEvent;
import org.jwix777.ArenaSetupAssistant.events.BedwarsGameEnabledEvent;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;

import java.util.Objects;

public class PlayerTeleport implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        World world = e.getTo().getWorld();
        World world_from = e.getFrom().getWorld();
        if (!ArenaSetupAssistant.api.isPlayerPlayingAnyGame(player)) {
            PlayerInventory inventory = player.getInventory();
            inventory.clear();
            ArenaSetupAssistant.api.getGames().forEach((Game game) -> {
                if (game.getGameWorld() == world) {
                    if (game.getStatus() == GameStatus.DISABLED) {
                        Bukkit.getPluginManager().callEvent(new BedwarsGameDisabledEvent(game));
                    }
                }
                if (game.getGameWorld() == world_from) {
                    if (game.getStatus() == GameStatus.REBUILDING || game.getStatus() == GameStatus.WAITING) {
                        Bukkit.getPluginManager().callEvent(new BedwarsGameEnabledEvent(game));
                    }
                }
            });
        }
    }

    @EventHandler
    public void CommandPreprocess(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        Player player = e.getPlayer();
        for (String s : msg.split(" ")) {
            if (Objects.equals(s, "edit")) {
                if (!ArenaSetupAssistant.api.isPlayerPlayingAnyGame(player)) {
                    PlayerInventory inventory = player.getInventory();
                    inventory.clear();
                    Game game = ArenaSetupAssistant.api.getGameByName(msg.split(" ")[2]);
                    if (game != null) {
                        Location loc = game.getLobbySpawn();
                        if (player.getWorld() == loc.getWorld()) return;
                        player.teleport(loc);
                        Bukkit.getPluginManager().callEvent(new BedwarsGameDisabledEvent(game));
                    }
                }
            } else if (Objects.equals(s, "save")) {
                if (!ArenaSetupAssistant.api.isPlayerPlayingAnyGame(player)) {
                    PlayerInventory inventory = player.getInventory();
                    inventory.clear();
                    Game game = ArenaSetupAssistant.api.getGameByName(msg.split(" ")[2]);
                    if (game != null) {
                        Bukkit.getPluginManager().callEvent(new BedwarsGameEnabledEvent(game));
                    }
                }
            }
        }
    }
}
