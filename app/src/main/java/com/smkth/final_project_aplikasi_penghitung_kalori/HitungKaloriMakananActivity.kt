package com.smkth.final_project_aplikasi_penghitung_kalori

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smkth.final_project_aplikasi_penghitung_kalori.databinding.ActivityHitungKaloriMakananBinding

class HitungKaloriMakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHitungKaloriMakananBinding
    private lateinit var sharedPref: SharedPreferences

    private val dataKalori = mapOf(
        "Nasi Putih" to 175,
        "Ayam Goreng" to 260,
        "Telur Rebus" to 155,
        "Tempe Goreng" to 200,
        "Tahu Goreng" to 180,
        "Indomie Goreng" to 445
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHitungKaloriMakananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val makananList = dataKalori.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, makananList)
        binding.autoMakanan.setAdapter(adapter)

        binding.autoMakanan.setOnClickListener {
            binding.autoMakanan.showDropDown()
        }


        sharedPref = getSharedPreferences("kalori_prefs", MODE_PRIVATE)

        binding.btnHitung.setOnClickListener {
            val makanan = binding.autoMakanan.text.toString()
            val berat = binding.editJumlahGram.text.toString()

            if (makanan !in dataKalori || berat.isEmpty()) {
                Toast.makeText(this, "Isi data dengan benar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val kaloriPer100 = dataKalori[makanan]!!
            val beratGram = berat.toFloat()
            val totalKalori = (kaloriPer100 * beratGram) / 100

            val kaloriSekarang = sharedPref.getInt("kalori_masuk", 0)
            sharedPref.edit().putInt("kalori_masuk", kaloriSekarang + totalKalori.toInt()).apply()

            binding.txtHasilKalori.text = "Kalori: ${"%.2f".format(totalKalori)} kkal"
            Toast.makeText(this, "Kalori berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            finish()
        }
    }

}