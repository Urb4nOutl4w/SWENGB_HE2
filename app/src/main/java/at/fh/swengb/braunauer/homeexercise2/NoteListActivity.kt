package at.fh.swengb.braunauer.homeexercise2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var myDb: NoteRoomDatabase
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var user : UserAndNotes

    companion object {
        val EXTRA_NOTE_ID = "NOTE_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        myDb = NoteRoomDatabase.getDatabase(applicationContext)

        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter({
            //Toast.makeText(this, "Note clicked: ${it.title}", Toast.LENGTH_SHORT).show()
            val implicitIntent = Intent(this, AddNoteActivity::class.java)
            implicitIntent.putExtra(EXTRA_NOTE_ID, it.id)
            startActivity(implicitIntent)
        },{
            //Toast.makeText(this, "Long clicked: ${it.title}", Toast.LENGTH_SHORT).show()
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Delete Note")
            dialogBuilder.setMessage("Are you sure you want to delete the Note ${it.title}?")
            dialogBuilder.setPositiveButton("Yes") { _, _ ->
                myDb.noteDao.delete(it)
                onResume()
            }
            dialogBuilder.setNegativeButton("No", null)
            dialogBuilder.show()
        })
        noteRecyclerView.adapter = noteAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.logout -> {
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val nameStr = sharedPreferences.getString("name", "")
        user = myDb.userDao.findUserAndNotes(nameStr)

        headerText.text = "Notes for ${user.user.name}, ${user.user.age}"

        noteAdapter.updateData(user.notes)
    }

    fun addNote(v: View){
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }

    fun logOut(){
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("name", null).apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}
