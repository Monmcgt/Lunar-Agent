package me.monmcgt.code.bedwarsstatslunarinject.apis.responses;

public class HypixelKey$Response {
    public boolean success;
    public Record record;
    public String cause;

    public static class Record {
        public String key;
        public String owner;
        public int limit;
        public int queriesInPastMin;
        public long totalQueries;
    }
}
