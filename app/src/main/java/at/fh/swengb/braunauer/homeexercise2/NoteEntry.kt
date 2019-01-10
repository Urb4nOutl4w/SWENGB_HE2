package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note_entries")
class NoteEntry(val title : String, val content : String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}