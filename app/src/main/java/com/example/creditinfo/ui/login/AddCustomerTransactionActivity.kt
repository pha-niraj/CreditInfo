package com.example.creditinfo.ui.login

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import kotlinx.android.synthetic.main.activity_add_customer_transaction.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddCustomerTransactionActivity : AppCompatActivity() {

    var name = ""
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer_transaction)

        name = intent.getStringExtra("name")!!
        position = intent.getIntExtra("position",0)

        transcation_save_button.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        if (transcation_message.text.toString().isNotEmpty()){
            if (transcation_amount.text.toString().isNotEmpty()){

                val radioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                calculateBalance(transcation_amount.text.toString().toInt(),radioButton.text.toString())

                val list = ArrayList<String>()
                list.add(transcation_message.text.toString())
                list.add(transcation_amount.text.toString())
                list.add(radioButton.text.toString())
                list.add(getCurrentDateTime())

                val transactionUploadtask = MainScreenActivity.databaseReference.child(name).child("transactions").ref.push().setValue(list)

                transactionUploadtask.addOnSuccessListener {
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                    finish()
                }
                transactionUploadtask.addOnFailureListener {
                    Toast.makeText(this,"Error while Saving",Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
            else{
                Toast.makeText(this,"Enter Transaction Amount",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"Enter Transaction Message",Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateBalance(balance: Int, type: String) {
        val currentBalance = MainScreenActivity.dataSet[position].customerBalance.toInt()

        if (type == "You Got"){
            MainScreenActivity.databaseReference.child(name).child("Balance").setValue((currentBalance+balance).toString())
        }else {
            MainScreenActivity.databaseReference.child(name).child("Balance").setValue((currentBalance-balance).toString())
        }

    }

    private fun getCurrentDateTime() : String{
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}