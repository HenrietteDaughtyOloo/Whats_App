package com.henriette.whatsappclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.henriette.whatsappclone.MainActivity
import com.henriette.whatsappclone.R
import com.henriette.whatsappclone.databinding.ActivityNumberBinding

class NumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNumberBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnContinueButton.setOnClickListener{
            if (binding.tilPhoneNumber.text!!.isEmpty()){
                Toast.makeText(this, " Hi there! Please Enter Your Number!!", Toast.LENGTH_SHORT).show()
            }else{
                var intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("number", binding.tilPhoneNumber.text!!.toString())
                startActivity(intent)
            }
        }
    }
}