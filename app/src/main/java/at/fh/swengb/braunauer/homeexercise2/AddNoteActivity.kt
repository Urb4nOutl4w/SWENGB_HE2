package at.fh.swengb.braunauer.homeexercise2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var myDb: NoteRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        myDb = NoteRoomDatabase.getDatabase(applicationContext)
    }

    fun saveNote(v: View){

        val titleStr = titleText.text.toString()
        val contentStr = contentText.text.toString()
        val note = NoteEntry(titleStr,contentStr)

        if (titleStr.isEmpty() ||
            contentStr.isEmpty()){
            return
        }

        myDb.noteDao.insert(note)

        this.finish()
    }
}
