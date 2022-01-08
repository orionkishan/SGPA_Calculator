package com.example.sgpacalculator

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view: View = inflater.inflate(R.layout.courses, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val course: courses = list[position]
        holder.coursename.text = course.getCoursename()
//        course.getCredits()?.let { totalCredit(it) }

        //in some cases, it will prevent unwanted situations
        holder.check.setOnCheckedChangeListener(null)

        course.getisChecked()?.let { holder.check.setChecked(it) }

        holder.check.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                //set your object's last status
                course.setisChecked(isChecked)
                if(holder.check.isChecked){
                    course.getCredits()?.let { totalCredit(it) }
                }
                else{
                    course.getCredits()?.let { subtractCredit(it) }
                }
            }
        })
        Log.i("Course is",course.getCoursename().toString())
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

//        init {
//            check.setOnClickListener(View.OnClickListener { v: View? ->
//                check.setOn
//                val courselist: courses = list[absoluteAdapterPosition]
//                courselist.setisChecked(!courselist.isChecked)
//                notifyItemChanged(absoluteAdapterPosition)
//                Log.i("Course is ",courselist.getCoursename().toString())
//            })
//            check.setOnClickListener(View.OnClickListener {
//                fun onClick(view:View){
//                    if(check.isChecked){
//                        for (course in list)
//                        {
//                            if(course.getCoursename()?.equals(coursename)==true)
//                                course.getCredits()?.let { totalCredit(it) }
//                        }
//                    }
//                    else{
//                        for (course in list)
//                        {
//                            if(course.getCoursename()?.equals(coursename)==true)
//                                course.getCredits()?.let { subtractCredit(it) }
//                        }
//                    }
//                }
//            })
//            check.setOnCheckedChangeListener(null)
//            check.isChecked = cours.isSelected();
//            check.setOnCheckedChangeListener { buttonView, isChecked ->
//                val courselist: courses = list[bindingAdapterPosition]
//                courselist.setisChecked(isChecked)
//                notifyItemChanged(bindingAdapterPosition)
//                if(courselist.getisChecked()==true){
//                    courselist.getCredits()?.let { totalCredit(it) }
//                }
//                else{
//                    courselist.getCredits()?.let {subtractCredit(it)}
//                }
//                Log.i("Course is ",courselist.getCoursename().toString())
//            }
//        }
    }

    init {
        this.list = list
        inflater = LayoutInflater.from(context)
        FirebaseApp.initializeApp(context);
        this.context=context
    }
}

