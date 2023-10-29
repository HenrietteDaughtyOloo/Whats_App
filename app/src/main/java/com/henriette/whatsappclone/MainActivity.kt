package com.henriette.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.henriette.whatsappclone.activity.NumberActivity
import com.henriette.whatsappclone.adapter.ViewPagerAdapter
import com.henriette.whatsappclone.databinding.ActivityMainBinding
import com.henriette.whatsappclone.ui.CallFragment
import com.henriette.whatsappclone.ui.ChatFragment
import com.henriette.whatsappclone.ui.StatusFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding?=null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null){
            startActivity(Intent(this, NumberActivity::class.java))
            finish()
        }


        val adapter =ViewPagerAdapter(this, supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter

        binding!!.tabs.setupWithViewPager(binding!!.viewPager)
    }
}