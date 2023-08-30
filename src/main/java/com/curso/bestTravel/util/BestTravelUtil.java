package com.curso.bestTravel.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil {

    private static final Random random = new Random();

    public static LocalDateTime getRandomSoon(){
        var randomHour = random.nextInt(5-2)+2;
        var now = LocalDateTime.now();
        return now.plusHours(randomHour);
    }

    public static LocalDateTime getRandomLatter(){
        var randomHour = random.nextInt(12-6)+6;
        var now = LocalDateTime.now();
        return now.plusHours(randomHour);
    }
}
