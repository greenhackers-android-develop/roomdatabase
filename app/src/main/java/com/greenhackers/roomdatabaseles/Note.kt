package com.greenhackers.roomdatabaseles

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var uid:Long = 0L,

    @ColumnInfo(name = "title")
    var title:String? = "",

    @ColumnInfo(name = "desc")
    var description:String? = "",

    @ColumnInfo(name = "time")
    var save_time:String = ""
)
