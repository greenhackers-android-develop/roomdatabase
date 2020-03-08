package com.greenhackers.roomdatabaseles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(

    private val notes: ArrayList<Note>,
    //for delete on item click
    private val on_delete:(vh:NoteHolder,position:Int)->Unit,
    //for edit note
    private val on_edit:(vh:NoteHolder,position:Int)->Unit



) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]
        with(holder) {
            id = note.uid
            title = note.title
            desc = note.description
            date = note.save_time
            tvTitle.text = note.title
            tvDesc.text = note.description
            tvDate.text = note.save_time
            itemView.iv_delete.setOnClickListener { on_delete(holder,position) }
            itemView.iv_edit.setOnClickListener { on_edit(holder,position) }
        }
    }

    class NoteHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle = v.tv_title
        val tvDesc = v.tv_desc
        val tvDate = v.tv_date

        var id:Long? = 0L
        var title:String? = ""
        var desc:String? = ""
        var date:String? = ""
    }
}