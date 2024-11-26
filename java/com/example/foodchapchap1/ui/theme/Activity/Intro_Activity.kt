package com.example.foodapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import com.example.foodchapchap.ui.theme.Activity.LoginActivity
import com.example.mobileapp.R

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)


        // Set up listeners for buttons
        val signUpBtn = findViewById<TextView>(R.id.signupBtn)
        val signInBtn = findViewById<TextView>(R.id.signinBtn)

        signUpBtn.setOnClickListener {
            // Navigate to Sign Up Activity
            val SignupActivity = Unit
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            // Navigate to Sign In Activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
