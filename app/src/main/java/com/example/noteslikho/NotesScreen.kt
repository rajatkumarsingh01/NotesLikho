package com.example.noteslikho

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val notesList by viewModel.notes.collectAsState(initial = emptyList())
    var newNote by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {


        Image(
            painter = painterResource(id = R.drawable.notesbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "NotesLikho",
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                style = TextStyle(fontSize = 32.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = newNote,
                maxLines = 10,
                onValueChange = { newNote = it },
                label = { Text("Write a note") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (newNote.isNotBlank()) {
                        viewModel.addNotes(Notes(notesWrite = newNote))
                        newNote =""
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC1E3),
                    contentColor = Color.Black
                )
            ) {
                Text("Add Note")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "View Your Notes",
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notesList.filterNotNull()) { note ->
                    NoteCard(
                        note = note,
                        onDelete = { viewModel.deleteNotes(note) },
                        onEdit = { viewModel.updateNotes(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Notes, onDelete: () -> Unit, onEdit: (Notes) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    // View full note dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row {
                    Button(onClick = {
                        showDialog = false
                        showEditDialog = true
                    }) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onDelete()
                        showDialog = false
                    }) {
                        Text("Delete")
                    }
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            },
            title = { Text("Note Details") },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cardbg),
                        contentDescription = "Note Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Text(
                        text = note.notesWrite,
                        style = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        )
    }

    // Edit note dialog
    if (showEditDialog) {
        var editedText by remember { mutableStateOf(note.notesWrite) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            confirmButton = {
                Button(onClick = {
                    if (editedText.isNotBlank()) {
                        onEdit(note.copy(notesWrite = editedText))
                        showEditDialog = false
                    }
                }) {
                    Text("Update")
                }
            },
            dismissButton = {
                Button(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Edit Note") },
            text = {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    label = { Text("Edit your note") }
                )
            }
        )
    }

    // One-liner preview card
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { showDialog = true }
    ) {
        Box(modifier = Modifier.height(60.dp)) {
            Image(
                painter = painterResource(id = R.drawable.cardbg), // Replace with your image
                contentDescription = "Card Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = note.notesWrite,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = onDelete,

            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
    }

}
