package com.example.foodcare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // INTENT EXPLICIT: Pindah ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}