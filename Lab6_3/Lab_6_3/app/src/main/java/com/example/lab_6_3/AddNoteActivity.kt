package com.example.lab_6_3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        databaseHelper = DatabaseHelper(this)
        val editTextTitle: EditText = findViewById(R.id.editTextTitle)
        val editTextContent: EditText = findViewById(R.id.editTextContent)
        val buttonSave: Button = findViewById(R.id.buttonSave)

        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()
            val date = System.currentTimeMillis().toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                databaseHelper.addNote(title, content, date)
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                finish() // Quay lại MainActivity
            } else {
                Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
