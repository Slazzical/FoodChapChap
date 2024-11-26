package com.example.greetingcard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.greetingcard.ui.theme.GreetingCardTheme
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the click listener for the search icon
        val searchIcon: ImageView = findViewById(R.id.search_cafeteria)
        searchIcon.setOnClickListener {
            try {
                // Log to confirm the click event
                Log.d("MainActivity", "Search icon clicked")

                val intent = Intent(this, SearchCafeteriaActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                // Log any exceptions to catch potential errors
                Log.e("MainActivity", "Error launching SearchCafeteriaActivity", e)
            }
        }

        val buttonIds = listOf(R.id.view_cafeteria, R.id.view_cafeteria2, R.id.view_cafeteria3, R.id.view_cafeteria4)

        buttonIds.forEach { buttonId ->
            val myButton = findViewById<Button>(buttonId)
            myButton.setOnClickListener {
                try {
                    // Log to confirm the click event with specific button ID
                    Log.d("MainActivity", "Button with ID: $buttonId clicked")

                    val intent = Intent(this, CafeteriaActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    // Log any exceptions with context of button ID
                    Log.e("MainActivity", "Error launching CafeteriaActivity for Button $buttonId", e)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GreetingCardTheme {
        MainActivity()
    }
}
