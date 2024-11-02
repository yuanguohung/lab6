package com.example.lab_6_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {

    private lateinit var contactDatabaseHelper: ContactDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        contactDatabaseHelper = ContactDatabaseHelper(this)

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val buttonSave: Button = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                val contact = Contact(0, name, phone, email) // ID sẽ được tự động tạo
                contactDatabaseHelper.addContact(contact)
                Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show()
                finish() // Quay lại danh sách sau khi thêm
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
