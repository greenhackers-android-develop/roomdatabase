package com.greenhackers.roomdatabaseles

import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_add.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        bt_cancel.setOnClickListener { finish() }
        bt_add.setOnClickListener {
            val title = et_title.text.toString()
            val desc = et_desc.text.toString()

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formattedDate = current.format(formatter)

            addNote(title,desc,formattedDate)

        }
    }

    private fun addNote(title:String,desc:String,date:String){

        class SaveNote:AsyncTask<Void,Void,Long>(){
            override fun doInBackground(vararg params: Void?): Long? {
                val note = Note(title = title,description = desc,save_time = date)
                val id = DatabaseClient.getInstance(applicationContext)
                    .ourDatabase
                    .noteDao()
                    .insert(note)
                return id
            }

            override fun onPostExecute(result: Long?) {
                super.onPostExecute(result)
                if (result!=null){
                    finish()
                    t("Save Successfully")
                }else t("Save Error")

            }

        }
        SaveNote().execute()
    }
}
