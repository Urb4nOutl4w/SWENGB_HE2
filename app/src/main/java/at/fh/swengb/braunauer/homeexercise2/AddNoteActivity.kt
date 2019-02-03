package at.fh.swengb.braunauer.homeexercise2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import at.fh.swengb.braunauer.homeexercise2.NoteListActivity.Companion.EXTRA_NOTE_ID
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.note_item.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var myDb: NoteRoomDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        myDb = NoteRoomDatabase.getDatabase(applicationContext)

        val noteId = getIntent().getLongExtra(EXTRA_NOTE_ID, -1)

        if (noteId >= 0) {
            val note = myDb.noteDao.findNoteById(noteId)
            titleText.setText(note.title)
            contentText.setText(note.content)
        }
    }

    fun saveNote(v: View){
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", null)
        val titleStr = titleText.text.toString()
        val contentStr = contentText.text.toString()
        val note = NoteEntry(titleStr,contentStr,userName)
        val noteId = getIntent().getLongExtra(EXTRA_NOTE_ID, -1)

        if (titleStr.isEmpty() ||
            contentStr.isEmpty()){
            val text = "Please enter Title and Content!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            return
        }
        if (noteId >= 0) {
            myDb.noteDao.updateNote(noteId, titleStr, contentStr)
        }
        else {
            myDb.noteDao.insert(note)
        }


        this.finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.share -> {
                shareContent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun shareContent (){
        val content = contentText.text.toString()
        if (content == "") {
            val text = "You have to enter some Content if you want to share it!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }else{
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, content) // Text der geshared werden soll
            intent.type = "text/plain"
            val chooserIntent = Intent.createChooser(intent, "Select an App you want to share with")
            startActivity(chooserIntent)
        }

    }
}
