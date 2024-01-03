package org.jwix777.ArenaSetupAssistant.utils;

import me.filoghost.holographicdisplays.api.Position;
import org.bukkit.Location;

public class BlockUtils {
    public static Position locToPos(Location loc) {
        Position return_loc;
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        return_loc = Position.of(loc.getWorld(), x, y, z);
        return return_loc;
    }
}
