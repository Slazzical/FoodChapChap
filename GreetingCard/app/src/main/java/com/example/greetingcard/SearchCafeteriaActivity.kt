package com.example.greetingcard

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchCafeteriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_cafeteria)  // Make sure you have this layout file

        // Set up the click listener for the back arrow
        val backArrow: ImageView = findViewById(R.id.search_icon)
        backArrow.setOnClickListener {
            // Return to MainActivity when the back arrow is clicked
            onBackPressed()
        }
    }
}