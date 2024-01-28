package org.jwix777.ArenaSetupAssistant.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.screamingsandals.bedwars.api.game.Game;

public class BedwarsGameEnabledEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Game game;

    public BedwarsGameEnabledEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }


    /**
     * Thing for spigot
     *
     * @return HandlerList
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
