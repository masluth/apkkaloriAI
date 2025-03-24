package com.smkth.final_project_aplikasi_penghitung_kalori

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smkth.final_project_aplikasi_penghitung_kalori.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("calorie_prefs", MODE_PRIVATE)

        val olahragaList = mapOf(
            "Berlari" to 8.0,
            "Bersepeda" to 6.0,
            "Berenang" to 7.0,
            "Jalan kaki" to 3.5
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, olahragaList.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOlahraga.adapter = adapter

        binding.btnHitung.setOnClickListener {
            val berat = binding.etBerat.text.toString().toDoubleOrNull()
            val durasi = binding.etDurasi.text.toString().toDoubleOrNull()
            val olahraga = binding.spinnerOlahraga.selectedItem.toString()

            if (berat != null && durasi != null) {
                val met = olahragaList[olahraga] ?: 1.0
                val kalori = met * berat * durasi / 60

                binding.tvHasil.text = "Kalori terbakar: %.2f kalori".format(kalori)

                // Simpan hasil terakhir
                val editor = sharedPreferences.edit()
                editor.putString("hasil", binding.tvHasil.text.toString())
                editor.apply()

            } else {
                Toast.makeText(this, "Masukkan data yang valid!", Toast.LENGTH_SHORT).show()
            }
        }

        // Ambil hasil terakhir saat aplikasi dibuka
        binding.tvHasil.text = sharedPreferences.getString("hasil", "")
    }
}