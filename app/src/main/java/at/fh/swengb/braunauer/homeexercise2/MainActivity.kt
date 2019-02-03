package at.fh.swengb.braunauer.homeexercise2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myDb: NoteRoomDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDb = NoteRoomDatabase.getDatabase(applicationContext)

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        if (sharedPreferences.contains("name") == false) {
            sharedPreferences.edit().putString("name", null).apply()
        }

        val nameStr: String? = sharedPreferences.getString("name", null)
        if (nameStr != null) {
            val intent = Intent(this, NoteListActivity::class.java)
            startActivity(intent)
            this.finish()
        } else{
            nameText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    val name = nameText.text.toString()
                    val user = myDb.userDao.findUser(name)
                    if (user != null){
                        ageText.setText(user.age)
                    } else {
                        ageText.setText(null)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            })
        }
    }

    fun saveCredentials(v: View){

        val nameStr = nameText.text.toString()
        val ageStr = ageText.text.toString()

        if (nameStr.isEmpty() ||
            ageStr.isEmpty()){
            val text = "Please enter Name and Age!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            return
        }

        val user = myDb.userDao.findUser(nameStr)
        val newUser = User(nameStr,ageStr)

        if (user == null) {
            myDb.userDao.insert(newUser)
        } else if (user.age != ageStr){
            myDb.userDao.update(newUser)
        }

        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("name", nameStr).apply()

        val intent = Intent(this, NoteListActivity::class.java)
        startActivity(intent)

        this.finish()
    }
}
