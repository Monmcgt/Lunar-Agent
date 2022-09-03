package me.monmcgt.code.bedwarsstatslunarinject.stats;

import me.monmcgt.code.bedwarsstatslunarinject.apis.responses.HypixelStats$Response;

public class BedwarsStatsData {
    public int bedwarsLevel;
    public float fkdr;
    public float winrate;
    public int winstreak;
    public HypixelStats$Response hypixelStats$Response;

    public boolean isFromCache = false;

    public BedwarsStatsData(int bedwarsLevel, float fkdr, float winrate, int winstreak, HypixelStats$Response hypixelStats$Response) {
        this.bedwarsLevel = bedwarsLevel;
        this.fkdr = fkdr;
        this.winrate = winrate;
        this.winstreak = winstreak;
        this.hypixelStats$Response = hypixelStats$Response;
    }
}
