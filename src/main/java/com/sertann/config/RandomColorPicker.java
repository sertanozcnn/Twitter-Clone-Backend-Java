package com.sertann.config;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomColorPicker {


    public static String getRandomColor() {


        List<String> RANDOM_COLORS = Arrays.asList(
                "#AB48BE", "#7B1FA2", "#79929E", "#455A65", "#EC407A",
                "#C1175A", "#5D6AC0", "#0388D2", "#0098A7", "#689F39",
                "#34691E", "#8C6E63", "#EF6C00", "#F6511E", "#BF360E"
        );

        Random random = new Random();
        int index = random.nextInt(RANDOM_COLORS.size());
        return RANDOM_COLORS.get(index);


    }


}
