package com.smkth.final_project_aplikasi_penghitung_kalori

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.animation.AnimationUtils
import com.smktnh.final_project_aplikasi_penghitung_kalori.R
import com.smktnh.final_project_aplikasi_penghitung_kalori.databinding.ActivityAturTargetBinding

class AturTargetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAturTargetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val aktivitasList = listOf(
            "Tidak aktif",     // 1.2
            "Ringan",          // 1.375
            "Sedang",          // 1.55
            "Berat",           // 1.725
            "Sangat berat"     // 1.9
        )

        val anim = AnimationUtils.loadAnimation(this, R.anim.btn_click)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, aktivitasList)
        binding.autoAktivitas.setAdapter(adapter)

        binding.autoAktivitas.setOnClickListener {
            binding.autoAktivitas.showDropDown()
        }

        val prefs = getSharedPreferences("kalori_prefs", MODE_PRIVATE)
        val currentTarget = prefs.getInt("target_kalori", 0)

        if (currentTarget > 0) {
            binding.etTargetKalori.setText(currentTarget.toString())
        }

        binding.btnSimpanTarget.setOnClickListener {
            binding.btnSimpanTarget.startAnimation(anim)
            it.animate().apply {
                duration = 150
                scaleX(0.95f)
                scaleY(0.95f)
                withEndAction {
                    it.animate().scaleX(1f).scaleY(1f).duration = 150
                }
            }
            val targetInput = binding.etTargetKalori.text.toString()
            if (targetInput.isEmpty()) {
                Toast.makeText(this, "Harap isi target kalori", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val targetKalori = targetInput.toInt()
            prefs.edit().putInt("target_kalori", targetKalori).apply()
            Toast.makeText(this, "Target kalori disimpan!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnRekomendasi.setOnClickListener {
            binding.btnRekomendasi.startAnimation(anim)
            try {
                val hasil = hitungRekomendasiKalori()
                binding.etTargetKalori.setText(hasil.toString())
                Toast.makeText(this, "Target disarankan: $hasil kkal", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Isi data dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun hitungRekomendasiKalori(): Int {
        val usia = binding.etUsia.text.toString().toInt()
        val tinggi = binding.etTinggi.text.toString().toDouble()
        val berat = binding.etBerat.text.toString().toDouble()
        val aktivitas = binding.autoAktivitas.text.toString()
        val isPria = binding.rbPria.isChecked

        val bmr = if (isPria) {
            88.362 + (13.397 * berat) + (4.799 * tinggi) - (5.677 * usia)
        } else {
            447.593 + (9.247 * berat) + (3.098 * tinggi) - (4.330 * usia)
        }

        val faktorAktivitas = when (aktivitas) {
            "Tidak aktif" -> 1.2
            "Ringan" -> 1.375
            "Sedang" -> 1.55
            "Berat" -> 1.725
            "Sangat berat" -> 1.9
            else -> 1.2
        }


        return (bmr * faktorAktivitas).toInt()
    }

}