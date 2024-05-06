package com.example.appinfo



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class MainActivity2 : AppCompatActivity() {


    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button


    // Firebase Database reference
    private val database = FirebaseDatabase.getInstance().reference.child("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        // Find views
        nameEditText = findViewById(R.id.nameedit)
        emailEditText = findViewById(R.id.editTextTextPass)
        phoneEditText = findViewById(R.id.editTextPhone)
        genderEditText = findViewById(R.id.editTextText2)
        ageEditText = findViewById(R.id.editTextPhone2)
        signUpButton = findViewById(R.id.button2)
        loginButton = findViewById(R.id.button5)


        loginButton.setOnClickListener {
            val inte = Intent(this, MainActivity8::class.java)
            startActivity(inte)

        }


        signUpButton.setOnClickListener {


            // Retrieve text from EditText fields
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val gender = genderEditText.text.toString()
            val age = ageEditText.text.toString()
            // Start the next activity
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || age.isEmpty()) {
                // Show error message or toast indicating fields are empty
                // For example:
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()

            } else {
                // Save user data to Firebase Database
                saveUserData(name, email, phone, gender, age)



            }


            // Validate data if needed
            // For example, check if fields are not empty


            // Save user data to Firebase Database
            saveUserData(name, email, phone, gender, age)


            // Start the next activity
            val i = Intent(this, MainActivity3::class.java)
            startActivity(i)
        }
    }


    private fun saveUserData(name: String, email: String, phone: String, gender: String, age: String) {
        // Get a reference to the Firebase database
        val database = FirebaseDatabase.getInstance().reference

        // Create a unique key for the user
        val userId = database.child("users").push().key

        // Create a User object
        val user = User(name, email, phone, gender, age)

        // Save the user object to Firebase Database under the generated key
        userId?.let {
            database.child("users").child(it).setValue(user)
                .addOnSuccessListener {
                    // Data successfully saved
                    // You can add any additional logic here if needed
                }
                .addOnFailureListener {
                    // Failed to save data
                    // Handle error gracefully
                }
        }
    }

}


// User data class
data class User(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val gender: String = "",
    val age: String = ""
)
