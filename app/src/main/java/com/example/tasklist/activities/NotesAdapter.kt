package com.example.tasklist.activities

import NotesDataBaseHelper
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Note
import com.example.tasklist.databinding.NoteItemBinding

class NotesAdapter(private var notes: MutableList<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    private val db: NotesDataBaseHelper = NotesDataBaseHelper(context)

    class NoteViewHolder(
        private var binding: NoteItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTextView: TextView = binding.tvTitle
        val contentTextView: TextView = binding.tvContent
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
    }

    override fun getItemCount(): Int = notes.size


    fun refreshData(newNotes: List<Note>) {

        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}


