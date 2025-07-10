package com.smkth.final_project_aplikasi_penghitung_kalori

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smktnh.final_project_aplikasi_penghitung_kalori.databinding.ActivityTambahkanKaloriBinding

class TambahkanKaloriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahkanKaloriBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahkanKaloriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("kalori_prefs", MODE_PRIVATE)

        binding.btnSimpanKalori.setOnClickListener {
            val inputKalori = binding.etJumlahKalori.text.toString()

            if (inputKalori.isEmpty()) {
                Toast.makeText(this, "Kalori tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val kaloriBaru = inputKalori.toInt()
            val kaloriSekarang = sharedPref.getInt("kalori_masuk", 0)
            val totalKalori = kaloriSekarang + kaloriBaru

            sharedPref.edit().putInt("kalori_masuk", totalKalori).apply()

            Toast.makeText(this, "Kalori berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}