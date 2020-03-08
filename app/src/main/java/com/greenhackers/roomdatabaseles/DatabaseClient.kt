package com.greenhackers.roomdatabaseles

import android.content.Context
import androidx.room.Room

class DatabaseClient private constructor(ctx:Context){
    val ourDatabase:OurDatabase = Room
        .databaseBuilder(ctx,OurDatabase::class.java,"Something")
        .build()
    companion object{
        private var databaseInstance:DatabaseClient? = null

        fun getInstance(context: Context):DatabaseClient{
            if (databaseInstance==null){
                databaseInstance = DatabaseClient(context)
            }
            return databaseInstance!!
        }
    }
}



