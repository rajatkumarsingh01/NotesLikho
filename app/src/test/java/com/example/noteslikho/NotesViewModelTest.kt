package com.example.noteslikho

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class NotesViewModelTest {

    @Mock
    lateinit var notesRepository: NotesRepository

    private lateinit var viewModel: NotesViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = NotesViewModel(notesRepository)
    }

    @Test
    fun `addNotes should insert note`() = runTest {
        val note = Notes(notesWrite = "Test Note")
        viewModel.addNotes(note)
        verify(notesRepository).insert(note)
    }
}
