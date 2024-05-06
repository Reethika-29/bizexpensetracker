package com.example.appinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

// Import this line to access views by their IDs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var button: Button = findViewById(R.id.button)
        // Set OnClickListener to the button
        button.setOnClickListener {
            // Create an Intent to navigate to MainActivity2
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent) // Start MainActivity2
        }
    }
}
