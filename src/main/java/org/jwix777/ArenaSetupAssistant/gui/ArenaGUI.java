package org.jwix777.ArenaSetupAssistant.gui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jwix777.ArenaSetupAssistant.ArenaSetupAssistant;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;

public class ArenaGUI implements Listener {

    public void open(Player player, String arenaName){
        SGMenu Menu = ArenaSetupAssistant.spiGUI.create("Arena: ".concat(arenaName), 3);
        Material toggleMaterial;
        String toggleName;
        Game game = BedwarsAPI.getInstance().getGameByName(arenaName);
        if(game.getStatus() == GameStatus.WAITING) {
            toggleMaterial = Material.REDSTONE_BLOCK;
            toggleName = "Stop arena";
        } else {
            toggleMaterial = Material.EMERALD_BLOCK;
            toggleName = "Enable arena";
        }
        SGButton toggle = new SGButton(
                // Includes an ItemBuilder class with chainable methods to easily
                // create menu items.
                new ItemBuilder(toggleMaterial).name(toggleName).build()
        ).withListener((InventoryClickEvent event) -> {
            if(game.getStatus() == GameStatus.WAITING){
                player.performCommand("bw admin " + arenaName + " edit");
            } else {
                player.performCommand("bw admin " + arenaName + " save");
            }
            open(player, arenaName);
        });
        SGButton back = new SGButton(
                new ItemBuilder(Material.BARRIER).name("Back").build()
        ).withListener((InventoryClickEvent event) -> new ArenasGUI().open(player));
        SGButton setSpawner = new SGButton(
                new ItemBuilder(Material.MOB_SPAWNER).name("Set Spawner").build()
        ).withListener((InventoryClickEvent event) -> new SpawnerGUI().open(player, arenaName));
        Menu.addButton(toggle);
        Menu.addButton(setSpawner);
        Menu.addButton(back);
        player.openInventory(Menu.getInventory());
    }
}
