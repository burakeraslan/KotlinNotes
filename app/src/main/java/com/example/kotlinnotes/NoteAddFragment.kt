package com.example.kotlinnotes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.Navigation

class NoteAddFragment : Fragment() {
    private lateinit var editTextNoteHeader: EditText
    private lateinit var editTextNoteDescription: EditText
    private lateinit var doneButton: View
    private lateinit var noteId: String
    private lateinit var noteHeader: String
    private lateinit var noteDescription: String
    private var isEdit: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_add, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNoteHeader = view.findViewById(R.id.editTextNoteHeader)
        editTextNoteDescription = view.findViewById(R.id.editTextNoteDescription)
        doneButton = view.findViewById(R.id.doneButton)

        editTextNoteHeader.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextNoteHeader, InputMethodManager.SHOW_IMPLICIT)

        doneButton.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            editTextNoteHeader.clearFocus()
            editTextNoteDescription.clearFocus()
        }

        val backButton = view.findViewById<View>(R.id.backButton)
        backButton.setOnClickListener {
            handleSaveNote()
            val action = NoteAddFragmentDirections.actionNoteAddFragmentToNoteListFragment()
            Navigation.findNavController(view).navigate(action)
        }

        arguments?.let {
            val safeArgs = NoteAddFragmentArgs.fromBundle(it)
            isEdit = safeArgs.id != ""
            noteId = safeArgs.id
            noteHeader = safeArgs.header
            noteDescription = safeArgs.description
            editTextNoteHeader.setText(noteHeader)
            editTextNoteDescription.setText(noteDescription)
        }

    }

    private fun handleSaveNote() {
        try {
            val db = requireActivity().applicationContext.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, header TEXT, description TEXT)")
            val header = editTextNoteHeader.text.toString()
            val description = editTextNoteDescription.text.toString()
            if (header.isEmpty() && description.isEmpty()) {
                return
            } else {
                if (isEdit) {
                    val id: Int = noteId.toInt()
                    db.execSQL("UPDATE notes SET header = '$header', description = '$description' WHERE id = $id")
                } else {
                    db.execSQL("INSERT INTO notes (header, description) VALUES ('$header', '$description')")
                }
                println("Note saved successfully!")
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

}