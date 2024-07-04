package com.example.tasklist.activities

import NotesDataBaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasklist.data.Note
import com.example.tasklist.databinding.ActivityAddNotesBinding


class addNotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNotesBinding
    lateinit var db: NotesDataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDataBaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.tittleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()


        }
    }
}