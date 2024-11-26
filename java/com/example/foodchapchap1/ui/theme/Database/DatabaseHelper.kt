package com.example.foodchapchap1.ui.theme.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MobileApplicationProject.db"
        private const val DATABASE_VERSION = 1

        //        User's Table
        const val TABLE_USERS = "Users"
        const val COLUMN_USER_ID = "userID"
        const val COLUMN_USERNAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"

        // Cafeteria_Location Table
        const val TABLE_CAFETERIA_LOCATION = "Cafeteria_Location"
        const val COLUMN_LOCATION_ID = "locationID"
        const val COLUMN_LOCATION_NAME = "location_name"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_OPENING_HOURS = "opening_hours"
        const val COLUMN_CLOSING_HOURS = "closing_hours"

        // Vendors Table
        const val TABLE_VENDORS = "Vendors"
        const val COLUMN_VENDOR_ID = "vendorID"
        const val COLUMN_VENDOR_DESCRIPTION = "descriptions"
        const val COLUMN_LOCATION_FK = "locationID"
        const val COLUMN_PHONE_NUMBER = "phone_number"

        // Menu_Items Table
        const val TABLE_MENU_ITEMS = "Menu_Items"
        const val COLUMN_ITEM_ID = "itemID"
        const val COLUMN_VENDOR_FK = "vendorID"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_ITEM_DESCRIPTION = "descriptions"
        const val COLUMN_PRICE = "price"
        const val COLUMN_AVAILABILITY = "availability"

        // Orders Table
        const val TABLE_ORDERS = "Orders"
        const val COLUMN_ORDER_ID = "orderID"
        const val COLUMN_USER_FK = "userID"
        const val COLUMN_VENDOR_ORDER_FK = "vendorID"
        const val COLUMN_ORDER_STATUS = "order_status"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_TOTAL_PRICE = "total_price"
        const val COLUMN_ORDER_DATE = "order_date"
        const val COLUMN_PICKUP_TIME = "pickup_time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
//            Create Users Table
            val createUsersTable = """
                CREATE TABLE $TABLE_USERS (
                    $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_USERNAME TEXT NOT NULL,
                    $COLUMN_EMAIL TEXT UNIQUE,
                    $COLUMN_PASSWORD TEXT
                )
            """
            db?.execSQL(createUsersTable)

            // Create Cafeteria_Location Table
            val createCafeteriaLocationTable = """
                CREATE TABLE $TABLE_CAFETERIA_LOCATION (
                    $COLUMN_LOCATION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_LOCATION_NAME TEXT NOT NULL,
                    $COLUMN_ADDRESS TEXT UNIQUE,
                    $COLUMN_OPENING_HOURS TEXT,
                    $COLUMN_CLOSING_HOURS TEXT
                )
            """
            db?.execSQL(createCafeteriaLocationTable)

            // Create Vendors Table
            val createVendorsTable = """
                CREATE TABLE $TABLE_VENDORS (
                    $COLUMN_VENDOR_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_USERNAME TEXT UNIQUE,
                    $COLUMN_VENDOR_DESCRIPTION TEXT,
                    $COLUMN_LOCATION_FK INTEGER,
                    $COLUMN_PHONE_NUMBER TEXT,
                    FOREIGN KEY($COLUMN_LOCATION_FK) REFERENCES $TABLE_CAFETERIA_LOCATION($COLUMN_LOCATION_ID) ON DELETE SET NULL
                )
            """
            db?.execSQL(createVendorsTable)

            // Create Menu_Items Table
            val createMenuItemsTable = """
                CREATE TABLE $TABLE_MENU_ITEMS (
                    $COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_VENDOR_FK INTEGER,
                    $COLUMN_ITEM_NAME TEXT,
                    $COLUMN_ITEM_DESCRIPTION TEXT,
                    $COLUMN_PRICE REAL,
                    $COLUMN_AVAILABILITY INTEGER DEFAULT 0,
                    FOREIGN KEY($COLUMN_VENDOR_FK) REFERENCES $TABLE_VENDORS($COLUMN_VENDOR_ID) ON DELETE CASCADE
                )
            """
            db?.execSQL(createMenuItemsTable)

            // Create Orders Table
            val createOrdersTable = """
                CREATE TABLE $TABLE_ORDERS (
                    $COLUMN_ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_USER_FK INTEGER,
                    $COLUMN_VENDOR_ORDER_FK INTEGER,
                    $COLUMN_ORDER_STATUS TEXT NOT NULL DEFAULT 'pending',
                    $COLUMN_QUANTITY INTEGER,
                    $COLUMN_TOTAL_PRICE REAL,
                    $COLUMN_ORDER_DATE TEXT,
                    $COLUMN_PICKUP_TIME TEXT,
                    FOREIGN KEY($COLUMN_USER_FK) REFERENCES $TABLE_USERS($COLUMN_USER_ID) ON DELETE CASCADE,
                    FOREIGN KEY($COLUMN_VENDOR_ORDER_FK) REFERENCES $TABLE_VENDORS($COLUMN_VENDOR_ID) ON DELETE SET NULL
                )
            """
            db?.execSQL(createOrdersTable)

        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error creating database: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_MENU_ITEMS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_VENDORS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_CAFETERIA_LOCATION")
            onCreate(db)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error upgrading database: ${e.message}")
        }
    }

//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDERS")
//        onCreate(db)
//    }

    // Insert User
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

    // Check if email is taken
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

    // Validate User Credentials
    fun validateUser(username: String, password: String): Boolean {

        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE email = ? AND password = ?",
            arrayOf(username, password)
        )
        val exists =
            cursor.moveToFirst() // If the cursor can move to the first row, the user exists
        cursor.close()
        return exists
    }


    // Insert Order
    fun insertOrder(
        userId: Int,
        vendorId: Int?,
        quantity: Int,
        totalPrice: Double,
        orderDate: String,
        pickupTime: String
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_FK, userId)
            put(COLUMN_VENDOR_ORDER_FK, vendorId)
            put(COLUMN_ORDER_STATUS, "pending")
            put(COLUMN_QUANTITY, quantity)
            put(COLUMN_TOTAL_PRICE, totalPrice)
            put(COLUMN_ORDER_DATE, orderDate)
            put(COLUMN_PICKUP_TIME, pickupTime)
        }
        return db.insert(TABLE_ORDERS, null, values)
    }

    // Update Order Status
    fun updateOrderStatus(orderId: Int, newStatus: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ORDER_STATUS, newStatus)
        }
        return db.update(TABLE_ORDERS, values, "$COLUMN_ORDER_ID=?", arrayOf(orderId.toString()))
    }

    // Get All Orders
    fun getAllOrders(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_ORDERS", null)
    }

    // Get Orders by User ID
    fun getOrdersByUserId(userId: Int): Cursor {
        val db = readableDatabase
        return db.rawQuery(
            "SELECT * FROM $TABLE_ORDERS WHERE $COLUMN_USER_FK=?",
            arrayOf(userId.toString())
        )
    }
}
