package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.fps;

import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$S$FPS {
    public boolean success;
    public String fps;

    public JSON$S$FPS(boolean success, String fps) {
        this.success = success;
        this.fps = fps;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }
}
