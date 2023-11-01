package com.example.studyspace.Database;

public class StudyRoom {
    private int roomID;
    private String room;
    private String building;
    private int morningAvailability;
    private int afternoonAvailability;
    private int eveningAvailability;

    public StudyRoom(int roomID, String room, String building, int morningAvailability, int afternoonAvailability, int eveningAvailability) {
        this.roomID = roomID;
        this.room = room;
        this.building = building;
        this.morningAvailability = morningAvailability;
        this.afternoonAvailability = afternoonAvailability;
        this.eveningAvailability = eveningAvailability;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getMorningAvailability() {
        return morningAvailability;
    }

    public void setMorningAvailability(int morningAvailability) {
        this.morningAvailability = morningAvailability;
    }

    public int getAfternoonAvailability() {
        return afternoonAvailability;
    }

    public void setAfternoonAvailability(int afternoonAvailability) {
        this.afternoonAvailability = afternoonAvailability;
    }

    public int getEveningAvailability() {
        return eveningAvailability;
    }

    public void setEveningAvailability(int eveningAvailability) {
        this.eveningAvailability = eveningAvailability;
    }


}
