package com.example.creditinfo.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import kotlinx.android.synthetic.main.activity_new_login_activty.*

class NewLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_login_activty)

        register_button.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

    }
}