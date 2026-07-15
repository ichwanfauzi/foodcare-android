package com.example.foodcare

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inisialisasi Kartu Menu
        val menuStok = findViewById<CardView>(R.id.menuStok)
        val menuDistribusi = findViewById<CardView>(R.id.menuDistribusi)
        val menuEdukasi = findViewById<CardView>(R.id.menuEdukasi)
        val menuTentang = findViewById<CardView>(R.id.menuTentang)

        // 2. Aksi Klik Menu Stok -> Pindah ke StokActivity
        menuStok.setOnClickListener {
            val intent = Intent(this, StokActivity::class.java)
            startActivity(intent)
        }

        // 3. Aksi Klik Menu Lainnya (Sementara Toast dulu)
        menuDistribusi.setOnClickListener {
            Toast.makeText(this, "Menu Distribusi akan segera hadir!", Toast.LENGTH_SHORT).show()
            // Nanti bisa diganti: startActivity(Intent(this, DistribusiActivity::class.java))
        }

        menuEdukasi.setOnClickListener {
            Toast.makeText(this, "Menu Edukasi SDG 2", Toast.LENGTH_SHORT).show()
        }

        menuTentang.setOnClickListener {
            Toast.makeText(this, "Aplikasi FoodCare v1.0", Toast.LENGTH_SHORT).show()
        }
    }
}