package com.smkth.final_project_aplikasi_penghitung_kalori

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smktnh.final_project_aplikasi_penghitung_kalori.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatbotActivity : AppCompatActivity() {

    private lateinit var inputMessage: EditText
    private lateinit var sendButton: Button
    private lateinit var chatResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot) // pastikan file XML ini ada

        inputMessage = findViewById(R.id.inputMessage)
        sendButton = findViewById(R.id.sendButton)
        chatResponse = findViewById(R.id.chatResponse)

        sendButton.setOnClickListener {
            val message = inputMessage.text.toString()
            if (message.isNotEmpty()) {
                generateAIResponse(message)
            }
        }
    }

    private fun generateAIResponse(userMessage: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mockAIResponse(userMessage)
            withContext(Dispatchers.Main) {
                chatResponse.text = response
            }
        }
    }

    private fun mockAIResponse(message: String): String {
        return when {
            message.contains("nasi", ignoreCase = true) -> "100 gram nasi putih mengandung sekitar 175 kalori."
            message.contains("ayam", ignoreCase = true) -> "100 gram dada ayam tanpa kulit mengandung sekitar 165 kalori."
            message.contains("indomie", ignoreCase = true) -> "1 bungkus Indomie goreng mengandung sekitar 350-400 kalori."
            message.contains("telur", ignoreCase = true) -> "1 butir telur rebus mengandung sekitar 70-80 kalori."
            message.contains("roti", ignoreCase = true) -> "1 lembar roti tawar mengandung sekitar 80 kalori."
            message.contains("pisang", ignoreCase = true) -> "1 buah pisang ukuran sedang mengandung sekitar 105 kalori."
            message.contains("susu", ignoreCase = true) -> "1 gelas susu full cream mengandung sekitar 150 kalori."
            message.contains("apel", ignoreCase = true) -> "1 buah apel ukuran sedang mengandung sekitar 95 kalori."
            message.contains("tempe", ignoreCase = true) -> "100 gram tempe mengandung sekitar 192 kalori."
            message.contains("tahu", ignoreCase = true) -> "100 gram tahu mengandung sekitar 80 kalori."
            message.contains("burger", ignoreCase = true) -> "1 buah burger mengandung sekitar 150-200 kalori."
            message.contains("pizza", ignoreCase = true) -> "1 potong pizza mengandung sekitar 120 kalori."
            message.contains("nasi goreng", ignoreCase = true) -> "1 bungkus nasi goreng mengandung sekitar 180-200 kalori."


            else -> "Maaf, saya hanya bisa menjawab tentang kalori makanan. Coba tanyakan nama makanan yang ingin kamu ketahui kalorinya."
        }
    }

}
