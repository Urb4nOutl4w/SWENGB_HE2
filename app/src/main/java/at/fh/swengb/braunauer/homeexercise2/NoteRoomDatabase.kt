package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [User::class, NoteEntry::class], version = 3)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract val noteDao: NoteEntryDao
    abstract val userDao: UserDao

    companion object {
        fun getDatabase(context: Context): NoteRoomDatabase {
            return Room.databaseBuilder(context, NoteRoomDatabase::class.java, "note-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}