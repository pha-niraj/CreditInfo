package com.example.creditinfo.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_new_login_screen.*

class NewLoginActivity : AppCompatActivity() {

    private val firbasereference = Firebase.database.reference
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_login_screen)

        sharedPreferences = getSharedPreferences("Login_info",MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLoginSuccessful",false)){
            this.startActivity(Intent(this,MainScreenActivity::class.java))
        }

        login_register.setOnClickListener {
            val intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            if ((login_mobile_number.text.toString().length==10)&&login_password.text.toString().isNotEmpty()) {
                firbasereference.orderByKey().equalTo(login_mobile_number.text.toString())
                    .addValueEventListener(object :
                        ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {

                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val password = snapshot.child(login_mobile_number.text.toString()).child("Password").value.toString()
                                if (password==login_password.text.toString()){
                                    sharedPreferences.edit().putString("MobileNumber",login_mobile_number.text.toString()).apply()
                                    sharedPreferences.edit().putBoolean("isLoginSuccessful",true).apply()
                                    val intent = Intent(this@NewLoginActivity,MainScreenActivity::class.java)
                                    startActivity(intent)
                                }

                            }
                            else{
                                Toast.makeText(this@NewLoginActivity,"Unregistered Number.Please Register!!",Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            }

        }
    }
}