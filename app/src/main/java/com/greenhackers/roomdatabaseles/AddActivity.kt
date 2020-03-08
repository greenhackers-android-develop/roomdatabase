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

        val isEdited = intent?.extras?.get("edit") as Boolean
        if (isEdited) {
            bt_add.text = "Update"
            val id = intent?.extras?.get("id") as Long
            val title = intent?.extras?.get("title") as String
            val desc = intent?.extras?.get("desc") as String

            et_title.setText(title)
            et_desc.setText(desc)

            bt_add.setOnClickListener {
                val title = et_title.text.toString()
                val desc = et_desc.text.toString()

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                val formattedDate = current.format(formatter)
                updateNote(id,title,desc,formattedDate)
            }
        } else {
            bt_add.setOnClickListener {
                val title = et_title.text.toString()
                val desc = et_desc.text.toString()

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                val formattedDate = current.format(formatter)

                addNote(title, desc, formattedDate)

            }
        }
    }

    private fun addNote(title: String, desc: String, date: String) {

        class SaveNote : AsyncTask<Void, Void, Long>() {
            override fun doInBackground(vararg params: Void?): Long? {
                val note = Note(title = title, description = desc, save_time = date)
                val id = DatabaseClient.getInstance(applicationContext)
                    .ourDatabase
                    .noteDao()
                    .insert(note)
                return id
            }

            override fun onPostExecute(result: Long?) {
                super.onPostExecute(result)
                if (result != null) {
                    finish()
                    t("Save Successfully")
                } else t("Save Error")

            }

        }
        SaveNote().execute()
    }

    private fun updateNote(id: Long, title: String, desc: String, date: String) {

        class SaveNote : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                val note = Note(uid = id, title = title, description = desc, save_time = date)
                DatabaseClient.getInstance(applicationContext)
                    .ourDatabase
                    .noteDao()
                    .update(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                finish()
                t("Updated Successfully")


            }

        }
        SaveNote().execute()
    }

}
