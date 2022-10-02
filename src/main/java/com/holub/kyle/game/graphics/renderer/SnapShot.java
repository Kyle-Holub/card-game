package com.holub.kyle.game.graphics.renderer;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SnapShot {
    private final int highestScore;
    private final float average;
    private int weight = 1;
    private float xPos;
    private float highestScoreYPos;
    private float averageYPos;

    public void incWeight() {
        weight++;
    }
}
