package me.PerpetuallyPerplexed.StaffChat.Utils;

import me.PerpetuallyPerplexed.StaffChat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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




    public static boolean isChatter(Player player) {
        return getChatterAsPlayer(player) != null;
    }

    public static void updateStaffGUI(Inventory inv, Chatter assigned) {

        ItemStack option1 =  new ItemStack(Material.PAPER);
        ItemMeta o1Mea = option1.getItemMeta();
        o1Mea.setDisplayName((assigned.canSee()) ? ChatColor.GRAY + "Disable Staff Chat" : ChatColor.GREEN + "Enable Staff Chat");
        o1Mea.setLore(new ArrayList<>(Arrays.asList("",ChatColor.GRAY + "Toggles whether you can see staff chat or not.")));
        option1.setItemMeta(o1Mea);
        inv.setItem(20,option1);

        ItemStack option2 =  new ItemStack(Material.PAPER);
        ItemMeta o2Mea = option2.getItemMeta();
        o2Mea.setDisplayName((assigned.isMessageToggled()) ? ChatColor.GRAY + "Disable Direct Messaging" : ChatColor.GREEN + "Enable Direct Messaging");
        o2Mea.setLore(new ArrayList<>(Arrays.asList("",ChatColor.GRAY + "Toggles whether you can see directly type into",ChatColor.GRAY + "staff chat or need to use /staffchat <message>")));
        option2.setItemMeta(o2Mea);
        inv.setItem(22,option2);

        ItemStack option3 =  new ItemStack(Material.PAPER);
        ItemMeta o3Mea = option3.getItemMeta();
        o3Mea.setDisplayName((assigned.soundsToggled()) ? ChatColor.GRAY + "Disable Staff Chat sounds" : ChatColor.GREEN + "Enable Staff Chat sounds");
        o3Mea.setLore(new ArrayList<>(Arrays.asList("",ChatColor.GRAY + "Toggles whether you can get sounds from staff chat or not")));
        option3.setItemMeta(o3Mea);
        inv.setItem(24,option3);

        ItemStack enable = new ItemStack(Material.GRAY_DYE);
        ItemStack disable = new ItemStack(Material.LIME_DYE);

        ItemMeta greendMeta = enable.getItemMeta();
        ItemMeta graydMeta = disable.getItemMeta();

        greendMeta.setDisplayName(ChatColor.GREEN + "Click to enable");
        graydMeta.setDisplayName(ChatColor.GREEN + "Click to disable");

        enable.setItemMeta(greendMeta);
        disable.setItemMeta(graydMeta);

        inv.setItem(29,(assigned.canSee()) ? disable : enable);
        inv.setItem(31,(assigned.isMessageToggled()) ? disable : enable);
        inv.setItem(33,(assigned.soundsToggled()) ? disable : enable);
    }

    public static String getReportableName(Rank type) {
        switch (type) {
            case OWNER:
                return instance.getConfig().getString("report-method.owner");
            case ADMIN:
                return instance.getConfig().getString("report-method.admin");
            case MOD:
                return instance.getConfig().getString("report-method.mod");
            case HELPER:
                return instance.getConfig().getString("report-method.helper");
            case CUSTOM1:
                return instance.getConfig().getString("report-method.custom1");
            case CUSTOM2:
                return instance.getConfig().getString("report-method.custom2");
            case CUSTOM3:
                return instance.getConfig().getString("report-method.custom3");
            case CUSTOM4:
                return instance.getConfig().getString("report-method.custom4");
            default:
                return instance.getConfig().getString("report-method.default");
        }
    }

    public static List<String> getReportableNamesList() {
        List<String> returnValue = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            returnValue.add(getReportableName(rank));
        }
        return returnValue;
    }

    enum Rank {
        OWNER,
        ADMIN,
        MOD,
        HELPER,
        CUSTOM1,
        CUSTOM2,
        CUSTOM3,
        CUSTOM4
    }
}
