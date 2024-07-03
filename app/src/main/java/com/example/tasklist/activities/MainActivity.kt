package com.example.tasklist.activities
import NotesDataBaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklist.data.Note
import com.example.tasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDataBaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = NotesDataBaseHelper(this)


        val noteList: MutableList<Note> = db.getAllNotes().toMutableList()
        notesAdapter = NotesAdapter(noteList, this)

       //hacer el binding del enableSwipe
        notesAdapter.enableSwipe(binding.notesRecyclerView)


        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter


        binding.addButton.setOnClickListener {
            val intent = Intent (this, addNotesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}
