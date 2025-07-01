package com.example.noteslikho

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notepad")
data class Notes(
    @PrimaryKey(autoGenerate =true)
    val id:Int=0,
    val notesWrite:String
)


