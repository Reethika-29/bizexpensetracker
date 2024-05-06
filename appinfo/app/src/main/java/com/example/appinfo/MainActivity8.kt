package com.example.appinfo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class MainActivity8 : AppCompatActivity() {
    private lateinit var teamNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var showPasswordCheckBox: CheckBox


    // Firebase Database reference
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("entries")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)


        // Initialize views
        teamNameEditText = findViewById(R.id.nameedit)
        passwordEditText = findViewById(R.id.passedit)
        showPasswordCheckBox = findViewById(R.id.checkBoxShowPassword)


        val loginButton: Button = findViewById(R.id.button)
        val cancelButton: Button = findViewById(R.id.button2)


        // Set onClickListener for cancel button
        cancelButton.setOnClickListener {
            // Clear all fields
            teamNameEditText.text.clear()
            passwordEditText.text.clear()
            showPasswordCheckBox.isChecked = false
        }


        // Add functionality for login button
        loginButton.setOnClickListener {
            val teamName = teamNameEditText.text.toString()
            val password = passwordEditText.text.toString()


            // Check if any field is empty
            if (teamName.isEmpty() || password.isEmpty()) {
                // Display a toast message to fill in all details
                Toast.makeText(this, "Please fill in team name and password", Toast.LENGTH_SHORT).show()
            } else {
                // Check if the team name and password match with any entry in the database
                checkLogin(teamName, password)
            }
        }
    }


    private fun checkLogin(teamName: String, password: String) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var loggedIn = false
                for (entrySnapshot in snapshot.children) {
                    val entry = entrySnapshot.getValue(Entry::class.java)
                    if (entry != null && entry.name == teamName && entry.password == password) {
                        loggedIn = true
                        // If team name and password match, perform login
                        // Start MainActivity3 after successful login
                        val intent = Intent(this@MainActivity8, MainActivity5::class.java)
                        intent.putExtra("teamName", teamName)
                        startActivity(intent)
                        Toast.makeText(this@MainActivity8, "Login Successful", Toast.LENGTH_SHORT).show()
                        finish() // Close the current activity
                        break
                    }
                }
                if (!loggedIn) {
                    // If no matching entry found, display error message
                    Toast.makeText(this@MainActivity8, "Invalid team name or password", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                Toast.makeText(this@MainActivity8, "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
