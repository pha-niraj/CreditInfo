package com.example.creditinfo.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        register_button.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        if (register_customer_name.text.toString().isNotEmpty()
            &&register_mobile_number.text.toString().length==10
            &&register_store_location.text.toString().isNotEmpty()
            &&register_store_name.text.toString().isNotEmpty()){
                if(register_password.text.toString() == register_renter_password.text.toString()){
                    val firebaseReference = Firebase.database.getReference(register_mobile_number.text.toString())

                    firebaseReference.child("CustomerName").setValue(register_customer_name.text.toString())
                    firebaseReference.child("StoreName").setValue(register_store_name.text.toString())
                    firebaseReference.child("StoreLocation").setValue(register_store_location.text.toString())
                    firebaseReference.child("Password").setValue((register_password.text.toString())).addOnSuccessListener {
                        Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                        .addOnFailureListener {
                            Toast.makeText(this,"Error!!Registration Unsuccessful",Toast.LENGTH_SHORT).show()
                            finish()
                        }
                }
        }
        else{
            Toast.makeText(this,"Please fill all the fileds",Toast.LENGTH_SHORT).show()
        }
    }
}