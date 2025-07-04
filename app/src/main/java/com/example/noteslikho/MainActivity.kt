package com.example.noteslikho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteslikho.ui.theme.NotesLikhoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao =NotesDB.getDatabase(applicationContext).notesDao()
        val repository =  NotesRepository(dao)
        val viewModelFactory =NotesViewModel.NotesViewModelFactory(repository)
        enableEdgeToEdge()
        setContent {
            NotesLikhoTheme {
                val notesViewmodel:NotesViewModel = viewModel(factory = viewModelFactory)
                NotesScreen(viewModel = notesViewmodel)

            }
        }
    }
}
