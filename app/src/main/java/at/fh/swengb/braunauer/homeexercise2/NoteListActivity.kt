package at.fh.swengb.braunauer.homeexercise2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var myDb: NoteRoomDatabase
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        writeHeader()

        myDb = NoteRoomDatabase.getDatabase(applicationContext)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter()
        noteRecyclerView.adapter = noteAdapter


    }

    override fun onResume() {
        super.onResume()

        val dbItems = myDb.noteDao.findAllNotes()
        noteAdapter.updateData(dbItems)
    }

    fun addNote(v: View){
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }

    fun writeHeader(){
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val nameStr = sharedPreferences.getString("name", null)
        val ageStr = sharedPreferences.getString("age", null)

        headerText.text = "Notes for ${nameStr}, ${ageStr}"
    }

}
