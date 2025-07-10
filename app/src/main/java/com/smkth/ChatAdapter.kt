package com.smkth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smkth.ChatMessage
import com.smktnh.final_project_aplikasi_penghitung_kalori.R

class ChatAdapter(private val messages: MutableList<ChatMessage>) :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

        companion object {
            private const val TYPE_USER = 0
            private const val TYPE_BOT = 1
        }

        override fun getItemViewType(position: Int) =
            if (messages[position].isUser) TYPE_USER else TYPE_BOT

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            val layout = if (viewType == TYPE_USER)
                R.layout.item_chat_user else R.layout.item_chat_bot
            val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return ChatViewHolder(view)
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) =
            holder.bind(messages[position])

        override fun getItemCount() = messages.size

        fun add(msg: ChatMessage) {
            messages.add(msg)
            notifyItemInserted(messages.lastIndex)
        }

        class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
            fun bind(msg: ChatMessage) { tvMessage.text = msg.message }
        }
    }

