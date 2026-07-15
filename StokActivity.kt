package com.example.foodcare

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class StokActivity : AppCompatActivity() {

    private lateinit var rvStok: RecyclerView
    private lateinit var adapter: StokAdapter
    // Format tanggal agar seragam: "1 Jan 2025"
    private val dateFormatter = SimpleDateFormat("d MMM yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stok)

        // 1. Setup RecyclerView
        rvStok = findViewById(R.id.rvStok)
        rvStok.layoutManager = LinearLayoutManager(this)

        // Load data awal
        updateUI()

        // 2. Tombol Back (Kembali ke Dashboard)
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish() // Menutup activity ini
        }

        // 3. Tombol FAB Orange (Tambah Data Baru)
        findViewById<View>(R.id.fabAdd).setOnClickListener {
            showFormDialog(isEdit = false, index = -1)
        }
    }

    // Fungsi untuk me-refresh list saat data berubah
    private fun updateUI() {
        adapter = StokAdapter(AppManager.daftarStok,
            onEditClick = { index ->
                showFormDialog(isEdit = true, index = index)
            },
            onDeleteClick = { index ->
                showDeleteConfirm(index)
            },
            onQuickAddClick = { index ->
                showQuickAddDialog(index)
            }
        )
        rvStok.adapter = adapter
    }

    // --- DIALOG 1: FORMULIR TAMBAH / EDIT DATA LENGKAP ---
    private fun showFormDialog(isEdit: Boolean, index: Int) {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (isEdit) "Edit Makanan" else "Tambah Stok Baru")

        // Wadah Layout (Dibuat lewat kodingan agar tidak perlu file XML baru)
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        // Input: Nama
        val inputNama = EditText(context)
        inputNama.hint = "Nama Makanan (Cth: Beras)"
        layout.addView(inputNama)

        // Input: Jumlah
        val inputJumlah = EditText(context)
        inputJumlah.hint = "Jumlah (Angka saja)"
        inputJumlah.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(inputJumlah)

        // Input: Tanggal Masuk (Read Only, Klik buat munculin kalender)
        val inputTglMasuk = EditText(context)
        inputTglMasuk.hint = "Tanggal Masuk"
        inputTglMasuk.focusable = View.NOT_FOCUSABLE
        inputTglMasuk.isClickable = true
        layout.addView(inputTglMasuk)

        // Input: Tanggal Expired (Read Only, Klik buat munculin kalender)
        val inputExp = EditText(context)
        inputExp.hint = "Tanggal Kadaluarsa"
        inputExp.focusable = View.NOT_FOCUSABLE
        inputExp.isClickable = true
        layout.addView(inputExp)

        // Jarak antar elemen
        inputJumlah.layoutParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = 20 }
        inputTglMasuk.layoutParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = 20 }
        inputExp.layoutParams = LinearLayout.LayoutParams(-1, -2).apply { topMargin = 20 }

        // Logic Kalender
        val calendar = Calendar.getInstance()
        val dateListener = { view: EditText ->
            DatePickerDialog(context, { _, year, month, day ->
                calendar.set(year, month, day)
                view.setText(dateFormatter.format(calendar.time))
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        inputTglMasuk.setOnClickListener { dateListener(inputTglMasuk) }
        inputExp.setOnClickListener { dateListener(inputExp) }

        // Jika Edit, isi data lama ke form
        if (isEdit && index >= 0) {
            val item = AppManager.daftarStok[index]
            inputNama.setText(item.nama)
            inputJumlah.setText(item.jumlah.toString())
            inputTglMasuk.setText(item.tglMasuk)
            inputExp.setText(item.tglExpired)
        } else {
            // Jika Baru, set tanggal hari ini otomatis
            inputTglMasuk.setText(dateFormatter.format(Date()))
        }

        builder.setView(layout)

        // Tombol Simpan
        builder.setPositiveButton("Simpan") { _, _ ->
            val nama = inputNama.text.toString()
            val jml = inputJumlah.text.toString().toIntOrNull() ?: 0
            val tglM = inputTglMasuk.text.toString()
            val tglE = inputExp.text.toString()

            if (nama.isNotEmpty() && tglM.isNotEmpty() && tglE.isNotEmpty()) {
                if (isEdit) {
                    // Update Data Lama
                    val item = AppManager.daftarStok[index]
                    item.nama = nama
                    item.jumlah = jml
                    item.tglMasuk = tglM
                    item.tglExpired = tglE
                    Toast.makeText(context, "Data diperbarui!", Toast.LENGTH_SHORT).show()
                } else {
                    // Tambah Data Baru
                    // Default satuan Kg & Kategori Makanan (Bisa dikembangkan nanti)
                    val baru = Makanan(nama, jml, "Kg", "Makanan", tglM, tglE)
                    AppManager.daftarStok.add(baru)
                    Toast.makeText(context, "Data berhasil ditambah!", Toast.LENGTH_SHORT).show()
                }
                updateUI()
            } else {
                Toast.makeText(context, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Batal", null)
        builder.show()
    }

    // --- DIALOG 2: HAPUS ---
    private fun showDeleteConfirm(index: Int) {
        val item = AppManager.daftarStok[index]
        AlertDialog.Builder(this)
            .setTitle("Hapus Data")
            .setMessage("Yakin ingin menghapus ${item.nama}?")
            .setPositiveButton("Hapus") { _, _ ->
                AppManager.daftarStok.removeAt(index)
                updateUI()
                Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // --- DIALOG 3: QUICK ADD (Tambah Cepat) ---
    private fun showQuickAddDialog(index: Int) {
        val item = AppManager.daftarStok[index]
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.hint = "Masukkan jumlah tambahan"

        AlertDialog.Builder(this)
            .setTitle("Tambah Stok: ${item.nama}")
            .setView(input)
            .setPositiveButton("Tambah") { _, _ ->
                val tambah = input.text.toString().toIntOrNull() ?: 0
                if (tambah > 0) {
                    item.jumlah += tambah
                    updateUI()
                    Toast.makeText(this, "Stok bertambah jadi ${item.jumlah}!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}