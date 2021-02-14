package me.PerpetuallyPerplexed.StaffChat;


import me.PerpetuallyPerplexed.StaffChat.Utils.Chatter;
import me.PerpetuallyPerplexed.StaffChat.Utils.Events;
import me.PerpetuallyPerplexed.StaffChat.Utils.MegaCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class Main extends JavaPlugin implements Listener {

    public List<Chatter> UsePlayers = new ArrayList<>();
    public String inStaff = ChatColor.DARK_RED + "Staff Chat> ";
    private static Main instance;


    @Override
    public void onEnable() {
        instance = this;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("StaffChat.use")) {
                UsePlayers.add(new Chatter(p,true,false,true));
            }
        }
        loadConfig();
        new MegaCommand(this);
        new Events(this);
        this.getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        //temp
    }



    public void loadConfig() {
        // LoadConfig

        this.saveDefaultConfig();
        if (!validConfig()) {
            getLogger().warning("Invalid Character in config!");
            return;
        }
        inStaff = this.getConfig().getString("chat-prefix");
        inStaff = ChatColor.translateAlternateColorCodes('&', inStaff + " ");

    }
    private boolean validConfig() {
        try {
            this.reloadConfig();
        } catch (Exception e) {
            return false;
        }
        return true;
    }



    public static Main getInstance() {
        return instance;
    }
}
