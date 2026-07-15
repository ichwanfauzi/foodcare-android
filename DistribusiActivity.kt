package com.example.foodcare // Sesuaikan dengan package kamu

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DistribusiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distribusi)

        // Inisialisasi view
        val spinner = findViewById<Spinner>(R.id.spinnerStok)
        val btnSalurkan = findViewById<Button>(R.id.btnSalurkan)

        // Data Dummy untuk Spinner
        val dataStok = arrayOf("Beras 5kg", "Minyak 2L", "Gula 1kg", "Mie Instan")

        // Buat Adapter untuk Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dataStok)
        spinner.adapter = adapter

        // Logika saat tombol ditekan
        btnSalurkan.setOnClickListener {
            val itemTerpilih = spinner.selectedItem.toString()
            Toast.makeText(this, "Berhasil menyalurkan: $itemTerpilih", Toast.LENGTH_SHORT).show()

            // Selesai dan kembali ke menu utama
            finish()
        }
    }
}