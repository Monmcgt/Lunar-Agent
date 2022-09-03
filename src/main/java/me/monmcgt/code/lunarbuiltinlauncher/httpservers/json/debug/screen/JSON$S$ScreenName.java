package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.screen;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class JSON$S$ScreenName {
    public boolean success;

    public String name;

    public JSON$S$ScreenName(boolean success, String name) {
        this.success = success;
        this.name = name;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
