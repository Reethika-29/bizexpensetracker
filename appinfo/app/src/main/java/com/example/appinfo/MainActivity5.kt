package com.example.appinfo

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity5 : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var textView5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().reference.child("expense")

        // Retrieve team name from intent extras
        val teamName = intent.getStringExtra("teamName")
        textView5 = findViewById(R.id.textView5)
        textView5.text = teamName

        button1 = findViewById(R.id.button7)

        button1.setOnClickListener {
            showInputDialog()
        }
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Details")

        val inputLayout = View.inflate(this, R.layout.dialog_input, null)
        val nameInput = inputLayout.findViewById<EditText>(R.id.nameInput)
        val descriptionInput = inputLayout.findViewById<EditText>(R.id.descriptionInput)
        val amountInput = inputLayout.findViewById<EditText>(R.id.amountInput)
        val datePicker = inputLayout.findViewById<DatePicker>(R.id.datePicker)

        builder.setView(inputLayout)

        builder.setPositiveButton("OK") { dialog, which ->
            val name = nameInput.text.toString().trim()
            val description = descriptionInput.text.toString().trim()
            val amount = amountInput.text.toString().trim()
            val calendar = Calendar.getInstance()
            calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            val selectedDate = formatDateToString(calendar.time)

            // Save data to Firebase Realtime Database
            val expenseId = databaseReference.push().key
            if (expenseId != null) {
                val expenseMap = HashMap<String, Any>()
                expenseMap["name"] = name
                expenseMap["description"] = description
                expenseMap["amount"] = amount
                expenseMap["date"] = selectedDate
                databaseReference.child(expenseId).setValue(expenseMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Expense added successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Failed to generate expense ID", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun formatDateToString(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}
