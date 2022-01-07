package com.example.sgpacalculator

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.annotations.NotNull
import java.util.*


class courseAdapter internal constructor(context: Context,var branch: String,var sem:String, list: ArrayList<String>) : RecyclerView.Adapter<courseAdapter.holder>() {

    lateinit var list: ArrayList<String>
    lateinit var inflater: LayoutInflater
    var gradeSum: Double = 0.0
    var totalCredits: Double = 0.0
    lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view: View = inflater.inflate(R.layout.courses, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val completeCourseName: String = list[position]
        holder.coursename.text = completeCourseName

//        var calculate: Button = holder.itemView.findViewById(R.id.calculateButton)


    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coursename: TextView = itemView.findViewById(R.id.coursename)
        var check: CheckBox = itemView.findViewById(R.id.checkBox)

        FirebaseApp.initializeApp(context);
        database=FirebaseDatabase.getInstance();
        ref= database!!.getReference("Branch").child(branch).child(holder.coursename.text.toString());
        ref?.addValueEventListener( object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        var credits: Double = snap.value.toString().toDouble()
                        totalCredits += credits
                        if (holder.check.isChecked) {
                            gradeSum += (credits * 10)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Cancelled", error.details)
            }
        })
    }

    init {
        this.list = list
        inflater = LayoutInflater.from(context)
        this.context=context
    }
}

