package org.jwix777.easysetup_screamingbedwars;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.ItemSpawnerType;

public class SpawnerGUI implements Listener {

    public SpawnerGUI() {
    }


    public void open(Player player, String arenaName){
        HolographicDisplaysAPI holoapi = EasySetup_ScreamingBedwars.holoapi;
        SGMenu Menu = EasySetup_ScreamingBedwars.spiGUI.create("Arena: ".concat(arenaName).concat(" spawners"), 3);
        Game game = BedwarsAPI.getInstance().getGameByName(arenaName);
        BedwarsAPI.getInstance().getItemSpawnerTypes().forEach((ItemSpawnerType spawner) -> {
            SGButton butt = new SGButton(
                    new ItemBuilder(spawner.getMaterial()).name(spawner.getItemName()).build()
            ).withListener((InventoryClickEvent event) -> {
                Bukkit.getLogger().info(spawner.getName());

                Bukkit.getPlayer(event.getWhoClicked().getName()).performCommand("bw admin " + arenaName + " spawner add " + spawner.getConfigKey());
                Hologram holo = holoapi.createHologram(player.getLocation());
                holo.getLines().appendText(spawner.getItemName());
                EasySetup_ScreamingBedwars.holos.add(holo);
            });
            Menu.addButton(butt);
        });
        SGButton back = new SGButton(
                new ItemBuilder(Material.BARRIER).name("Back").build()
        ).withListener((InventoryClickEvent event) -> new ArenaGUI().open(player, arenaName));
        Menu.addButton(back);
        player.openInventory(Menu.getInventory());

    }
}
