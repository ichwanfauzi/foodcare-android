package com.example.foodcare

object AppManager {
    // List ini akan menyimpan semua data stok selama aplikasi hidup
    val daftarStok = ArrayList<Makanan>()

    init {
        // KITA ISI DUMMY DATA AGAR MIRIP GAMBAR SAAT DI-RUN
        daftarStok.add(Makanan("Beras Premium", 50, "Kg", "Makanan", "1 Jan 2025", "1 Jun 2025"))
        daftarStok.add(Makanan("Minyak Goreng", 30, "Liter", "Minuman", "5 Jan 2025", "1 Apr 2025"))
        daftarStok.add(Makanan("Gula Pasir", 25, "Kg", "Makanan", "10 Jan 2025", "1 Jul 2025"))
    }
}