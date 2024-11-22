package com.example.foodchapchap.ui.theme.Activity

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodchapchap.R

class SignupActivity : AppCompatActivity() {

    lateinit var nameInput : EditText
    lateinit var emailInput : EditText
    lateinit var passwordInput : EditText
    lateinit var confirmpasswordInput : EditText
    lateinit var loginBtn : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        nameInput = findViewById(R.id.nameEt)
        emailInput  = findViewById(R.id.userEdt)
        passwordInput  = findViewById(R.id.passEdt)
        confirmpasswordInput  = findViewById(R.id.passwdEdt)
        loginBtn  = findViewById(R.id.signupBtn)

        loginBtn.setOnClickListener{
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmpassword = confirmpasswordInput.text.toString()
            Log.i("Test credentials", "Username : $name , email : $email and Password : $password")
        }
    }
}