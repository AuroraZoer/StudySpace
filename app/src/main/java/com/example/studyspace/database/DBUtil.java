package com.example.studyspace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtil extends SQLiteOpenHelper {
    private static final String databaseName = "StudySpace.db";
    private static final int databaseVersion = 1;

    // User table
    private static final String TABLE_USER = "User";
    private static final String USER_COLUMN_ID = "UserID";
    private static final String USER_COLUMN_USERNAME = "Username";
    private static final String USER_COLUMN_EMAIL = "Email";
    private static final String USER_COLUMN_PASSWORD = "Password";

    // StudyRoom table
    private static final String TABLE_STUDY_ROOM = "StudyRoom";
    private static final String ROOM_COLUMN_ID = "RoomID";
    private static final String ROOM_COLUMN_NAME = "Room";
    private static final String ROOM_COLUMN_BUILDING = "Building";
    private static final String ROOM_COLUMN_MORNING_AVAILABILITY = "MorningAvailability";
    private static final String ROOM_COLUMN_AFTERNOON_AVAILABILITY = "AfternoonAvailability";
    private static final String ROOM_COLUMN_EVENING_AVAILABILITY = "EveningAvailability";

    // StudyTime table
    private static final String TABLE_STUDY_TIME = "StudyTime";
    private static final String TIME_COLUMN_ID = "TimeID";
    private static final String TIME_COLUMN_USER_ID = "UserID";
    private static final String TIME_COLUMN_ROOM_ID = "RoomID";
    private static final String TIME_COLUMN_START_TIME = "StartTime";
    private static final String TIME_COLUMN_END_TIME = "EndTime";

    public DBUtil(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the User table
        String createUserTable = "CREATE TABLE " + TABLE_USER + " (" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COLUMN_USERNAME + " TEXT NOT NULL, " +
                USER_COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                USER_COLUMN_PASSWORD + " TEXT NOT NULL);";
        db.execSQL(createUserTable);

        // Create the StudyRoom table
        String createStudyRoomTable = "CREATE TABLE " + TABLE_STUDY_ROOM + " (" +
                ROOM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROOM_COLUMN_NAME + " INTEGER NOT NULL, " +
                ROOM_COLUMN_BUILDING + " INTEGER NOT NULL, " +
                ROOM_COLUMN_MORNING_AVAILABILITY + " INTEGER, " +
                ROOM_COLUMN_AFTERNOON_AVAILABILITY + " INTEGER, " +
                ROOM_COLUMN_EVENING_AVAILABILITY + " INTEGER);";
        db.execSQL(createStudyRoomTable);

        // Create the StudyTime table
        String createStudyTimeTable = "CREATE TABLE " + TABLE_STUDY_TIME + " (" +
                TIME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TIME_COLUMN_USER_ID + " INTEGER, " +
                TIME_COLUMN_ROOM_ID + " INTEGER, " +
                TIME_COLUMN_START_TIME + " TEXT, " +
                TIME_COLUMN_END_TIME + " TEXT, " +
                "FOREIGN KEY(" + TIME_COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + USER_COLUMN_ID + "), " +
                "FOREIGN KEY(" + TIME_COLUMN_ROOM_ID + ") REFERENCES " + TABLE_STUDY_ROOM + "(" + ROOM_COLUMN_ID + "));";
        db.execSQL(createStudyTimeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(("DROP TABLE IF EXISTS " + TABLE_USER));

    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_USERNAME, user.getUsername());
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_PASSWORD, user.getPassword());
        long result = db.insert(TABLE_USER, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean checkEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + USER_COLUMN_ID + " FROM " + TABLE_USER + " WHERE " + USER_COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean emailExists = cursor.getCount() > 0;
        cursor.close();
        return emailExists;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + USER_COLUMN_EMAIL + " = ? AND " + USER_COLUMN_PASSWORD + " = ?", new String[]{email, password});
        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        return loginSuccessful;
    }

}
