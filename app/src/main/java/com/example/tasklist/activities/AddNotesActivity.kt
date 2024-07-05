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

        // Obtener el ID de la categoría del Intent
        val categoryId = intent.getIntExtra("CATEGORY_ID", -1)

        binding.saveButton.setOnClickListener {
            val title = binding.tittleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if (categoryId != -1) {
                // Crear una nueva nota con la categoría especificada
                val note = Note(
                    id = 0, // El ID se establecerá automáticamente en la base de datos
                    title = title,
                    content = content,
                    category = categoryId
                )

                // Insertar la nota en la base de datos
                db.insertNote(note)

                // Mostrar mensaje de confirmación
                Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()

                // Finalizar la actividad actual y regresar a la anterior
                finish()
            } else {
                // Si el categoryId no es válido, mostrar un mensaje de error
                Toast.makeText(this, "Invalid Category Id", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
