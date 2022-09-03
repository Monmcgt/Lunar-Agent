package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.world.player.all;

import lombok.NoArgsConstructor;
import lombok.ToString;
import me.monmcgt.code.lunarbuiltinlauncher.entities.Player;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$S$GetAllPlayers {
    public boolean success;
    public String message;
    public Player[] players;

    public JSON$S$GetAllPlayers(boolean success, String message, Player[] players) {
        this.success = success;
        this.message = message;
        this.players = players;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
