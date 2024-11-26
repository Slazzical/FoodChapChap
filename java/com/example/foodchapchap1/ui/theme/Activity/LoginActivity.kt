package com.example.foodchapchap1.ui.theme.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.foodchapchap1.R
import com.example.foodchapchap1.ui.theme.Database.DatabaseHelper

class LoginActivity : AppCompatActivity() {

    lateinit var emailInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginBtn : AppCompatButton
    lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        dbHelper = DatabaseHelper(this)

        emailInput = findViewById(R.id.userEdt)
        passwordInput = findViewById(R.id.passEdt)
        loginBtn = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener{
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    Log.i("LoginActivity", "Checking credentials for email: $email")

                    // Validate user credentials
                    if (dbHelper.validateUser(email, password)) {
                        // Successful login, navigate to MainActivity
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Invalid credentials, show a toast
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Error during login: ${e.message}")
                    Toast.makeText(this, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show toast if email or password is empty
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

