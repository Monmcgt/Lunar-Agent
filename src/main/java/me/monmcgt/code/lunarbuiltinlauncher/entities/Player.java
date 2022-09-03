package me.monmcgt.code.lunarbuiltinlauncher.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Player {
    private String name;
    private double posX;
    private double posY;
    private double posZ;
    private double yaw;
    private double pitch;
}
