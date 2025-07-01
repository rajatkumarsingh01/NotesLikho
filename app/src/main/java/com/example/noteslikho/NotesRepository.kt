package com.example.noteslikho

import kotlinx.coroutines.flow.Flow

class NotesRepository(private val dao :NotesDao) {
    val allNotes:Flow<List<Notes?>> =dao.getNotes()

    suspend fun insert(notes:Notes){
        dao.writeNotes(notes)
    }

    suspend fun update(notes: Notes){
        dao.updateNotes(notes)
    }

    suspend fun delete(notes: Notes){
        dao.deleteNotes(notes)
    }

}