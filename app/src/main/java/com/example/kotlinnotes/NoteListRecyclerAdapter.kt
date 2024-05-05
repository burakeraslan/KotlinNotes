package com.example.kotlinnotes

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class NoteListRecyclerAdapter(private val noteList: ArrayList<HashMap<String, String>>): RecyclerView.Adapter<NoteListRecyclerAdapter.NoteListViewHolder>() {
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
        val linearLayoutRecyclerRow = holder.itemView.findViewById<View>(R.id.linearLayoutRecyclerRow)

        textViewNoteHeader.text = noteList[position]["header"]
        textViewNoteDescription.text = noteList[position]["description"]

        linearLayoutRecyclerRow.setOnClickListener {
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteAddFragment(
                noteList[position]["id"] ?: "",
                noteList[position]["header"] ?: "",
                noteList[position]["description"] ?: ""
            )
            Navigation.findNavController(it).navigate(action)
        }

        linearLayoutRecyclerRow.setOnLongClickListener{
            val alertDialog = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle("Delete Note")
            alertDialog.setMessage("Are you sure you want to delete this note?")
            alertDialog.setPositiveButton("Delete") { _, _ ->
                val db = holder.itemView.context.applicationContext.openOrCreateDatabase("notes", android.content.Context.MODE_PRIVATE, null)
                db.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, header TEXT, description TEXT)")
                db.execSQL("DELETE FROM notes WHERE id = ${noteList[position]["id"]}")
                Toast.makeText(holder.itemView.context, "Note deleted", Toast.LENGTH_SHORT).show()
                noteList.removeAt(position)
                notifyDataSetChanged()
            }
            alertDialog.setNegativeButton("No") { _, _ -> }
            val dialog = alertDialog.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.RED)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.BLACK)
            }
            dialog.show()

            return@setOnLongClickListener true
        }
    }
}