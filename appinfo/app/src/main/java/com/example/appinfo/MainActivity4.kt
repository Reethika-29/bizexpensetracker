package com.example.appinfo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val teamName = intent.getStringExtra("teamName")
        val numberOfPeople = intent.getStringExtra("numberOfPeople")?.toIntOrNull() ?: 0

        val layout = findViewById<LinearLayout>(R.id.layout_people_details)

        // Dynamically create EditText fields for each person
        for (i in 1..numberOfPeople) {
            val editTextName = EditText(this)
            editTextName.hint = "Name $i"
            layout.addView(editTextName)

            val editTextEmail = EditText(this)
            editTextEmail.hint = "Email $i"
            layout.addView(editTextEmail)

            val editTextPhone = EditText(this)
            editTextPhone.hint = "Phone $i"
            layout.addView(editTextPhone)
        }

        val createTeamButton = findViewById<Button>(R.id.button)
        createTeamButton.setOnClickListener {
            val i = Intent(this, MainActivity5::class.java)
            startActivity(i)
        }
    }
}
