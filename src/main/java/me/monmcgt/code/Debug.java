package me.monmcgt.code;

public class Debug {
    public static boolean DEBUG = false;

    public static void println(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }
}
