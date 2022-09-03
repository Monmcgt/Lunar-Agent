package me.monmcgt.code.lunarbuiltinlauncher.httpservers.json.debug.display.title;

import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class JSON$C$SetDisplayTitle {
    public String title;

    public JSON$C$SetDisplayTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
