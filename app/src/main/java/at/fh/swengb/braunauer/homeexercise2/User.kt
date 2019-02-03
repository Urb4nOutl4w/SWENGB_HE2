package at.fh.swengb.braunauer.homeexercise2

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "user_profiles")
class User(@PrimaryKey
           val name : String = "",
           val age : String){

}
