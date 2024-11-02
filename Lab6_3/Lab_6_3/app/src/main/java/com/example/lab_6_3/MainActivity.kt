package com.example.lab_6_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var notes: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        val buttonAddNote: Button = findViewById(R.id.buttonAddNote)

        // Thiết lập RecyclerView
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        loadNotes()

        // Mở AddNoteActivity
        buttonAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    private fun loadNotes() {
        notes = databaseHelper.getAllNotes()
        noteAdapter = NoteAdapter(notes) { note -> onNoteClick(note) }
        recyclerViewNotes.adapter = noteAdapter
    }

    private fun onNoteClick(note: Note) {
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadNotes() // Tải lại danh sách ghi chú khi quay lại MainActivity
    }
}
