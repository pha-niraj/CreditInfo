package com.example.creditinfo.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.creditinfo.R
import kotlinx.android.synthetic.main.activity_single_customer.*

class SingleCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_customer)

        val position = intent.getIntExtra("position",0)
        val adapter = SingleCustomerRecyclerviewAdapter(MainScreenActivity.dataSet[position].transactions,this)
        single_customer_recyclerview.adapter = adapter
        single_customer_recyclerview.layoutManager = LinearLayoutManager(this)

        customer_textview.text = MainScreenActivity.dataSet[position].cutomerName
        balance_textview.text = MainScreenActivity.dataSet[position].customerBalance


        add_transaction_button.setOnClickListener {
            val intent = Intent(this,AddCustomerTransactionActivity::class.java)
            intent.putExtra("name",MainScreenActivity.dataSet[position].cutomerName)
            intent.putExtra("position",position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        single_customer_recyclerview.adapter?.notifyDataSetChanged()
    }
}