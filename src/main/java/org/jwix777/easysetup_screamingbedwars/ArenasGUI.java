package org.jwix777.easysetup_screamingbedwars;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.game.Game;

import java.util.List;

public class ArenasGUI implements Listener {
    public void open(Player player) {

        // Create a GUI with 3 rows (27 slots)
        SGMenu myAwesomeMenu = EasySetup_ScreamingBedwars.spiGUI.create("&cBedwars Control Panel", 3);

        // Create a button
        List<Game> games = BedwarsAPI.getInstance().getGames();
        games.forEach((Game game) -> {
            SGButton arena = new SGButton(
                    new ItemBuilder(Material.PAPER).name(game.getName()).build()
            ).withListener((InventoryClickEvent event) -> new ArenaGUI().open(player, game.getName()));
            myAwesomeMenu.addButton(arena);
        });
        // Add the button to your GUI
        // Show the GUI
        player.openInventory(myAwesomeMenu.getInventory());

    }
}
