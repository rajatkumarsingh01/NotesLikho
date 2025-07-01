package com.example.noteslikho

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.noteslikho.Notes


class NotesViewModel (private val repository: NotesRepository):ViewModel() {

    val notes: Flow<List<Notes?>>  = repository.allNotes


    fun addNotes(note:Notes){
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun updateNotes(note: Notes){
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun deleteNotes(note:Notes){
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotesViewModel(repository) as T
        }

    }

}