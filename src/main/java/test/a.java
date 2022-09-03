package test;

import me.monmcgt.code.Debug;
import me.monmcgt.code.classes.S2C_Message;

public class a {
    public static void main(String[] args) {
//        System.out.println(check("wowthis123"));
        Debug.println("\u00A7");
        S2C_Message s2C_message = new S2C_Message("§ragicdo§r[5]§r was slained by §r[↑][↑][↑]wdebzeiduo§r[83]§r using a §r包む サーベル§r\n", false);
        Debug.println(s2C_message.getMessage());
        Debug.println(s2C_message.getPlainMessage());
    }

    public static boolean check(String n) {
        if (n.isEmpty() /*&& en.getName().isEmpty()*/) {
            return true;
        }

        if (n.length() == 10) {
            int num = 0;
            int let = 0;
            char[] var4 = n.toCharArray();

            for (char c : var4) {
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        return false;
                    }

                    ++let;
                } else {
                    if (!Character.isDigit(c)) {
                        return false;
                    }

                    ++num;
                }
            }

            return num >= 2 && let >= 2;
        }

        return false;
    }
}
