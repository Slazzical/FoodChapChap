package com.example.foodchapchap1.ui.theme.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodchapchap1.R
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.foodchapchap1.ui.theme.Database.DatabaseHelper

class OrderActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId", "WrongViewCast", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


        dbHelper = DatabaseHelper(this)
        // Initialize header views
        val estimatedTimeTextView: TextView = findViewById(R.id.tv_estimated_time)
        val orderNumberTextView: TextView = findViewById(R.id.tv_order_number)

        // Initialize progress step views
        val orderPlacedCheckBox: CheckBox = findViewById(R.id.cb_order_placed)
        val orderConfirmedCheckBox: CheckBox = findViewById(R.id.cb_order_confirmed)
        val orderProcessedCheckBox: CheckBox = findViewById(R.id.cb_order_processed)
        val readyToPickupCheckBox: CheckBox = findViewById(R.id.cb_ready_to_pickup)

        // Update header with sample data
        estimatedTimeTextView.text = "Estimated Time: 30 minutes"
        orderNumberTextView.text = "Order #2482011"

        // Insert a sample order
        val result = dbHelper.insertOrder(
            userId = 1,
            vendorId = 2,
            quantity = 3,
            totalPrice = 15.99,
            orderDate = "2024-11-24",
            pickupTime = "12:30 PM"
        )

        // Add event listeners to each step
        orderPlacedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Order Placed!", Toast.LENGTH_SHORT).show()
            }
        }

        orderConfirmedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Order Confirmed!", Toast.LENGTH_SHORT).show()
            }
        }

        orderProcessedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Order Processed!", Toast.LENGTH_SHORT).show()
            }
        }

        readyToPickupCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Ready for Pickup!", Toast.LENGTH_SHORT).show()
            }
        }

        if (result != -1L) {
            Toast.makeText(this, "Order Inserted Successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to Insert Order!", Toast.LENGTH_SHORT).show()
        }

        // Example: Updating Order Status
        val updatedRows = dbHelper.updateOrderStatus(orderId = 1, newStatus = "Confirmed")
        if (updatedRows > 0) {
            Toast.makeText(this, "Order Status Updated!", Toast.LENGTH_SHORT).show()
        }

        // Example: Fetching All Orders
        val cursorAllOrders = dbHelper.getAllOrders()
        cursorAllOrders.use {
            while (it.moveToNext()) {
                val orderId = it.getInt(it.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID))
                val orderStatus = it.getString(it.getColumnIndex(DatabaseHelper.COLUMN_ORDER_STATUS))
                Toast.makeText(this, "Order ID: $orderId, Status: $orderStatus", Toast.LENGTH_SHORT).show()
            }
        }

        // Example: Fetching Orders for a Specific User
        val cursorUserOrders = dbHelper.getOrdersByUserId(userId = 1)
        cursorUserOrders.use {
            while (it.moveToNext()) {
                val orderId = it.getInt(it.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID))
                val orderStatus = it.getString(it.getColumnIndex(DatabaseHelper.COLUMN_ORDER_STATUS))
                Toast.makeText(this, "User's Order ID: $orderId, Status: $orderStatus", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
