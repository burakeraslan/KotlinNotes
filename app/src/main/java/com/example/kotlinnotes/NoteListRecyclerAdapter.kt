package com.example.kotlinnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteListRecyclerAdapter(val noteList: ArrayList<HashMap<String, String>>): RecyclerView.Adapter<NoteListRecyclerAdapter.NoteListViewHolder>() {
    class NoteListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return NoteListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        val textViewNoteHeader = holder.itemView.findViewById<TextView>(R.id.textViewNoteHeader)
        val textViewNoteDescription = holder.itemView.findViewById<TextView>(R.id.textViewNoteDescription)

        textViewNoteHeader.text = noteList[position]["header"]
        textViewNoteDescription.text = noteList[position]["description"]
    }

}