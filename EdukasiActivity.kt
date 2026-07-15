package com.example.foodcare

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EdukasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edukasi)

        findViewById<Button>(R.id.btnWeb).setOnClickListener {
            // INTENT IMPLICIT: Membuka Browser Luar
            val link = "https://www.un.org/sustainabledevelopment/hunger/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
    }
}