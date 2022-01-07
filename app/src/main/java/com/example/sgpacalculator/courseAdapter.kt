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


class courseAdapter internal constructor(context: Context,var branch: String,var sem:String, list: ArrayList<String>) : RecyclerView.Adapter<courseAdapter.holder>() {

    lateinit var list: ArrayList<String>
    lateinit var inflater: LayoutInflater
    var gradeSum: Double = 0.0
    var totalCredits: Double = 0.0
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        val view: View = inflater.inflate(R.layout.courses, parent, false)
        return holder(view)
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val completeCourseName: String = list[position]
        holder.coursename.text = completeCourseName
        totalCredit(holder.coursename.text.toString())
    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun totalCredit(coursename:String) {

        var database=FirebaseDatabase.getInstance()
        var ref:DatabaseReference= database!!.getReference("Branch").child(branch).child(sem).child(coursename)
        ref?.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        var credits: Double = snap.value.toString().toDouble()
                        totalCredits += credits
                        Log.i("Credits:",totalCredits.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Cancelled", error.details)
            }
        })
    }



    inner class holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var coursename: TextView = itemView.findViewById(R.id.coursename)
        var check: CheckBox = itemView.findViewById(R.id.checkBox)


    }

    init {
        this.list = list
        inflater = LayoutInflater.from(context)
        FirebaseApp.initializeApp(context);
        this.context=context
    }
}

