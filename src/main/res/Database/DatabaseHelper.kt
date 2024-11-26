package com.example.foodchapchap.ui.theme.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MobileApplicationProject.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "Users"
        const val COLUMN_USER_ID = "userID"
        const val COLUMN_USERNAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createUsersTable = """
                CREATE TABLE $TABLE_USERS (
                    $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_USERNAME TEXT NOT NULL,
                    $COLUMN_EMAIL TEXT UNIQUE,
                    $COLUMN_PASSWORD TEXT
                )
            """
            db?.execSQL(createUsersTable)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error creating database: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            onCreate(db)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error upgrading database: ${e.message}")
        }
    }

    fun insertUser(username: String, email: String, password: String): Long {
        if (isEmailTaken(email)) {
            Log.e("DatabaseHelper", "Email already exists: $email")
            return -1L // Return -1 to indicate failure due to duplicate email
        }
        return try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_USERNAME, username)
                put(COLUMN_EMAIL, email)
                put(COLUMN_PASSWORD, password)
            }
            db.insert(TABLE_USERS, null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Insert failed: ${e.message}")
            -1L
        }
    }

    fun isEmailTaken(email: String): Boolean {
        return try {
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
            val cursor = db.rawQuery(query, arrayOf(email))
            val exists = cursor.count > 0
            cursor.close()
            exists
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error checking email: ${e.message}")
            false
        }
    }

    fun validateUser(username: String, password: String): Boolean {
        // Logic to check if the user exists in the database with the given credentials
        // You can write a query to check if the username and password match in your user table
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", arrayOf(username, password))
        val exists = cursor.moveToFirst() // If the cursor can move to the first row, the user exists
        cursor.close()
        return exists
    }
}
