package org.jwix777.easysetup_screamingbedwars;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SetupGUI implements Listener {
    public void openMyAwesomeMenu(Player player) {

        // Create a GUI with 3 rows (27 slots)
        SGMenu myAwesomeMenu = EasySetup_ScreamingBedwars.spiGUI.create("&cBedwars Control Panel", 3);

        // Create a button
        SGButton myAwesomeButton = new SGButton(
                // Includes an ItemBuilder class with chainable methods to easily
                // create menu items.
               new ItemBuilder(Material.REDSTONE_BLOCK).name("Restart bedwars").build()
        ).withListener((InventoryClickEvent event) -> {
            // Events are cancelled automatically, unless you turn it off
            // for your plugin or for this inventory.
            Bukkit.getPlayer(event.getWhoClicked().getName()).performCommand("bw reload");
        });
        SGButton arenacontrol = new SGButton(
                // Includes an ItemBuilder class with chainable methods to easily
                // create menu items.
                new ItemBuilder(Material.COMPASS).name("Manage arenas").build()
        ).withListener((InventoryClickEvent event) -> new ArenasGUI().open(Bukkit.getPlayer(event.getWhoClicked().getName())));
        // Add the button to your GUI
        myAwesomeMenu.addButton(myAwesomeButton);
        myAwesomeMenu.addButton(arenacontrol);
        // Show the GUI
        player.openInventory(myAwesomeMenu.getInventory());

    }
}
