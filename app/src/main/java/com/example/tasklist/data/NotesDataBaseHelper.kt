import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tasklist.data.Note

class NotesDataBaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATA_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATA_VERSION = 4
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_CATEGORY = "category"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT ,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT , $COLUMN_CATEGORY INTEGER)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
        }

        fun insertNote(note: Note) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_TITLE, note.title)
                put(COLUMN_CONTENT, note.content)
                put(COLUMN_CATEGORY, note.category)
            }
            db.insert(TABLE_NAME, null, values)
            db.close()
        }

        fun getAllNotes(): List<Note> {
            val noteListe = mutableListOf<Note>()
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_NAME"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                val category = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))


                val note = Note(id, title, content, category)
                noteListe.add(note)
            }

            cursor.close()
            db.close()
            return noteListe
        }

        fun getAllNotesByCategory(categoryId: Int): List<Note> {
            val noteListe = mutableListOf<Note>()
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_CATEGORY = $categoryId"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
                val category = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))


                val note = Note(id, title, content, category)
                noteListe.add(note)
            }

            cursor.close()
            db.close()
            return noteListe
        }

        fun updateNote(note: Note) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_TITLE, note.title)
                put(COLUMN_CONTENT, note.content)
                put(COLUMN_CATEGORY, note.category)
            }
            val whereClause = "$COLUMN_ID = ?"
            val WhereArgs = arrayOf(note.id.toString())
            db.update(TABLE_NAME, values, whereClause, WhereArgs)
            db.close()
        }


        fun getNoteById(noteID: Int): Note {
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
            val cursor = db.rawQuery(query, null)
            cursor.moveToFirst()

            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            cursor.close()
            db.close()
            return Note(
               id = id,
              title = title,
                content = content,
                category = 0)


        }

        fun deleteNote(noteId: Int) {
            val db = writableDatabase
            val whereClause = "$COLUMN_ID = ?"
            val WhereArgs = arrayOf(noteId.toString())
            db.delete(TABLE_NAME, whereClause, WhereArgs)
            db.close()

        }
    }
