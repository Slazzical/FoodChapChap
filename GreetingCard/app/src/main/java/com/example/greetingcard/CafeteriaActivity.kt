package com.example.greetingcard

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class CafeteriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cafeteria)  // Make sure you have this layout file

        // Set up the click listener for the back arrow
        val backArrow: ImageView = findViewById(R.id.back_arrow)
        backArrow.setOnClickListener {
            // Return to MainActivity when the back arrow is clicked
            onBackPressed()
        }
    }
}

