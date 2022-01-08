package com.example.sgpacalculator

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.*


class courseAdapter internal constructor(context: Context,var branch: String,var sem:String, list: ArrayList<courses>) : RecyclerView.Adapter<courseAdapter.holder>() {

    lateinit var list: ArrayList<courses>
    lateinit var inflater: LayoutInflater
    var gradeSum: Double = 0.0
    var totalCredits: Double = 0.0
    lateinit var context: Context
    var map : HashMap<String, Pair<Double,String>> = HashMap<String, Pair<Double,String>> ()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view: View = inflater.inflate(R.layout.courses, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val course: courses = list[position]
        holder.coursename.text = course.getCoursename()
        holder.check.setOnCheckedChangeListener(null)

        course.getisChecked()?.let { holder.check.setChecked(it) }


        holder.check.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                //set your object's last status
                course.setisChecked(isChecked)
                if(holder.check.isChecked){
                    course.getCredits()?.let { addCredit(it) }
                    course.getCoursename()?.let { map.put(it,
                        Pair(course.getCredits(),holder.autoCompleteTextViewGrade.text.toString()) as Pair<Double, String>
                    ) }
                }
                else{
                    course.getCredits()?.let { subtractCredit(it) }
                    map.remove(course.getCoursename())
                }
            }
        })


        holder.autoCompleteTextViewGrade.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                course.getCoursename()?.let { map.put(it,
                    Pair(course.getCredits(),holder.autoCompleteTextViewGrade.text.toString()) as Pair<Double, String>
                ) }
                course.setGrade(holder.autoCompleteTextViewGrade.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addCredit(credit:Double) {
        totalCredits += credit
    }

    fun subtractCredit(credit: Double){
        totalCredits-= credit
    }



    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coursename: TextView = itemView.findViewById(R.id.coursename)
        var check: CheckBox = itemView.findViewById(R.id.checkBox)
        val autoCompleteTextViewGrade = itemView.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewGrade)

        init {
            val grade = itemView.resources.getStringArray(R.array.Grades)
            val gradeAdapter = ArrayAdapter(context, R.layout.dropdown_items, grade)
            autoCompleteTextViewGrade.setAdapter(gradeAdapter)
        }
    }

    init {
        this.list = list
        inflater = LayoutInflater.from(context)
        FirebaseApp.initializeApp(context);
        this.context=context
    }
}

