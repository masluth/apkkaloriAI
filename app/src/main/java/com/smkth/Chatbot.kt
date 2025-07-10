package com.smkth

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.smktnh.final_project_aplikasi_penghitung_kalori.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Chatbot {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val systemPrompt = """
        Saya adalah chatbot pintar bernama Kalo. 
        Jawaban kamu hanya boleh seputar kalori makanan, nutrisi, dan gizi.
        Jika pertanyaannya tidak relevan, jawab: 
        "Maaf, saya hanya bisa membantu soal kalori makanan dan gizi."
    """.trimIndent()

    suspend fun ask(question: String): String = withContext(Dispatchers.IO) {
        return@withContext try {
            val finalPrompt = "$systemPrompt\nPertanyaan: $question"
            val response = generativeModel.generateContent(finalPrompt)
            response.text ?: "Maaf, saya tidak bisa menjawab saat ini."
        } catch (e: Exception) {
            Log.e("Chatbot", "Error: ${e.message}", e)
            "Terjadi kesalahan saat menghubungi AI."
        }
    }
}
