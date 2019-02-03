package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user_profiles WHERE name = :name")
    fun findUser(name : String): User?

    @Query("SELECT * FROM user_profiles WHERE name = :name")
    fun findUserAndNotes(name : String?): UserAndNotes
}