package org.jwix777.ArenaSetupAssistant;

import com.samjakob.spigui.SpiGUI;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jwix777.ArenaSetupAssistant.gui.EasySetupGUI;
import org.jwix777.ArenaSetupAssistant.utils.BlockUtils;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.Team;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStore;
import org.screamingsandals.bedwars.api.game.ItemSpawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class ArenaSetupAssistant extends JavaPlugin implements Listener {
    public static SpiGUI spiGUI;
    public static HolographicDisplaysAPI holoapi;

    public static List<Hologram> holos =  new ArrayList<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().getPlugin("HolographicDisplays"));
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().getPlugin("BedWars"));
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars")) {
            getLogger().severe("*** Bedwars is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }
        BedwarsAPI api = BedwarsAPI.getInstance();
        Bukkit.getServer().getPluginManager().registerEvents(new hz(), this);
        while (Objects.equals(api, ObjectUtils.NULL)) {
            api = BedwarsAPI.getInstance();
            try {
                wait(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        holoapi = HolographicDisplaysAPI.get(this);
        spiGUI = new SpiGUI(this);
        for(Game game : api.getGames()) {

            for (ItemSpawner spawner : game.getItemSpawners()) {
                Hologram hologram = holoapi.createHologram(BlockUtils.locToPos(spawner.getLocation()).add(0.0D, 1.0D, 0.0D));
                hologram.getLines().appendText(spawner.getItemSpawnerType().getItemName());
                hologram.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
                holos.add(hologram);
            }
            for (Team team : game.getAvailableTeams()) {
                Hologram hologram = holoapi.createHologram(BlockUtils.locToPos(team.getTeamSpawn()).add(0, 1, 0));
                hologram.getLines().appendText(team.getName().concat(" spawn"));
                hologram.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
                holos.add(hologram);
                Hologram bed_holo = holoapi.createHologram(BlockUtils.locToPos(team.getTargetBlock()).add(0, 1, 0));
                bed_holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
                holos.add(bed_holo);
            }
            for (GameStore store : game.getGameStores()) {
                Hologram hologram = holoapi.createHologram(store.getStoreLocation().add(0, 1D, 0));
                hologram.getLines().appendText(store.getShopCustomName().concat(" shop"));
                hologram.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);
                holos.add(hologram);
            }
        }
        Logger logger = Bukkit.getLogger();
        Bukkit.getPluginCommand("asa").setExecutor(new EasySetupGUI());
        logger.info("Started ArenaSetupAssistant");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger logger = Bukkit.getLogger();
        logger.info("Disabled ArenaSetupAssistant");
    }
}

