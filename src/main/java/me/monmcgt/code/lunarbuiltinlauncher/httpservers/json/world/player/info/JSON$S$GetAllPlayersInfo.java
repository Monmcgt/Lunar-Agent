package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.world.player.info;

import lombok.NoArgsConstructor;
import lombok.ToString;
import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$S$GetAllPlayersInfo {
    public boolean success;
    public String message;
    public PlayerInfo[] players;

    public JSON$S$GetAllPlayersInfo(boolean success, String message, PlayerInfo[] players) {
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

    public PlayerInfo[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerInfo[] players) {
        this.players = players;
    }
}
