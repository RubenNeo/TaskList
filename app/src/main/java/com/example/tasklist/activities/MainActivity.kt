package com.example.tasklist.activities

import NotesDataBaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklist.R
import com.example.tasklist.adapter.NotesAdapter
import com.example.tasklist.data.Note
import com.example.tasklist.databinding.ActivityAddNotesBinding
import com.example.tasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDataBaseHelper
    private lateinit var notesAdapter: NotesAdapter

    var categoryID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryID = intent.getIntExtra("CATEGORY_ID", -1)

        val background = when (categoryID) {
            1 -> R.drawable.bakground_home
            2 -> R.drawable.bathroom
            3 -> R.drawable.kitchen
            4 -> R.drawable.bakground_home
            5 -> R.drawable.bakground_home
            6 -> R.drawable.bakground_home
            else -> R.drawable.blue_border
        }

        binding.background.setImageResource(background)

        db = NotesDataBaseHelper(this)

        val noteList: MutableList<Note> = db.getAllNotesByCategory(categoryID).toMutableList()
        notesAdapter = NotesAdapter(noteList, this)

        //hacer el binding del enableSwipe
        notesAdapter.enableSwipe(binding.notesRecyclerView)


        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter


        binding.addButton.setOnClickListener {
            val intent = Intent(this, addNotesActivity::class.java)
            intent.putExtra("CATEGORY_ID", categoryID)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotesByCategory(categoryID))
    }
}
