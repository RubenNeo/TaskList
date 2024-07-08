package com.example.tasklist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasklist.databinding.ActivityCategoriesBinding


class Categories_activity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    enum class Category(val id: Int) {
        HOME(1),
        BATHROOM(2),
        KITCHEN(3),
        LIVING_ROOM(4),
        OUT_OF_HOUSE(5),
        SHOPPING(6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar las categorías a cada CardView
        binding.Cv1.tag = Category.HOME.id
        binding.Cv2.tag = Category.BATHROOM.id
        binding.Cv3.tag = Category.KITCHEN.id
        binding.Cv4.tag = Category.LIVING_ROOM.id
        binding.Cv5.tag = Category.OUT_OF_HOUSE.id
        binding.Cv6.tag = Category.SHOPPING.id

        // Manejar clics en los CardView
        binding.Cv1.setOnClickListener {
            navigateToAddNotesActivity(it.tag as Int)
        }

        binding.Cv2.setOnClickListener {
            navigateToAddNotesActivity(it.tag as Int)
        }

        binding.Cv3.setOnClickListener {
            navigateToAddNotesActivity(it.tag as Int)
        }

        binding.Cv4.setOnClickListener {
            navigateToAddNotesActivity(it.tag as Int)
        }

        binding.Cv5.setOnClickListener {
            navigateToAddNotesActivity(it.tag as Int)
        }

        binding.Cv6.setOnClickListener {
            navigateToAddNotesActivity(it.tag as Int)
        }
    }

    private fun navigateToAddNotesActivity(categoryId: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("CATEGORY_ID", categoryId)
        startActivity(intent)
    }
}
