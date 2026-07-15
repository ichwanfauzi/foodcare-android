package com.example.foodcare

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StokAdapter(
    private var list: List<Makanan>,
    // Callback untuk aksi tombol
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onQuickAddClick: (Int) -> Unit
) : RecyclerView.Adapter<StokAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = view.findViewById(R.id.tvNamaItem)
        val jumlah: TextView = view.findViewById(R.id.tvJumlahItem)
        val tglMasuk: TextView = view.findViewById(R.id.tvTglMasukItem)
        val exp: TextView = view.findViewById(R.id.tvExpItem)

        // Komponen Visual
        val indicator: View = view.findViewById(R.id.indicatorColor)
        val layoutAlert: LinearLayout = view.findViewById(R.id.layoutAlert)

        // Tombol-tombol
        val btnQuickAdd: View = view.findViewById(R.id.btnQuickAdd) // View (FrameLayout)
        val btnEdit: View = view.findViewById(R.id.btnEdit)         // View (FrameLayout)
        val btnDelete: View = view.findViewById(R.id.btnDelete)     // View (FrameLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stok, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // 1. Set Teks Dasar
        holder.nama.text = item.nama
        holder.jumlah.text = "Jumlah: ${item.jumlah} ${item.satuan}"
        holder.tglMasuk.text = "Masuk: ${item.tglMasuk}"
        holder.exp.text = "Kedaluwarsa: ${item.tglExpired}"

        // 2. LOGIKA WARNA & EXPIRED (Otomatis cek tanggal)
        if (isExpired(item.tglExpired)) {
            // Jika Kadaluarsa: Garis Merah, Label Muncul, Teks Merah
            holder.indicator.setBackgroundColor(Color.parseColor("#D32F2F")) // Merah
            holder.layoutAlert.visibility = View.VISIBLE
            holder.exp.setTextColor(Color.parseColor("#D32F2F"))
        } else {
            // Jika Aman: Garis Hijau, Label Sembunyi
            holder.indicator.setBackgroundColor(Color.parseColor("#00A643")) // Hijau
            holder.layoutAlert.visibility = View.GONE
            holder.exp.setTextColor(Color.parseColor("#757575")) // Abu-abu
        }

        // 3. Set Listener Tombol
        holder.btnQuickAdd.setOnClickListener { onQuickAddClick(position) }
        holder.btnEdit.setOnClickListener { onEditClick(position) }
        holder.btnDelete.setOnClickListener { onDeleteClick(position) }
    }

    override fun getItemCount(): Int = list.size

    // Fungsi Pembantu untuk Cek Tanggal
    private fun isExpired(dateString: String): Boolean {
        return try {
            // Format harus sama dengan data (Contoh: "1 Jan 2025")
            val sdf = SimpleDateFormat("d MMM yyyy", Locale.US)
            val expDate = sdf.parse(dateString)
            val today = Date() // Tanggal hari ini

            // Jika expDate sebelum hari ini, berarti Expired
            expDate != null && expDate.before(today)
        } catch (e: Exception) {
            false // Jika format salah, anggap tidak expired
        }
    }
}