package com.example.lab_6_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditContactActivity : AppCompatActivity() {

    private lateinit var contactDatabaseHelper: ContactDatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        contactDatabaseHelper = ContactDatabaseHelper(this)

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val buttonSave: Button = findViewById(R.id.buttonSave)

        contactId = intent.getIntExtra("contactId", -1)

        if (contactId != -1) {
            val contact = contactDatabaseHelper.getContactById(contactId)
            if (contact != null) {
                editTextName.setText(contact.name)
                editTextPhone.setText(contact.phone)
                editTextEmail.setText(contact.email)
            } else {
                Toast.makeText(this, "Contact not found", Toast.LENGTH_SHORT).show()
                finish() // Nếu không tìm thấy liên hệ, kết thúc activity
            }
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                val updatedContact = Contact(contactId, name, phone, email)
                contactDatabaseHelper.updateContact(updatedContact)
                Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show()
                finish() // Quay lại danh sách sau khi cập nhật
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
