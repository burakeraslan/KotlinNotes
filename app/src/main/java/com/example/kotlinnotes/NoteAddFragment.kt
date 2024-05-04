package com.example.kotlinnotes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class NoteAddFragment : Fragment() {
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

        val editTextNoteHeader = view.findViewById<EditText>(R.id.editTextNoteHeader)
        editTextNoteHeader.requestFocus()
        val editTextNoteDescription = view.findViewById<EditText>(R.id.editTextNoteDescription)

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editTextNoteHeader, InputMethodManager.SHOW_IMPLICIT)

        val doneButton = view.findViewById<View>(R.id.doneButton)
        doneButton.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            editTextNoteHeader.clearFocus()
            editTextNoteDescription.clearFocus()
        }

        val backButton = view.findViewById<View>(R.id.backButton)
        backButton.setOnClickListener {
            println(
                editTextNoteHeader.text.toString() + " " + editTextNoteDescription.text.toString()
            )
        }

    }
}