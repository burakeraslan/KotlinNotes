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
    lateinit var editTextNoteHeader: EditText
    lateinit var editTextNoteDescription: EditText
    lateinit var doneButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_add, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNoteHeader = view.findViewById<EditText>(R.id.editTextNoteHeader)
        editTextNoteDescription = view.findViewById<EditText>(R.id.editTextNoteDescription)
        doneButton = view.findViewById<View>(R.id.doneButton)

        editTextNoteHeader?.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextNoteHeader, InputMethodManager.SHOW_IMPLICIT)

        doneButton?.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            editTextNoteHeader?.clearFocus()
            editTextNoteDescription?.clearFocus()
        }

        val backButton = view.findViewById<View>(R.id.backButton)
        backButton.setOnClickListener {
            handleSaveNote()
            Navigation.findNavController(view).navigate(R.id.action_noteAddFragment_to_noteListFragment)
        }

    }

    fun handleSaveNote() {
        try {
            val db = requireActivity().applicationContext.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, header TEXT, description TEXT)")
            val header = editTextNoteHeader?.text.toString()
            val description = editTextNoteDescription?.text.toString()
            if (header.isEmpty() && description.isEmpty()) {
                return
            } else {
                db.execSQL("INSERT INTO notes (header, description) VALUES ('$header', '$description')")
                println("Note saved successfully!")
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

}