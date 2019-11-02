package com.dog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

public class DatabaseInitialization {
    private Database dbHandler;

    private static final String TABLE_RECORD = "record";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RECORD_TIME = "record_time";

    private static final String DATABASE_NAME = "record.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_RECORD + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_RECORD_TIME
            + " real not null);";

    private Float actualRecord;

    public DatabaseInitialization() {
        openDatabase();

        DatabaseCursor cursor = null;

        try {
            cursor = dbHandler.rawQuery("SELECT * FROM record");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        if (!cursor.next()) { //If table has no record
            insertFirstRecord();
        }

        try {
            cursor = dbHandler.rawQuery("SELECT * FROM record");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        while (cursor.next()) {
            Gdx.app.log("Actual Record", String.valueOf(cursor.getFloat(1)));
            actualRecord = cursor.getFloat(1);
        }

        closeDatabase();
    }

    private void openDatabase() {
        Gdx.app.log("DatabaseInitialization", "creation started");

        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        Gdx.app.log("DatabaseInitialization", "created successfully");
    }

    private void closeDatabase() {
        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        dbHandler = null;
        Gdx.app.log("DatabaseInitialization", "dispose");
    }

    private void insertFirstRecord() {
        try {
            dbHandler
                    .execSQL("INSERT INTO record (record_time) values (200.000)");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void insertRecord(Float time) {
        openDatabase();
        try {
            dbHandler
                    .execSQL("UPDATE record SET record_time='" + time + "' WHERE id = 1");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        closeDatabase();
    }

    public Float getActualRecord() {
        return actualRecord;
    }
}
