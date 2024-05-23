package com.example.healthok.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthok.data.model.Message
import com.vcreate.healthok.R

class MessageAdapter(
    var messageList :List<Message>
):RecyclerView.Adapter<MessageAdapter.MsgViewHolder>() {
    inner class MsgViewHolder(var v: View) : RecyclerView.ViewHolder(v) {
        val leftChatView = v.findViewById<LinearLayout>(R.id.left_chat_view)
        val leftTextView = v.findViewById<TextView>(R.id.left_chat_text_view)
        val rightChatView = v.findViewById<LinearLayout>(R.id.right_chat_view)
        val rightTextView = v.findViewById<TextView>(R.id.right_chat_text_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val chatView = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return MsgViewHolder(chatView)
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sendBy == Message.SEND_BY_ME) {
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.message
        } else {
            holder.leftChatView.visibility = View.VISIBLE
            holder.rightChatView.visibility = View.GONE
            holder.leftTextView.text = message.message
        }

    }
}