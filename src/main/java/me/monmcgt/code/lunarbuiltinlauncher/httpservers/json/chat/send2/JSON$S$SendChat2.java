package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.send2;

import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$S$SendChat2 {
    public boolean success;
    public String message;

    public JSON$S$SendChat2(boolean success, String message) {
        this.success = success;
        this.message = message;
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
}
