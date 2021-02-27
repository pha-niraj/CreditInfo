package com.example.creditinfo.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.creditinfo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : AppCompatActivity()

{
    companion object{
         val database = Firebase.database
         lateinit var  databaseReference : DatabaseReference
         var dataSet : ArrayList<CustomerInfo> = ArrayList()
         var mainBalance  = "0"
    }


    data class CustomerTranscation(val amount : String ,val transactionType : String, val transactionDate : String,val date :String)

    data class CustomerInfo(val cutomerName : String,val customerNumber : String, var customerBalance :String,var transactions : ArrayList<CustomerTranscation>)


    private lateinit var mobileNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        mobileNumber = getSharedPreferences("Login_info",MODE_PRIVATE).getString("MobileNumber","")!!

        val adapter = MainRecyclerviewAdapter(dataSet,this)

        customer_recyclerview.adapter = adapter
        customer_recyclerview.layoutManager = LinearLayoutManager(this)

        databaseReference = database.getReference(mobileNumber)

        add_customer_view.setOnClickListener {
            val intent =  Intent(this,AddCustomerActivity::class.java)
            startActivity(intent)
        }

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
               val snapshots =  snapshot.children
                dataSet.clear()
                for (dataSnapshot in snapshots ){
                    val customerName= dataSnapshot.key
                    if (customerName=="MainBalance"){
                        mainBalance = dataSnapshot.child("MainBalance").value.toString()
                        total_balance_textview.text = mainBalance
                        continue
                    }

                    if (customerName!="CustomerName"&&customerName!="Password"&&customerName!="StoreLocation"&&customerName!="StoreName") {
                        val customerBalance = dataSnapshot.child("Balance").value.toString()
                        val customerNumber = dataSnapshot.child("Number").value.toString()
                        val transactionList = ArrayList<CustomerTranscation>()

                        val transactionsDataSanps = dataSnapshot.child("transactions").children

                        for (transaction in transactionsDataSanps) {

                            val customerTransaction = CustomerTranscation(
                                transaction.child("1").value.toString(),
                                transaction.child("2").value.toString(),
                                transaction.child("0").value.toString(),
                                transaction.child("1").value.toString()
                            )

                            transactionList.add(customerTransaction)

                        }

                        val customerInfo = CustomerInfo(
                            customerName!!,
                            customerNumber,
                            customerBalance,
                            transactionList
                        )

                        dataSet.add(customerInfo)
                    }

                }
                adapter.notifyDataSetChanged()

            }
        })

    }
}