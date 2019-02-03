package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.*

@Dao
interface NoteEntryDao {
    @Insert
    fun insert(note: NoteEntry)

    @Update
    fun update(note: NoteEntry)

    @Query("SELECT * FROM note_entries")
    fun findAllNotes(): List<NoteEntry>

    @Query("SELECT * FROM note_entries WHERE id = :id")
    fun findNoteById(id: Long): NoteEntry

    @Query("UPDATE note_entries SET title = :title, content = :content WHERE id = :id")
    fun updateNote(id: Long, title: String, content: String)

    @Delete
    fun delete(note: NoteEntry)
}