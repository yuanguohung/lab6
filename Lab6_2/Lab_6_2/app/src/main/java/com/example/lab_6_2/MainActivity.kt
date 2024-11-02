package com.example.lab_6_2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var contactDatabaseHelper: ContactDatabaseHelper
    private lateinit var listViewContacts: ListView
    private lateinit var contactAdapter: ArrayAdapter<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewContacts = findViewById(R.id.listViewContacts)
        contactDatabaseHelper = ContactDatabaseHelper(this)

        loadContacts()

        listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = contactAdapter.getItem(position)
            val intent = Intent(this, ContactDetailActivity::class.java)
            intent.putExtra("contactId", contact?.id)
            startActivity(intent)
        }

        val buttonAddContact: Button = findViewById(R.id.buttonAddContact)
        buttonAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadContacts() {
        val contacts = contactDatabaseHelper.getAllContacts()
        contactAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        listViewContacts.adapter = contactAdapter
    }
}
