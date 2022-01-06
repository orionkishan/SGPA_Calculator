package com.example.sgpacalculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class courseAdapter internal constructor(var context: Context, list: ArrayList<String>) : RecyclerView.Adapter<courseAdapter.holder>() {
    var list: ArrayList<String>
    var inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view: View = inflater.inflate(R.layout.courses, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val completeCourseName: String = list[position]
        holder.coursename.text = completeCourseName
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coursename: TextView = itemView.findViewById(R.id.coursename)
    }

    init {
        this.list = list
        inflater = LayoutInflater.from(context)
    }
}
