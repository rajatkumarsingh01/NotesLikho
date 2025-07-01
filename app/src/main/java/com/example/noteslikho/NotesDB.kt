package com.example.noteslikho

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Notes::class], version = 1)
abstract class NotesDB :RoomDatabase(){

    abstract fun notesDao():NotesDao

    companion object{

        @Volatile
        private var INSTANCE:NotesDB?=null

        fun getDatabase(context: Context) :NotesDB{
            return INSTANCE?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    NotesDB::class.java,
                    "notes_db").build().also {
                        INSTANCE=it
                }
            }
        }


    }




}