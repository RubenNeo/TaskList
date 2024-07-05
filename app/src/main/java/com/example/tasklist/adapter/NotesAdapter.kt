package com.example.tasklist.adapter

import NotesDataBaseHelper
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.update.update_activity
import com.example.tasklist.data.Note
import com.example.tasklist.databinding.NoteItemBinding

class NotesAdapter(private var notes: MutableList<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDataBaseHelper = NotesDataBaseHelper(context)

    class NoteViewHolder(
        private val binding: NoteItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        val titleTextView: TextView = binding.tvTitle
        val contentTextView: TextView = binding.tvContent
        val optionButton: ImageView = binding.ivOptionBottom
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    fun refreshData(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int): Note = notes[position]

    // Funcionalidad de deslizamiento.
    fun enableSwipe(recyclerView: RecyclerView) {
        val swipeHelper = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = getItem(position)
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        // Actualizar Nota
                        val intent = Intent(viewHolder.itemView.context, update_activity::class.java).apply {
                            putExtra("note_id", note.id)
                        }
                        viewHolder.itemView.context.startActivity(intent)
                    }
                    ItemTouchHelper.RIGHT -> {
                        // Eliminar Nota
                        db.deleteNote(note.id)
                        removeItem(position)
                        Toast.makeText(viewHolder.itemView.context, "Note deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - iconLeft!!.intrinsicHeight) / 2

                if (dX > 0) { // Deslizar hacia la derecha (Eliminar)
                    backgroundRight.setBounds(
                        itemView.left,
                        itemView.top,
                        itemView.left + dX.toInt(),
                        itemView.bottom
                    )
                    backgroundRight.draw(c)

                    // Dibujar el icono de eliminar
                    val iconTop = itemView.top + (itemView.height - iconRight!!.intrinsicHeight) / 2
                    val iconBottom = iconTop + iconRight.intrinsicHeight
                    val iconLeftMargin = itemView.left + iconMargin
                    val iconRightMargin = iconLeftMargin + iconRight.intrinsicWidth
                    iconRight.setBounds(iconLeftMargin, iconTop, iconRightMargin, iconBottom)
                    iconRight.draw(c)
                } else if (dX < 0) { // Deslizar hacia la izquierda (Actualizar)
                    backgroundLeft.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    backgroundLeft.draw(c)

                    // Dibujar el icono de actualizar
                    val iconTop = itemView.top + (itemView.height - iconLeft!!.intrinsicHeight) / 2
                    val iconBottom = iconTop + iconLeft.intrinsicHeight
                    val iconLeftMargin = itemView.right - iconMargin - iconLeft.intrinsicWidth
                    val iconRightMargin = itemView.right - iconMargin
                    iconLeft.setBounds(iconLeftMargin, iconTop, iconRightMargin, iconBottom)
                    iconLeft.draw(c)
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            private val iconLeft: Drawable? = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_edit)
            private val iconRight: Drawable? = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
            private val backgroundLeft :ColorDrawable = ColorDrawable(Color.CYAN)
            private val backgroundRight: ColorDrawable = ColorDrawable(Color.RED)
        }

        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.optionButton.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, holder.optionButton)
            popupMenu.inflate(R.menu.note_item_menu)
            // Agregar un listener para manejar las opciones seleccionadas en el menú emergente
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.editOption -> {
                        // Editar nota
                        val intent = Intent(holder.itemView.context, update_activity::class.java).apply {
                            putExtra("note_id", note.id)
                        }
                        holder.itemView.context.startActivity(intent)
                        true
                    }
                    R.id.deleteOption -> {
                        db.deleteNote(note.id)
                        removeItem(holder.adapterPosition)
                        Toast.makeText(holder.itemView.context, "Note deleted", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show() // Mostar menu emergente
        }
    }
}
