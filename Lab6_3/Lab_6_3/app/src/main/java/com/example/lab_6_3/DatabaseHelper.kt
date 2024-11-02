package com.example.lab_6_3

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NOTES = "notes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NOTES ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_TITLE TEXT,"
                + "$COLUMN_CONTENT TEXT,"
                + "$COLUMN_DATE TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    // Thêm ghi chú
    fun addNote(title: String, content: String, date: String) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_CONTENT, content)
        values.put(COLUMN_DATE, date)

        writableDatabase.insert(TABLE_NOTES, null, values)
    }

    // Cập nhật ghi chú
    fun updateNote(id: Int, title: String, content: String, date: String) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_CONTENT, content)
        values.put(COLUMN_DATE, date)

        writableDatabase.update(TABLE_NOTES, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Xóa ghi chú
    fun deleteNote(id: Int) {
        writableDatabase.delete(TABLE_NOTES, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    // Lấy tất cả ghi chú
    @SuppressLint("Range")
    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NOTES", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
                val date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                notes.add(Note(id, title, content, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notes
    }
}
