package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface NoteEntryDao {
    @Insert
    fun insert(note: NoteEntry)

    @Update
    fun update(note: NoteEntry)

    @Query("SELECT * FROM note_entries")
    fun findAllNotes(): List<NoteEntry>
}