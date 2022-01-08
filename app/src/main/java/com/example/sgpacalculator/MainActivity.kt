package com.example.sgpacalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        val sgpaButton = findViewById<CardView>(R.id.cardSgpa)
        sgpaButton.setOnClickListener{
            val intent = Intent(this, sgpa_activity::class.java)
            startActivity(intent)
        }
    }
}