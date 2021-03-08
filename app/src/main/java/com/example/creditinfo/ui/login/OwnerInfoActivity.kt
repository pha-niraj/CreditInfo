package com.example.creditinfo.ui.login

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.creditinfo.R
import kotlinx.android.synthetic.main.activity_owner_info.*
import kotlinx.android.synthetic.main.activity_owner_info.view.*
import kotlinx.android.synthetic.main.activity_registration.*


class OwnerInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_info)

       // actionBar.setDisplayHomeAsUpEnabled(true)

        owner_name.text = MainScreenActivity.ownerInfo.name
        owner_balance.text = MainScreenActivity.ownerInfo.mainBalance
        owner_mobile.text = MainScreenActivity.ownerInfo.number
        owner_store.text = MainScreenActivity.ownerInfo.storeName
        owner_location.text = MainScreenActivity.ownerInfo.storeLocation


    }
}