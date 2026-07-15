package com.example.foodcare

// Ini adalah "Resep" untuk satu item makanan
data class Makanan(
    var nama: String,
    var jumlah: Int,
    var satuan: String,   // Contoh: "Kg", "Liter", "Boks"
    var kategori: String, // "Makanan" atau "Minuman"
    var tglMasuk: String,
    var tglExpired: String
)