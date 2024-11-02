package com.example.lab_6_3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var noteId: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val textViewTitle: TextView = findViewById(R.id.textViewTitle)
        val textViewContent: TextView = findViewById(R.id.textViewContent)
        val buttonDelete: Button = findViewById(R.id.buttonDelete)

        databaseHelper = DatabaseHelper(this)
        noteId = intent.getIntExtra("noteId", -1)

        if (noteId != -1) {
            val note = databaseHelper.getAllNotes().find { it.id == noteId }
            note?.let {
                textViewTitle.text = it.title
                textViewContent.text = it.content
            }

            buttonDelete.setOnClickListener {
                databaseHelper.deleteNote(noteId)
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                finish() // Quay lại MainActivity
            }
        }
    }
}
