package me.PerpetuallyPerplexed.StaffChat.Utils;

import me.PerpetuallyPerplexed.StaffChat.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    private final Main instance;

    public Events(Main instance) {
        this.instance = instance;
        instance.getServer().getPluginManager().registerEvents(this,instance);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player Chatter = event.getPlayer();
        if (!(instance.UsePlayers.contains(Utils.getChatterAsPlayer(Chatter)))) return;
        event.setCancelled(true);
        String Prefix = Utils.getPermission(Chatter);
        for (Chatter staffC : instance.UsePlayers) {
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
}
