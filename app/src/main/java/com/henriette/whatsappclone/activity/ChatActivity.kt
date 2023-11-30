package com.henriette.whatsappclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.henriette.whatsappclone.R
import com.henriette.whatsappclone.adapter.MessageAdapter
import com.henriette.whatsappclone.databinding.ActivityChatBinding
import com.henriette.whatsappclone.databinding.SentItemLayoutBinding
import com.henriette.whatsappclone.model.MessageModel
import java.util.Date

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase

    private lateinit var senderUid:String
    private lateinit var receiverUid:String

    private lateinit var senderRoom:String
    private lateinit var receiverRoom:String

    private lateinit var list: ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        senderUid =FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        list = ArrayList()

        senderRoom = senderUid + receiverUid
        receiverRoom = senderUid + receiverUid

        database= FirebaseDatabase.getInstance()

        binding.imageView.setOnClickListener {
            if (binding.etMessageBox.text.isEmpty()){
                Toast.makeText(this, "Please Enter Your Message", Toast.LENGTH_SHORT).show()
            }else{
                val message = MessageModel(binding.etMessageBox.text.toString(), senderUid, Date().time)

                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom).child(randomKey!!).setValue(message).addOnSuccessListener {

                        database.reference.child("chats")
                            .child(receiverRoom).child("message").child(randomKey!!).setValue(message).addOnSuccessListener {
                                binding.etMessageBox.text = null
                                Toast.makeText(this, "Message Sent!!!", Toast.LENGTH_SHORT).show()
                            }
                    }
            }
        }

        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    list.clear()

                    for (snapshort1 in snapshot.children){
                        val data = snapshort1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }

                    binding.rvRecyclerView.adapter = MessageAdapter(this@ChatActivity, list)

                }
                override fun onCancelled(error:DatabaseError){
                    Toast.makeText(this@ChatActivity, "Error : $error", Toast.LENGTH_SHORT).show()

                }
            })

    }
}