package com.dog.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.sql.Database
import com.badlogic.gdx.sql.DatabaseCursor
import com.badlogic.gdx.sql.DatabaseFactory
import com.badlogic.gdx.sql.SQLiteGdxException

class DatabaseInitialization {
    private var dbHandler: Database? = null
    var actualRecord: Float? = null
    private fun openDatabase() {
        Gdx.app.log("DatabaseInitialization", "creation started")
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null)
        dbHandler!!.setupDatabase()
        try {
            dbHandler!!.openOrCreateDatabase()
            dbHandler!!.execSQL(DATABASE_CREATE)
        } catch (e: SQLiteGdxException) {
            e.printStackTrace()
        }
        Gdx.app.log("DatabaseInitialization", "created successfully")
    }

    private fun closeDatabase() {
        try {
            dbHandler!!.closeDatabase()
        } catch (e: SQLiteGdxException) {
            e.printStackTrace()
        }
        dbHandler = null
        Gdx.app.log("DatabaseInitialization", "dispose")
    }

    private fun insertFirstRecord() {
        try {
            dbHandler
                    ?.execSQL("INSERT INTO record (record_time) values (200.000)")
        } catch (e: SQLiteGdxException) {
            e.printStackTrace()
        }
    }

    fun insertRecord(time: Float) {
        openDatabase()
        try {
            dbHandler
                    ?.execSQL("UPDATE record SET record_time='$time' WHERE id = 1")
        } catch (e: SQLiteGdxException) {
            e.printStackTrace()
        }
        closeDatabase()
    }

    companion object {
        private const val TABLE_RECORD = "record"
        private const val COLUMN_ID = "id"
        private const val COLUMN_RECORD_TIME = "record_time"
        private const val DATABASE_NAME = "record.db"
        private const val DATABASE_VERSION = 1
        // Database creation sql statement
        private const val DATABASE_CREATE = ("create table if not exists "
                + TABLE_RECORD + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_RECORD_TIME
                + " real not null);")
    }

    init {
        openDatabase()
        var cursor: DatabaseCursor? = null
        try {
            cursor = dbHandler!!.rawQuery("SELECT * FROM record")
        } catch (e: SQLiteGdxException) {
            e.printStackTrace()
        }
        if (!cursor!!.next()) { //If table has no record
            insertFirstRecord()
        }
        try {
            cursor = dbHandler!!.rawQuery("SELECT * FROM record")
        } catch (e: SQLiteGdxException) {
            e.printStackTrace()
        }
        while (cursor!!.next()) {
            Gdx.app.log("Actual Record", cursor.getFloat(1).toString())
            actualRecord = cursor.getFloat(1)
        }
        closeDatabase()
    }
}