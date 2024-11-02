package com.example.lab_6_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var contactDatabaseHelper: ContactDatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        val textViewNameDetail: TextView = findViewById(R.id.textViewNameDetail)
        val textViewPhoneDetail: TextView = findViewById(R.id.textViewPhoneDetail)
        val textViewEmailDetail: TextView = findViewById(R.id.textViewEmailDetail)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)
        val buttonDelete: Button = findViewById(R.id.buttonDelete)

        contactDatabaseHelper = ContactDatabaseHelper(this)

        contactId = intent.getIntExtra("contactId", -1)

        if (contactId != -1) {
            val contact = contactDatabaseHelper.getContactById(contactId)
            if (contact != null) {
                textViewNameDetail.text = contact.name
                textViewPhoneDetail.text = contact.phone
                textViewEmailDetail.text = contact.email
            } else {
                Toast.makeText(this, "Contact not found", Toast.LENGTH_SHORT).show()
            }
        }

        buttonEdit.setOnClickListener {
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("contactId", contactId)
            startActivity(intent)
        }


        buttonDelete.setOnClickListener {
            val deleted = contactDatabaseHelper.deleteContact(contactId)
            if (deleted) {
                Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to delete contact", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
