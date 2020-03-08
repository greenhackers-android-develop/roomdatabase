package com.greenhackers.roomdatabaseles

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NoteDao {
    @Insert
    fun insert(note:Note):Long

    @Query("select * from note order by time desc")
    fun getAllNotes():List<Note>

    @Delete
    fun delete(note:Note)

}