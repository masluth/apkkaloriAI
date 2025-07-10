package com.smkth.final_project_aplikasi_penghitung_kalori

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smktnh.final_project_aplikasi_penghitung_kalori.databinding.ActivityHitungPembakaranKaloriBinding

class HitungPembakaranKaloriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHitungPembakaranKaloriBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val dataOlahraga = mapOf(
        "Jogging" to 7.0,
        "Bersepeda" to 6.8,
        "Renang" to 8.0,
        "Yoga" to 3.0,
        "Angkat Beban" to 5.0,
        "Berjalan Santai" to 2.5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungPembakaranKaloriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val olaharagList = dataOlahraga.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, olaharagList)
        binding.autoOlahraga.setAdapter(adapter)

        binding.autoOlahraga.setOnClickListener {
            binding.autoOlahraga.showDropDown()
        }

        sharedPreferences = getSharedPreferences("kalori_prefs", MODE_PRIVATE)

        binding.btnHitungTerbakar.setOnClickListener {
            val olahraga = binding.autoOlahraga.text.toString()
            val berat = binding.etBeratBadan.text.toString()
            val durasi = binding.etDurasi.text.toString()

            if (olahraga !in dataOlahraga || berat.isEmpty() || durasi.isEmpty()) {
                Toast.makeText(this, "Isi semua data dengan benar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val met = dataOlahraga[olahraga]!!
            val beratKg = berat.toFloat()
            val durasiJam = durasi.toFloat() / 60

            val kaloriTerbakar = met * beratKg * durasiJam

            // Simpan ke SharedPreferences
            val kaloriKeluar = sharedPreferences.getInt("kalori_keluar", 0)
            sharedPreferences.edit().putInt("kalori_keluar", kaloriKeluar + kaloriTerbakar.toInt()).apply()

            binding.tvHasilKaloriTerbakar.text = "Kalori Terbakar: ${"%.2f".format(kaloriTerbakar)} kkal"
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}