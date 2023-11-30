package com.henriette.whatsappclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.henriette.whatsappclone.R
import com.henriette.whatsappclone.activity.ChatActivity
import com.henriette.whatsappclone.databinding.ChatUserItemLayooutBinding
import com.henriette.whatsappclone.model.UserModel

class ChatAdapter(var context: Context, var list: ArrayList<UserModel>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){
    inner class  ChatViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding:ChatUserItemLayooutBinding = ChatUserItemLayooutBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return  ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_user_item_layoout, parent,false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val user = list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.ivUserImage)
        holder.binding.tvUserName.text= user.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("uid", user.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}