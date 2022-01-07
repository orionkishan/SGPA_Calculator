package com.example.sgpacalculator

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*


class sgpa_activity : AppCompatActivity() {
    companion object{
        var rv: RecyclerView? = null
        var list: ArrayList<String>? = null
        var courseadapter: courseAdapter? = null
        var database: FirebaseDatabase? = null
        var ref: DatabaseReference? = null
        var branch:String ="CSE"
        var sem:String ="I"
    }


    class downloadTask : AsyncTask<Void, Void, Void>()
    {
        override fun doInBackground(vararg voids: Void?): Void? {
            database = FirebaseDatabase.getInstance()
            ref = database!!.getReference("Branch").child(branch).child(sem)
            return null
        }

        override fun onPostExecute(unused: Void?) {
            super.onPostExecute(unused)
            ref?.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                                snap.key?.let { list?.add(it) }
                        }


                        courseadapter?.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("Cancelled", error.details)
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sgpa)


        val autoCompleteTextViewBranch = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewBranch)
        val autoCompleteTextViewSemester = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewSemester)
//        val autoCompleteTextViewGrade = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewGrade)

        val branchArray = resources.getStringArray(R.array.Branch)
        val semesterArray = resources.getStringArray(R.array.Semester)
        val grade = resources.getStringArray(R.array.Grades)

        val branchAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, branchArray)
        val semesterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, semesterArray)
        val gradeAdapter = ArrayAdapter(this, R.layout.dropdown_items, grade)

        autoCompleteTextViewBranch.setAdapter(branchAdapter)
        autoCompleteTextViewSemester.setAdapter(semesterAdapter)
//        autoCompleteTextViewGrade.setAdapter(gradeAdapter)

        val goButton =  findViewById<Button>(R.id.goButton)

        rv = findViewById(R.id.rvSubjects)
        val gridLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        rv?.setLayoutManager(gridLayoutManager)

        list = ArrayList<String>()
        courseadapter = courseAdapter(this, branch, sem, list!!)
        rv?.setAdapter(courseadapter)
        val task = downloadTask()
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        goButton.setOnClickListener(){

            branch =  autoCompleteTextViewBranch.text.toString()
            sem  = autoCompleteTextViewSemester.text.toString()
            list!!.clear();
            courseadapter!!.notifyDataSetChanged();
            val task = downloadTask()
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }



        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener(){

            var sgpa: Double= (courseadapter!!.gradeSum/ courseadapter!!.totalCredits)
            Log.i(sgpa.toString(),"SGPA is")
            Log.i(courseadapter!!.gradeSum.toString(),"GradeSum is")
            AlertDialog.Builder(this)
                .setTitle("SGPA")
                .setMessage("Your SGPA is $sgpa") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes,
                    DialogInterface.OnClickListener { dialog, which ->
                        // Continue with delete operation
                    }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show()
        }
    }
}


