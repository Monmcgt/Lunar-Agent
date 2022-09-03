package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.send2;

import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$C$SendChat2 {
    public String message;
    public String b;

    public JSON$C$SendChat2(String message, String b) {
        this.message = message;
        this.b = b;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
