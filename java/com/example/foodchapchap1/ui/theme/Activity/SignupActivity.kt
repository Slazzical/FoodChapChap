package com.example.foodchapchap1.ui.theme.Activity

import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.foodchapchap1.R
import com.example.foodchapchap1.ui.theme.Database.DatabaseHelper

class SignupActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmpasswordInput: EditText
    private lateinit var signupBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbHelper = DatabaseHelper(this)

        nameInput = findViewById(R.id.nameEt)
        emailInput = findViewById(R.id.userEdt)
        passwordInput = findViewById(R.id.passEdt)
        confirmpasswordInput = findViewById(R.id.passwdEdt)
        signupBtn = findViewById(R.id.signupBtn)

        signupBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmpassword = confirmpasswordInput.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()) {
                if (password != confirmpassword) {
                    Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                } else if (dbHelper.isEmailTaken(email)) {
                    Toast.makeText(this, "Email already exists. Please choose another.", Toast.LENGTH_SHORT).show()
                } else {
                    val result = dbHelper.insertUser(name, email, password)
                    if (result != -1L) {
                        Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Signup Failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
