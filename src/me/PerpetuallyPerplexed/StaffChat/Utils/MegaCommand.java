package me.PerpetuallyPerplexed.StaffChat.Utils;

import me.PerpetuallyPerplexed.StaffChat.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MegaCommand implements TabExecutor {

    private final Main instance;

    public MegaCommand(Main instance) {
        this.instance = instance;
        instance.getCommand("staffchat").setExecutor(this);
        instance.getCommand("staffchat").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender,@Nonnull Command command,@Nonnull String label,@Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player senderPlayer = (Player) sender;
        if (!(senderPlayer.hasPermission("StaffChat.use"))) {
            senderPlayer.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }
        String Prefix = Utils.getPermission(senderPlayer);
        if (args.length < 1) {
            if (senderPlayer.hasPermission("StaffChat.reload")) {
                senderPlayer.sendMessage(ChatColor.RED + "Usage: /staffchat <toggle/<message>/reload>");
                return true;
            }
            senderPlayer.sendMessage(ChatColor.RED + "Usage: /staffchat <toggle/<message>");
            return true;
        }

        switch (args[0]) {
            case "see":
                if (instance.UsePlayers.contains(Utils.getChatterAsPlayer(senderPlayer))) {
                    for (Chatter staff : instance.UsePlayers) {
                        Player playerStaff = staff.getUser();

                        playerStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + Prefix + " " + senderPlayer.getName() + " &aleft your chat!"));
                    }

                    instance.UsePlayers.remove(Utils.getChatterAsPlayer(senderPlayer));
                    return true;
                }
                // Add to Schat
                instance.UsePlayers.add(new Chatter(senderPlayer,true,false,true));
                for (Chatter staffChatter : instance.UsePlayers) {
                    Player staff = staffChatter.getUser();
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + Prefix + " " + senderPlayer.getName() + " &ajoined your chat!"));
                    // /playsound minecraft:entity.player.levelup ambient PerplexedPerson ~ ~ ~ 10 2
                    if (staffChatter.soundsToggled()) {
                        staff.playSound(staff.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 2);
                    }
                }
                break;
            case "reload":
                if (!(senderPlayer.hasPermission("StaffChat.reload"))) {
                    senderPlayer.sendMessage(ChatColor.RED + "You don't have permission  to do this command!");
                    return true;
                }
                instance.loadConfig();
                //noinspection ConstantConditions
                senderPlayer.sendMessage(instance.getConfig().getString("reload-message"));
                break;
            default:
                String message;
                StringBuilder sb = new StringBuilder();
                for (String arg : args) {
                    sb.append(arg);
                    sb.append(" ");
                }
                message = sb.toString();

                for (Chatter staffC : instance.UsePlayers) {
                    Player staff = staffC.getUser();
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + "&r" + Prefix + " "+ senderPlayer.getName() + ChatColor.WHITE +": " + message));
                    if (!(staff == sender) && staffC.soundsToggled()) {
                        // /playsound minecraft:entity.arrow.hit_player ambient PerplexedPerson ~~~10 1
                        staff.playSound(staff.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
                    }
                }
                break;

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        List<String> returnValue = new ArrayList<>();
        if (!sender.hasPermission("StaffChat.use")) {
            return returnValue;
        }
        returnValue.add("toggle");
        returnValue.add("see");
        if (sender.hasPermission("StaffChat.reload")) {
            returnValue.add("reload");
        }
        if (args.length == 1) {
            List<String> otherReturn = new ArrayList<>();
            for (String s : returnValue) {
                if (s.startsWith(args[0].toLowerCase())) {
                    otherReturn.add(s);
                }
            }
            return otherReturn;
        }
        return returnValue;
    }
}
