package com.example.appinfo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity3 : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var numberOfPeopleEditText: EditText
    private lateinit var domainEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var createButton: Button
    private lateinit var cancelButton: Button

    private val database = FirebaseDatabase.getInstance().reference.child("entries")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        nameEditText = findViewById(R.id.nameedit)
        numberOfPeopleEditText = findViewById(R.id.editTextno)
        domainEditText = findViewById(R.id.editTextdom)
        passwordEditText = findViewById(R.id.editTextText)
        createButton = findViewById(R.id.button3)
        cancelButton = findViewById(R.id.button4)

        createButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val numberOfPeople = numberOfPeopleEditText.text.toString()
            val domain = domainEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isEmpty() || numberOfPeople.isEmpty() || domain.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            } else {
                saveDataToDatabase(name, numberOfPeople, domain, password)
                val intent = Intent(this, MainActivity4::class.java)
                intent.putExtra("teamName", name)
                intent.putExtra("numberOfPeople", numberOfPeople) // Pass number of people as extra
                startActivity(intent)
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun saveDataToDatabase(name: String, numberOfPeople: String, domain: String, password: String) {
        val entryId = database.push().key
        val entry = entryId?.let { Entry(name, numberOfPeople, domain, password) }
        entry?.let {
            database.child(entryId).setValue(it)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

data class Entry(
    val name: String = "",
    val numberOfPeople: String = "",
    val domain: String = "",
    val password: String = ""
)
