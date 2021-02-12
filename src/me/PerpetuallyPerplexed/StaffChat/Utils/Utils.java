package me.PerpetuallyPerplexed.StaffChat.Utils;

import me.PerpetuallyPerplexed.StaffChat.Main;
import org.bukkit.entity.Player;

public class Utils {

    
    private static final Main instance;

    static {
        instance = Main.getInstance();
    }
    

    public static String getPermission(Player PrefixCheck) {

        if (PrefixCheck.hasPermission("StaffChat.owner")) {
            return instance.getConfig().getString("prefix.owner");
        }

        if (PrefixCheck.hasPermission("StaffChat.admin")) {
            return instance.getConfig().getString("prefix.admin");
        }

        if (PrefixCheck.hasPermission("StaffChat.mod")) {
            return instance.getConfig().getString("prefix.mod");
        }

        if (PrefixCheck.hasPermission("StaffChat.helper")) {
            return instance.getConfig().getString("prefix.helper");
        }

        if (PrefixCheck.hasPermission("StaffChat.custom1")) {
            return instance.getConfig().getString("prefix.custom1");
        }

        if (PrefixCheck.hasPermission("StaffChat.custom2")) {
            return instance.getConfig().getString("prefix.custom2");
        }

        if (PrefixCheck.hasPermission("StaffChat.custom3")) {
            return instance.getConfig().getString("prefix.custom3");
        }

        if (PrefixCheck.hasPermission("StaffChat.custom4")) {
            return instance.getConfig().getString("prefix.custom4");
        }




        return instance.getConfig().getString("prefix.default");
    }


    public static Chatter getChatterAsPlayer(Player player) {
        for (Chatter c : instance.UsePlayers) {
            if (c.getUser().equals(player)) {
                return c;
            }
        }

        return null;
    }
}
