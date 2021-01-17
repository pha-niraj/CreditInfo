package com.example.creditinfo.ui.login

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_customer.*

class AddCustomerActivity : AppCompatActivity() {


    val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)

        customer_save_button.setOnClickListener {
            if (customer_name.text.toString().isNotEmpty())  {
                if (customer_number.text.toString().length==10){
                    closeKeyBoard()
                    uploadCustomerDetailstoDatabase(customer_name.text.toString(),customer_number.text.toString())
                }
                else{
                    Toast.makeText(this,"Enter Valid Number",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Name cannot be empty",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadCustomerDetailstoDatabase(name: String, number: String) {
         val uploadTask =  database.getReference(number).setValue(name)
        uploadTask.addOnSuccessListener {
            customer_number.clearFocus()
            customer_name.clearFocus()
            Toast.makeText(this,"Saved Successful",Toast.LENGTH_LONG).show()
        }
        uploadTask.addOnFailureListener {
            customer_number.clearFocus()
            customer_name.clearFocus()
            Toast.makeText(this,"Error while saving! Try again",Toast.LENGTH_LONG).show()
        }
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}