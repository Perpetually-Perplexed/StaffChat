package me.PerpetuallyPerplexed.StaffChat.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GUI {

    private final Chatter assigned;
    private final Inventory thisGUI = Bukkit.createInventory(null,54, ChatColor.DARK_GRAY + "Your settings");


    public GUI(Chatter assigned) {
        this.assigned = assigned;
        assigned.setSettings(true);
        createInventory();
    }


    private void createInventory() {
        ItemStack[] is = new ItemStack[54];
        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta pMeta =pane.getItemMeta();
        //noinspection ConstantConditions
        pMeta.setDisplayName(" ");
        pane.setItemMeta(pMeta);
        for (int i = 0;i < 54;i++) {
            is[i] = pane;
        }
        thisGUI.setStorageContents(is);

        // 1st Paper Slot 20
        // 2nd Paper slot 22
        // 3rd Paper slot 24

        ItemStack option1 =  new ItemStack(Material.PAPER);
        ItemMeta o1Mea = option1.getItemMeta();
        o1Mea.setDisplayName((assigned.canSee()) ? ChatColor.GRAY + "Disable Staff Chat" : ChatColor.GREEN + "Enable Staff Chat");
        o1Mea.setLore(new ArrayList<>(Arrays.asList("",ChatColor.GRAY + "Toggles whether you can see staff chat or not.")));
        option1.setItemMeta(o1Mea);
        thisGUI.setItem(20,option1);

        ItemStack option2 =  new ItemStack(Material.PAPER);
        ItemMeta o2Mea = option2.getItemMeta();
        o2Mea.setDisplayName((assigned.isMessageToggled()) ? ChatColor.GRAY + "Disable Direct Messaging" : ChatColor.GREEN + "Enable Direct Messaging");
        o2Mea.setLore(new ArrayList<>(Arrays.asList("",ChatColor.GRAY + "Toggles whether you can see directly type into",ChatColor.GRAY + "staff chat or need to use /staffchat <message>")));
        option2.setItemMeta(o2Mea);
        thisGUI.setItem(22,option2);

        ItemStack option3 =  new ItemStack(Material.PAPER);
        ItemMeta o3Mea = option3.getItemMeta();
        o3Mea.setDisplayName((assigned.soundsToggled()) ? ChatColor.GRAY + "Disable Staff Chat" : ChatColor.GREEN + "Enable Staff Chat");
        o3Mea.setLore(new ArrayList<>(Arrays.asList("",ChatColor.GRAY + "Toggles whether you can see staff chat or not.")));
        option3.setItemMeta(o3Mea);
        thisGUI.setItem(24,option3);

        ItemStack enable = new ItemStack(Material.GRAY_DYE);
        ItemStack disable = new ItemStack(Material.LIME_DYE);

        ItemMeta greendMeta = enable.getItemMeta();
        ItemMeta graydMeta = disable.getItemMeta();

        greendMeta.setDisplayName(ChatColor.GREEN + "Click to enable");
        graydMeta.setDisplayName(ChatColor.GREEN + "Click to disable");

        enable.setItemMeta(greendMeta);
        disable.setItemMeta(graydMeta);

        thisGUI.setItem(29,(assigned.canSee()) ? disable : enable);
        thisGUI.setItem(31,(assigned.isMessageToggled()) ? disable : enable);
        thisGUI.setItem(33,(assigned.soundsToggled()) ? disable : enable);
    }

    public void openGUI() {
        assigned.getUser().openInventory(thisGUI);
    }



}
