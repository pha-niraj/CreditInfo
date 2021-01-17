package com.example.creditinfo.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        add_customer_view.setOnClickListener {
            val intent =  Intent(this,AddCustomerActivity::class.java)
            startActivity(intent)
        }

    }
}