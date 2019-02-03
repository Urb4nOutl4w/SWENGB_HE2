package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class UserAndNotes() {
    @Embedded
    lateinit var user: User
    @Relation(entity = NoteEntry::class, entityColumn = "userName", parentColumn = "name")
    lateinit var notes: List<NoteEntry>
}