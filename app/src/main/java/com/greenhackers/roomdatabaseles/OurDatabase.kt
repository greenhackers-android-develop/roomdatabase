package com.greenhackers.roomdatabaseles

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version = 2)
abstract class OurDatabase :RoomDatabase(){
    abstract fun noteDao():NoteDao
}