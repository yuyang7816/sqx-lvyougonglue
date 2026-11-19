package com.sqx.app.base.utils;

import com.sqx.app.base.utils.model.StartAndEndTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chengming
 * @since 2025/9/28
 */
public class DateUtil {

    /**
     * 获取今日起止日期
     */
    public static StartAndEndTime today() {
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        StartAndEndTime startAndEndTime = new StartAndEndTime();
        startAndEndTime.setStartTime(startTime);
        startAndEndTime.setEndTime(endTime);
        return startAndEndTime;
    }

    /**
     *  获取当月起止日期
     */
    public static StartAndEndTime month() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startTime = now.with(TemporalAdjusters.firstDayOfMonth())
                .toLocalDate()
                .atStartOfDay();

        LocalDateTime endTime = now.with(TemporalAdjusters.lastDayOfMonth())
                .toLocalDate()
                .atTime(23, 59, 59, 999_999_999);

        StartAndEndTime startAndEndTime = new StartAndEndTime();
        startAndEndTime.setStartTime(startTime);
        startAndEndTime.setEndTime(endTime);
        return startAndEndTime;
    }

    /**
     * 当年起止日期
     */
    public static StartAndEndTime year() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.with(TemporalAdjusters.firstDayOfYear())
                .toLocalDate()
                .atStartOfDay();

        LocalDateTime endTime = now.with(TemporalAdjusters.lastDayOfYear())
                .toLocalDate()
                .atTime(23, 59, 59, 999_999_999);

        StartAndEndTime startAndEndTime = new StartAndEndTime();
        startAndEndTime.setStartTime(startTime);
        startAndEndTime.setEndTime(endTime);
        return startAndEndTime;
    }

    public static List<LocalDate> dateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dateList = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dateList.add(currentDate);
            currentDate = currentDate.plusDays(1); // 递增一天
        }

        return dateList;
    }
}
