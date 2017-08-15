package com.qxcmp.framework.support;

import org.springframework.stereotype.Component;

@Component
public class TimeDurationHelper {

    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    public String convert(long duration) {
        long days = duration / DAY;
        long hours = (duration - days * DAY) / HOUR;
        long minutes = (duration - days * DAY - hours * HOUR) / MINUTE;
        long seconds = (duration - days * DAY - hours * HOUR - minutes * MINUTE) / SECOND;

        if (days > 0) {
            return String.format("%s天%s小时%s分%s秒", days, hours, minutes, seconds);
        }
        if (hours > 0) {
            return String.format("%s小时%s分%s秒", hours, minutes, seconds);
        }
        if (minutes > 0) {
            return String.format("%s分%s秒", minutes, seconds);
        }
        return String.format("%s秒", seconds);
    }
}