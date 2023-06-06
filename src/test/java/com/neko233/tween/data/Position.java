package com.neko233.tween.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SolarisNeko
 * Date on 2023-01-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {

    private float x;
    private float y;
    private float z;

    public static Position zero() {
        return new Position(0, 0, 0);
    }
}
