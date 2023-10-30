package com.example.studyspace.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
                ROOM_COLUMN_NAME + " TEXT NOT NULL, " +
                ROOM_COLUMN_BUILDING + " TEXT NOT NULL, " +
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
        db.execSQL(("DROP TABLE IF EXISTS " + TABLE_STUDY_ROOM));
        db.execSQL(("DROP TABLE IF EXISTS " + TABLE_STUDY_TIME));
        onCreate(db);
    }

    // User table methods

    /**
     * Add a user to the database
     *
     * @param user The user to add
     * @return True if the user was added successfully, false otherwise
     */
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

    /**
     * Check if the email already exists in the database
     *
     * @param email The email to check
     * @return True if the email exists, false otherwise
     */
    public boolean checkEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + USER_COLUMN_ID + " FROM " + TABLE_USER + " WHERE " + USER_COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean emailExists = cursor.getCount() > 0;
        cursor.close();
        return emailExists;
    }

    /**
     * Check if the email and password match
     *
     * @param email    The email to check
     * @param password The password to check
     * @return
     */
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + USER_COLUMN_EMAIL + " = ? AND " + USER_COLUMN_PASSWORD + " = ?", new String[]{email, password});
        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        return loginSuccessful;
    }

    /**
     * Get the user ID if the login is successful
     *
     * @param email    The email to check
     * @param password The password to check
     * @return The user ID if the login is successful, -1 otherwise
     */
    public int getUserIdIfLoginSuccessful(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_COLUMN_ID};
        String selection = USER_COLUMN_EMAIL + " = ? AND " + USER_COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(USER_COLUMN_ID);

            if (columnIndex >= 0) {
                userId = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return userId;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {USER_COLUMN_ID, USER_COLUMN_USERNAME, USER_COLUMN_EMAIL, USER_COLUMN_PASSWORD};
        String selection = USER_COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(USER_COLUMN_ID);
            int usernameColumnIndex = cursor.getColumnIndex(USER_COLUMN_USERNAME);
            int emailColumnIndex = cursor.getColumnIndex(USER_COLUMN_EMAIL);
            int passwordColumnIndex = cursor.getColumnIndex(USER_COLUMN_PASSWORD);
            if (idColumnIndex >= 0 && usernameColumnIndex >= 0 && emailColumnIndex >= 0 && passwordColumnIndex >= 0) {
                int userId = cursor.getInt(idColumnIndex);
                String username = cursor.getString(usernameColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String password = cursor.getString(passwordColumnIndex);

                user = new User(userId, username, email, password);
            }
        }
        cursor.close();
        return user;
    }


    // StudyRoom table methods

    /**
     * Add all study rooms to the study room table
     *
     * @param studyRooms The list of study rooms to add
     * @return The number of successful inserts
     */
    public int insertStudyRooms(List<StudyRoom> studyRooms) {
        SQLiteDatabase db = this.getWritableDatabase();
        int successfulInserts = 0;

        try {
            db.beginTransaction();

            for (StudyRoom studyRoom : studyRooms) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ROOM_COLUMN_NAME, studyRoom.getRoom());
                contentValues.put(ROOM_COLUMN_BUILDING, studyRoom.getBuilding());
                contentValues.put(ROOM_COLUMN_MORNING_AVAILABILITY, studyRoom.getMorningAvailability());
                contentValues.put(ROOM_COLUMN_AFTERNOON_AVAILABILITY, studyRoom.getAfternoonAvailability());
                contentValues.put(ROOM_COLUMN_EVENING_AVAILABILITY, studyRoom.getEveningAvailability());
                long result = db.insert(TABLE_STUDY_ROOM, null, contentValues);
                if (result != -1) {
                    successfulInserts++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return successfulInserts;
    }

    /**
     * Get all buildings in the study room table
     *
     * @return The list of buildings
     */
    public String[] getAllBuildings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_STUDY_ROOM, new String[]{ROOM_COLUMN_BUILDING}, null, null, null, null, null, null);

        List<String> buildingList = new ArrayList<>();
        int buildingColumnIndex = cursor.getColumnIndex(ROOM_COLUMN_BUILDING);

        if (buildingColumnIndex >= 0 && cursor.moveToFirst()) {
            do {
                String building = cursor.getString(buildingColumnIndex);
                if (building != null && !building.isEmpty()) {
                    buildingList.add(building);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return buildingList.toArray(new String[0]);
    }


    public int getTotalRoomCount(String building, String timeOfDay) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = ROOM_COLUMN_BUILDING + " = ? AND " + timeOfDay + " = 1";
        String[] selectionArgs = {building};

        Cursor cursor = db.query(TABLE_STUDY_ROOM, null, selection, selectionArgs, null, null, null);

        int roomCount = cursor.getCount();
        cursor.close();
        db.close();

        return roomCount;
    }


    public List<String> getAvailableRooms(String building, String timeOfDay) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {ROOM_COLUMN_NAME};
        String selection = ROOM_COLUMN_BUILDING + " = ? AND " + timeOfDay + " = 1";
        String[] selectionArgs = {building};

        Cursor cursor = db.query(TABLE_STUDY_ROOM, projection, selection, selectionArgs, null, null, null);

        List<String> availableRooms = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int roomNameIndex = cursor.getColumnIndex(ROOM_COLUMN_NAME);
            if (roomNameIndex >= 0) {
                do {
                    String roomName = cursor.getString(roomNameIndex);
                    availableRooms.add(roomName);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return availableRooms;
    }



    // StudyTime table methods

    /**
     * Get the user's study time count
     *
     * @param userId The user ID
     * @return The user's study time count
     */
    public int gerUserStudyCount(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"COUNT(*)"};
        String selection = TIME_COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(TABLE_STUDY_TIME, columns, selection, selectionArgs, null, null, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("COUNT(*)");
            if (columnIndex >= 0) {
                count = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return count;
    }


}
