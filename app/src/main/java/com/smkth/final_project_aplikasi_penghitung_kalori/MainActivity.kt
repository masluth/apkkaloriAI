package com.smkth.final_project_aplikasi_penghitung_kalori

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.content.Context
import android.content.Intent
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smkth.final_project_aplikasi_penghitung_kalori.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("kalori_prefs", MODE_PRIVATE)

        val btnTambahMakanan = findViewById<FrameLayout>(R.id.btnTambahMakanan)
        val btnHitungMakanan = findViewById<FrameLayout>(R.id.btnHitungMakanan)
        val btnHitungOlahraga = findViewById<FrameLayout>(R.id.btnHitungOlahraga)
        val btnAturTarget = findViewById<FrameLayout>(R.id.btnAturTarget)
        val btnReset = findViewById<FrameLayout>(R.id.btnReset)


        btnTambahMakanan.setOnClickListener{
            startActivity(Intent(this, TambahkanKaloriActivity::class.java));
        }

        btnHitungMakanan.setOnClickListener {
            startActivity(Intent(this, HitungKaloriMakananActivity::class.java))
        }

        btnHitungOlahraga.setOnClickListener {
            startActivity(Intent(this, HitungPembakaranKaloriActivity::class.java))
        }

        btnAturTarget.setOnClickListener {
            startActivity(Intent(this, AturTargetActivity::class.java))
        }
        binding.btnHitungKalorin.setOnClickListener{
            startActivity(Intent(this, HitungKaloriMakananActivity::class.java))
        }

        btnReset.setOnClickListener{
            resetData()
        }

    }
    private fun tampilkanRingkasanKalori() {
        val target = sharedPref.getInt("target_kalori", 0)
        val masuk = sharedPref.getInt("kalori_masuk", 0)
        val terbakar = sharedPref.getInt("kalori_terbakar", 0)
        val net = masuk - terbakar

        binding.tvTargetKalori.text = "$target"
        binding.tvKaloriMasuk.text = "$masuk"
        binding.tvKaloriTerbakar.text = "$terbakar"
        binding.tvNetKalori.text = "$net"
        binding.tvNetCalorieRing.text = "$net"
    }

    private fun resetData() {
        val editor = sharedPref.edit()
        editor.remove("kalori_masuk")
        editor.remove("kalori_terbakar")
        editor.remove("target_kalori")
        editor.apply()
        tampilkanRingkasanKalori()
        Toast.makeText(this, "Data berhasil direset!", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        updateRingkasanKalori()
    }

    private fun updateRingkasanKalori() {
        val prefs = getSharedPreferences("kalori_prefs", MODE_PRIVATE)
        val kaloriMasuk = prefs.getInt("kalori_masuk", 0)
        val kaloriKeluar = prefs.getInt("kalori_keluar", 0)
        val targetKalori = prefs.getInt("target_kalori", 0)

        val sisaKalori = targetKalori - (kaloriMasuk - kaloriKeluar)

        binding.tvTargetKalori.text = "$targetKalori"
        binding.tvKaloriMasuk.text = "$kaloriMasuk"
        binding.tvKaloriTerbakar.text = "$kaloriKeluar"
        binding.tvNetKalori.text = "$sisaKalori"
        binding.tvNetCalorieRing.text = "$sisaKalori"
    }

}