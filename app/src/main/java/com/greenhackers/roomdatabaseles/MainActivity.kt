package com.greenhackers.roomdatabaseles

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            val i = Intent(this@MainActivity, AddActivity::class.java)
            i.putExtra("edit",false)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getNotes() {
        class GetNotes : AsyncTask<Void, Void, List<Note>>() {
            override fun doInBackground(vararg params: Void?): List<Note>? {
                return DatabaseClient.getInstance(applicationContext)
                    .ourDatabase
                    .noteDao()
                    .getAllNotes()
            }

            override fun onPostExecute(result: List<Note>?) {
                super.onPostExecute(result)
                val noteAdapter = NoteAdapter(
                    result as ArrayList<Note>,
                    { vh, p ->
                        //delete
                        val dia = AlertDialog.Builder(this@MainActivity)
                        dia.setTitle("Are you sure?")
                        dia.setPositiveButton("Ok") { _, _ ->
                            val note = Note(vh.id!!, vh.title, vh.desc, vh.date!!)
                            deleteNote(note)
                        }
                        dia.setNegativeButton("Cancel") { _, _ ->

                        }
                        dia.create().show()
                    },
                    { vh, p ->
                        //edit
                        val i = Intent(this@MainActivity,AddActivity::class.java)
                        i.putExtra("edit",true)
                        i.putExtra("id",vh.id)
                        i.putExtra("title",vh.title)
                        i.putExtra("desc",vh.desc)
                        startActivity(i)

                    },{
                        vh,p->
                        val dia = AlertDialog.Builder(this@MainActivity)
                        dia.setTitle("Are you sure?")
                        dia.setPositiveButton("Ok") { _, _ ->
                            val note = Note(vh.id!!, vh.title, vh.desc, vh.date!!)
                            deleteNote(note)
                        }
                        dia.setNegativeButton("Cancel") { _, _ ->

                        }
                        dia.create().show()
                    })
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter = noteAdapter
            }

        }
        GetNotes().execute()
    }

    private fun deleteNote(note: Note) {
        class DeleteNote : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                DatabaseClient.getInstance(this@MainActivity)
                    .ourDatabase
                    .noteDao()
                    .delete(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                t("Delete Successfully")
                getNotes()

            }
        }
        DeleteNote().execute()
    }

    override fun onResume() {
        super.onResume()
        getNotes()
    }
}

fun AppCompatActivity.t(str: String) {
    Toast.makeText(applicationContext, str, Toast.LENGTH_LONG).show()
}