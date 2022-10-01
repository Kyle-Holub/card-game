package com.holub.kyle.game.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecondTimer {

    @Setter
    private long startTime;
    
    public void start() {
        startTime = getTime();
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getElapsedTimeInSeconds() {
        long currentTime = getTime();
        long elapsedTime = (currentTime - startTime);
        return elapsedTime;
    }
}