package com.example.sgpacalculator

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.io.File
import java.util.*


class courseAdapter internal constructor(context: Context,var branch: String,var sem:String, list: ArrayList<courses>) : RecyclerView.Adapter<courseAdapter.holder>() {

    lateinit var list: ArrayList<courses>
    lateinit var inflater: LayoutInflater
    var gradeSum: Double = 0.0
    var totalCredits: Double = 0.0
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view: View = inflater.inflate(R.layout.courses, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val course: courses = list[position]
        holder.coursename.text = course.getCoursename()
//        course.getCredits()?.let { totalCredit(it) }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun totalCredit(credit:Double) {
        totalCredits += credit
    }

    fun subtractCredit(credit: Double){
        totalCredits-= credit
    }



    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coursename: TextView = itemView.findViewById(R.id.coursename)
        var check: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            check.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!isChecked) {
                    for (course in list)
                    {
                        if(course.getCoursename()?.equals(coursename)==true)
                            course.getCredits()?.let { subtractCredit(it) }
                    }
                }
                else
                {
                    for (course in list)
                    {
                        if(course.getCoursename()?.equals(coursename)==true)
                            course.getCredits()?.let { totalCredit(it) }
                    }
                }
            }

        }
    }

    init {
        this.list = list
        inflater = LayoutInflater.from(context)
        FirebaseApp.initializeApp(context);
        this.context=context
    }
}

