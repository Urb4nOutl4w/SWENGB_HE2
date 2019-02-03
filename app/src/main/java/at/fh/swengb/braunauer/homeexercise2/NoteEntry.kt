package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note_entries",
    indices = [Index("userName")],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["name"],
        childColumns = ["userName"],
        onDelete = ForeignKey.CASCADE
    )])
class NoteEntry(val title : String, val content : String, val userName : String){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}