package org.jwix777.easysetup_screamingbedwars;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasySetupGUI implements CommandExecutor {
    @Override //переопределяем метод
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        //CommandSender - отправляющий команду, Command - команда, String str я никогда не использовал, мне хватало cmd, String[] args - массив аргументов.
       new SetupGUI().openMyAwesomeMenu(Bukkit.getPlayer(sender.getName()));
        return false; //вернем "ложь" если команда выполнена неправильно (настраивается в plugin.yml)
    }
}
