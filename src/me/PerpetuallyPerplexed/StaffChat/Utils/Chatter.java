package me.PerpetuallyPerplexed.StaffChat.Utils;

import org.bukkit.entity.Player;

public class Chatter  {

    private final Player user;
    private boolean canSee;
    private boolean messageToggled;
    private boolean sounds;


    public Chatter(Player user,boolean canSee,boolean messageToggled,boolean sounds) {
        this.user = user;
        this.canSee = canSee;
        this.messageToggled = messageToggled;
        this.sounds = sounds;
    }

    public boolean canSee() {
        return canSee;
    }

    public boolean isMessageToggled() {
        return messageToggled;
    }

    public boolean soundsToggled() {
        return sounds;
    }

    public Player getUser() {
        return user;
    }

    public void setCanSee(boolean canSee) {
        this.canSee = canSee;
    }

    public void setMessageToggled(boolean messageToggled) {
        this.messageToggled = messageToggled;
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }

}
