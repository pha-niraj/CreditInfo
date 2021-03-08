package com.example.creditinfo.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.creditinfo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreenActivity : AppCompatActivity()

{
    companion object{
         val database = Firebase.database
         lateinit var  databaseReference : DatabaseReference
         var dataSet : ArrayList<CustomerInfo> = ArrayList()
         var ownerInfo = OwnerInfo("","","","","")
         var mainBalance  = "0"
    }


    data class CustomerTranscation(val amount : String ,val transactionType : String, val transactionDate : String,val date :String)

    data class CustomerInfo(val cutomerName : String,val customerNumber : String, var customerBalance :String,var transactions : ArrayList<CustomerTranscation>)

    data class OwnerInfo(var name : String,var number :String,var mainBalance : String, var storeName: String,var storeLocation : String)

    private lateinit var mobileNumber : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        total_balance_textview.text = mainBalance

        mobileNumber = getSharedPreferences("Login_info",MODE_PRIVATE).getString("MobileNumber","")!!

        ownerInfo.number = mobileNumber

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
                    val keyname= dataSnapshot.key

                    if (keyname=="Password"){
                        continue
                    }

                    if (keyname=="MainBalance"){
                        mainBalance = dataSnapshot.value.toString()
                        ownerInfo.mainBalance = mainBalance
                        continue
                    }

                    else if (keyname=="CustomerName"){
                        ownerInfo.name = dataSnapshot.value.toString()
                        continue
                    }
                    else if (keyname=="StoreLocation"){
                        ownerInfo.storeLocation = dataSnapshot.value.toString()
                        continue
                    }
                    else if (keyname=="StoreName"){
                        ownerInfo.storeName = dataSnapshot.value.toString()
                        continue
                    }
                    else {
                        val customerBalance = dataSnapshot.child("Balance").value.toString()
                        val customerNumber = dataSnapshot.child("Number").value.toString()
                        val transactionList = ArrayList<CustomerTranscation>()

                        val transactionsDataSanps = dataSnapshot.child("transactions").children

                        for (transaction in transactionsDataSanps) {

                            val customerTransaction = CustomerTranscation(
                                transaction.child("1").value.toString(),
                                transaction.child("2").value.toString(),
                                transaction.child("0").value.toString(),
                                transaction.child("3").value.toString()
                            )

                            transactionList.add(customerTransaction)

                        }

                        val customerInfo = CustomerInfo(
                            keyname!!,
                            customerNumber,
                            customerBalance,
                            transactionList
                        )

                        dataSet.add(customerInfo)
                    }

                }

                owner_info.text = ownerInfo.name+"\n"+ ownerInfo.storeName
                total_balance_textview.text = ownerInfo.mainBalance
                adapter.notifyDataSetChanged()

            }
        })


        owner_info_layout.setOnClickListener {
            val intent = Intent(this,OwnerInfoActivity::class.java)
            startActivity(intent)
        }

    }
}