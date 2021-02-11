package me.PerpetuallyPerplexed.StaffChat.Utils;

import me.PerpetuallyPerplexed.StaffChat.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class MegaCommand implements CommandExecutor {

    private final Main instance;

    public MegaCommand(Main instance) {
        this.instance = instance;
        instance.getCommand("staffchat").setExecutor(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender,@Nonnull Command command,@Nonnull String label,@Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player senderPlayer = (Player) sender;
        if (!(senderPlayer.hasPermission("schat.use"))) {
            senderPlayer.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }
        String Prefix = Utils.getPermission(senderPlayer);
        if (args.length < 1) {
            senderPlayer.sendMessage(ChatColor.RED + "Usage: /staffchat <toggle/your message>");
            return true;
        }

        switch (args[0]) {
            case "toggle":
                if (instance.UsePlayers.contains(senderPlayer)) {
                    for (Player Staff : instance.UsePlayers) {
                        Staff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + Prefix + " " + senderPlayer.getName() + " &aleft your chat!"));
                    }

                    instance.UsePlayers.remove(senderPlayer);
                    return true;
                }
                // Add to Schat
                instance.UsePlayers.add(senderPlayer);
                for (Player staff : instance.UsePlayers) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + Prefix + " " + senderPlayer.getName() + " &ajoined your chat!"));
                    // /playsound minecraft:entity.player.levelup ambient PerplexedPerson ~ ~ ~ 10 2
                    staff.playSound(staff.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 2);
                }
                break;
            case "reload":
                instance.loadConfig();
                //noinspection ConstantConditions
                senderPlayer.sendMessage(instance.getConfig().getString("reload-message"));
                break;
            default:
                String message;
                StringBuilder sb = new StringBuilder();
                for (int i = 0;i < args.length;i++) {
                    sb.append(args[i]);
                    sb.append(" ");
                }
                message = sb.toString();

                for (Player staff : instance.UsePlayers) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + "&r" + Prefix + " "+ senderPlayer.getName() + ChatColor.WHITE +": " + message));
                    if (!(staff == senderPlayer)) {
                        // /playsound minecraft:entity.arrow.hit_player ambient PerplexedPerson ~~~10 1
                        staff.playSound(staff.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
                    }
                }
                break;

        }
        return true;
    }
}
