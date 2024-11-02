package com.example.lab_6_2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "ContactDatabase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT,
            $COLUMN_PHONE TEXT,
            $COLUMN_EMAIL TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val phone = getString(getColumnIndexOrThrow(COLUMN_PHONE))
                val email = getString(getColumnIndexOrThrow(COLUMN_EMAIL))
                contacts.add(Contact(id, name, phone, email))
            }
        }

        cursor.close()
        db.close()
        return contacts
    }

    fun addContact(contact: Contact) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getContactById(id: Int): Contact? {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null)

        return if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            cursor.close()
            db.close()
            Contact(id, name, phone, email)
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun deleteContact(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun updateContact(contact: Contact) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(contact.id.toString()))
        db.close()
    }

}
