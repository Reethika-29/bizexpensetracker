package com.example.appinfo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        val teamName = intent.getStringExtra("teamName")

        val titleTextView = findViewById<TextView>(R.id.title)
        titleTextView.text = teamName
    }
}
