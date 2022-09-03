package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.chat.print;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$S$PrintChat {
    public boolean success;
    public String message;

    public JSON$S$PrintChat(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
