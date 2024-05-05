package com.example.kotlinnotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class NoteListFragment : Fragment() {
    private var noteList = ArrayList<HashMap<String, String>>()
    private var noteItemMap = HashMap<String, String>()
    private lateinit var noteListRecyclerAdapter: NoteListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        noteListRecyclerAdapter = NoteListRecyclerAdapter(noteList)
        recyclerView?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView?.adapter = noteListRecyclerAdapter

        val addButton = view.findViewById<View>(R.id.addButton)
        addButton?.setOnClickListener {
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteAddFragment("", "", "")
            Navigation.findNavController(it).navigate(action)
        }
        getData()

    }

    private fun getData() {
        try {
            val db = requireActivity().applicationContext.openOrCreateDatabase("notes", android.content.Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, header TEXT, description TEXT)")
            val cursor = db.rawQuery("SELECT * FROM notes", null)
            val idIndex = cursor.getColumnIndex("id")
            val headerIndex = cursor.getColumnIndex("header")
            val descriptionIndex = cursor.getColumnIndex("description")
            noteList.clear()
            while (cursor.moveToNext()) {
                noteItemMap = HashMap()
                noteItemMap["id"] = cursor.getString(idIndex)
                noteItemMap["header"] = cursor.getString(headerIndex)
                noteItemMap["description"] = cursor.getString(descriptionIndex)
                noteList.add(noteItemMap)
            }
            noteListRecyclerAdapter.notifyDataSetChanged()
            cursor.close()
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}