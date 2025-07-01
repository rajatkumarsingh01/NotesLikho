package com.example.noteslikho

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun writeNotes(notes: Notes)

    @Query("SELECT * FROM notepad")
     fun getNotes():Flow<List<Notes?>>

     @Update
    suspend fun updateNotes(notes: Notes)


    @Delete
    suspend fun deleteNotes(notes: Notes)
}