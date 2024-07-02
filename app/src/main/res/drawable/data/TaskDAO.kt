package drawable.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.core.content.contentValuesOf

class TaskDAO(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null, DATA_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "notes-app.db"
        private const val DATA_VERSION = 1
        private const val TABLE_NAME = "Allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Consulta para crear la tabla
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)

    }

    fun insertNote(note: Note) {
        val db = writableDatabase
        val values = contentValuesOf().apply {
            put(COLUMN_TITLE, note.toString())
            put(COLUMN_CONTENT, note.toString())
        }

        db.insert(TABLE_NAME, null, values)
        db.close()


    }


}

