package com.example.noteslikho

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesDatabaseTest {

    private lateinit var db: NotesDB
    private lateinit var dao: NotesDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NotesDB::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.notesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadNote() = runBlocking {
        val note = Notes(notesWrite = "Room Test")
        dao.writeNotes(note)
        val allNotes = dao.getNotes().first()
        assertTrue(allNotes.contains(note))
    }
}
