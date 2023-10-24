package com.example.studyspace.database;

public class StudyTime {
    private int timeID;
    private int userID;
    private int roomID;
    private String startTime;
    private String endTime;

    public StudyTime(int timeID, int userID, int roomID, String startTime, String endTime) {
        this.timeID = timeID;
        this.userID = userID;
        this.roomID = roomID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
