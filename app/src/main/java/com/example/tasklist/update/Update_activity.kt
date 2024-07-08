package com.example.tasklist.update

import NotesDataBaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Toast
import com.example.tasklist.R
import com.example.tasklist.data.Note
import com.example.tasklist.databinding.ActivityUpdateBinding

class update_activity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding
    lateinit var db: NotesDataBaseHelper
    private var noteId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = NotesDataBaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }
        val note = db.getNoteById(noteId)
        binding.updatetittleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.content)


        binding.updatesaveButton.setOnClickListener {
            val newTitle = binding.updatetittleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()
            val category = note.category
            val updateNote = Note(noteId , newTitle, newContent, category)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Saves Changes",Toast.LENGTH_SHORT).show()
        }

    }

}
