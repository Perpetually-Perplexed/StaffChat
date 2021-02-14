package me.PerpetuallyPerplexed.StaffChat.Utils;

import com.mysql.jdbc.Util;
import me.PerpetuallyPerplexed.StaffChat.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Events implements Listener {

    private final Main instance;

    public Events(Main instance) {
        this.instance = instance;
        instance.getServer().getPluginManager().registerEvents(this,instance);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player Chatter = event.getPlayer();
        if (!Utils.isChatter(Chatter)) {
            return;
        }
        if (!Utils.getChatterAsPlayer(Chatter).isMessageToggled()) {
            return;
        }
        event.setCancelled(true);
        String Prefix = Utils.getPermission(Chatter);
        for (Chatter staffC : instance.UsePlayers) {
            if (!staffC.canSee()) {
                continue;
            }
            Player Staff = staffC.getUser();
            Staff.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.inStaff + "&r" + Prefix + " "+ Chatter.getName() + ChatColor.WHITE +": " + event.getMessage()));
            if (!(Staff == Chatter) && staffC.soundsToggled()) {
                // /playsound minecraft:entity.arrow.hit_player ambient PerplexedPerson ~~~10 1
                Staff.playSound(Staff.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
            }

        }
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        instance.UsePlayers.remove(Utils.getChatterAsPlayer(event.getPlayer()));
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("StaffChat.use")) {
            instance.UsePlayers.add(new Chatter(event.getPlayer(),true,false,true));
        }
    }

    @EventHandler
    public void onGUIOpen(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        if (!Utils.isChatter(player)) {
            return;
        }


        Chatter playerChat = Utils.getChatterAsPlayer(player);

        //noinspection ConstantConditions
        if (!playerChat.inSettings()) {
            return;
        }


        event.setCancelled(true);


        ItemStack enable = new ItemStack(Material.LIME_DYE);
        ItemStack disable = new ItemStack(Material.GRAY_DYE);

        ItemMeta greendMeta = enable.getItemMeta();
        ItemMeta graydMeta = disable.getItemMeta();

        greendMeta.setDisplayName(ChatColor.GREEN + "Click to enable");
        graydMeta.setDisplayName(ChatColor.GREEN + "Click to disable");
        switch (event.getSlot()) {
            case 29:
                playerChat.setCanSee(!playerChat.canSee());
                player.sendMessage(ChatColor.GREEN + "Turned staff chat " + (playerChat.canSee() ? "on" : "off"));
                player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,10,2);
                event.getInventory().setItem(29,(playerChat.canSee()) ? disable : enable);
                break;
            case 31:
                playerChat.setMessageToggled(!playerChat.isMessageToggled());
                player.sendMessage(ChatColor.GREEN + "Turned direct messaging into staff chat " + (playerChat.canSee() ? "on" : "off"));
                player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,10,2);
                event.getInventory().setItem(31,(playerChat.isMessageToggled()) ? disable : enable);
                break;
            case 33:
                playerChat.setSounds(!playerChat.soundsToggled());
                player.sendMessage(ChatColor.GREEN + "Turned sounds from StaffChat " + (playerChat.canSee() ? "on" : "off"));
                player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,10,2);
                event.getInventory().setItem(33,(playerChat.isMessageToggled()) ? disable : enable);
                break;
            default:
                return;
        }

        Utils.updateStaffGUI(player.getOpenInventory().getTopInventory(),playerChat);
    }

    @EventHandler
    public void onInvLeave(InventoryCloseEvent event) {
        if (Utils.isChatter((Player) event.getPlayer())) {

        }

        //noinspection ConstantConditions
        if (Utils.getChatterAsPlayer((Player) event.getPlayer()).inSettings()) {
            //noinspection ConstantConditions
            Utils.getChatterAsPlayer((Player) event.getPlayer()).setSettings(false);
        }
    }
}
