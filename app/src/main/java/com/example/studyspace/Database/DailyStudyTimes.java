package com.example.studyspace.Database;

import java.util.LinkedHashMap;
import java.util.Map;

public class DailyStudyTimes {
    private Map<String, Long> totalTimes;
    private Map<String, Long> morningTimes;
    private Map<String, Long> afternoonTimes;
    private Map<String, Long> eveningTimes;

    public DailyStudyTimes() {
        this.totalTimes = new LinkedHashMap<>();
        this.morningTimes = new LinkedHashMap<>();
        this.afternoonTimes = new LinkedHashMap<>();
        this.eveningTimes = new LinkedHashMap<>();
    }

    public Map<String, Long> getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Map<String, Long> totalTimes) {
        this.totalTimes = totalTimes;
    }

    public Map<String, Long> getMorningTimes() {
        return morningTimes;
    }

    public void setMorningTimes(Map<String, Long> morningTimes) {
        this.morningTimes = morningTimes;
    }

    public Map<String, Long> getAfternoonTimes() {
        return afternoonTimes;
    }

    public void setAfternoonTimes(Map<String, Long> afternoonTimes) {
        this.afternoonTimes = afternoonTimes;
    }

    public Map<String, Long> getEveningTimes() {
        return eveningTimes;
    }

    public void setEveningTimes(Map<String, Long> eveningTimes) {
        this.eveningTimes = eveningTimes;
    }
}
