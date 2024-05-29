package com.sertann.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PostServiceUtil {

    public static String calculateElapsedTime(LocalDateTime creationTime) {

        LocalDateTime now = LocalDateTime.now(); //now time

        //the time between the created time and the present time

        Duration duration = Duration.between(creationTime, now);


        long seconds = duration.getSeconds();

        if(seconds < 86400 ){ // 24 hours
            if(seconds<3600){ // 1 hours
                if(seconds<60){ // 1 minutes
                    return seconds + " second ago";
                }else {
                    long minutes = seconds / 60;
                    return minutes + " minutes ago";
                }
            }else {
                long hours = seconds / 3600;
                return hours + " hours ago";
            }
        }else if(duration.toDays() < 365) { // 1 years
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
            String monthName = MONTH_NAMES[creationTime.getMonthValue() - 1];
            return creationTime.format(formatter) + " " + monthName;
        }else {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy");
            String monthName = MONTH_NAMES[creationTime.getMonthValue() - 1];
            return creationTime.format(formatter1) + " " + monthName  + " " + creationTime.format(formatter2) ;

        }


    }

    private static final String[] MONTH_NAMES = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

}

