package com.example.sgpacalculator

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*


class sgpa_activity : AppCompatActivity() {
    companion object{
        var rv: RecyclerView? = null
        var list: ArrayList<courses>? = null
        var courseadapter: courseAdapter? = null
        var database: FirebaseDatabase? = null
        var ref: DatabaseReference? = null
        var branch:String ="CSE"
        var sem:String ="I"
        var gradeMap : HashMap<String, Int> = HashMap<String, Int> ()
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
                                var course:courses = courses()
                                snap.key?.let { course.setCoursename(it)}
                                var credits:String = snap.value.toString()
                                credits = credits.subSequence(9,credits.length-1) as String
                                course.setCredits(credits.toDouble())
                                list?.add(course)
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

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sgpa)

        gradeMap["A+"] = 10
        gradeMap["A"] = 9
        gradeMap["B+"] = 8
        gradeMap["B"] = 7
        gradeMap["C"] = 6
        gradeMap["D"] = 5
        gradeMap["F"]= 0

        val ll = findViewById<LinearLayout>(R.id.linearLayout)
        val tv = findViewById<TextView>(R.id.textView)

        val autoCompleteTextViewBranch = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewBranch)
        val autoCompleteTextViewSemester = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewSemester)

        val branchArray = resources.getStringArray(R.array.Branch)
        val semesterArray = resources.getStringArray(R.array.Semester)

        val branchAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, branchArray)
        val semesterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, semesterArray)

        autoCompleteTextViewBranch.setAdapter(branchAdapter)
        autoCompleteTextViewSemester.setAdapter(semesterAdapter)

        val goButton =  findViewById<Button>(R.id.goButton)

        rv = findViewById(R.id.rvSubjects)
        val gridLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        rv?.setLayoutManager(gridLayoutManager)

        list = ArrayList<courses>()
        courseadapter = courseAdapter(this, branch, sem, list!!)
        rv?.setAdapter(courseadapter)
        val task = downloadTask()
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        val oa1: ObjectAnimator = ObjectAnimator.ofFloat(tv, "translationY", 0f, 800f)
        oa1.duration = 800
        oa1.start()

        val oa2: ObjectAnimator = ObjectAnimator.ofFloat(ll, "translationY", 0f, 800f)
        oa2.duration = 800
        oa2.start()

        var clicksOnGo: Int=0

        goButton.setOnClickListener(){
            clicksOnGo++
            branch =  autoCompleteTextViewBranch.text.toString()
            sem  = autoCompleteTextViewSemester.text.toString()
            list!!.clear();
            courseadapter!!.notifyDataSetChanged();
            val task = downloadTask()
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            courseadapter!!.totalCredits=0.0
            courseadapter!!.map.clear()

            val sv = findViewById<View>(R.id.scrollView) as ScrollView

            val rv = findViewById<RecyclerView>(R.id.rvSubjects) as RecyclerView

            val calcButton = findViewById<View>(R.id.calculateButton) as Button

//            val oa1: ObjectAnimator = ObjectAnimator.ofFloat(tv, "translationY", 800f, 0f)
//            oa1.duration = 1000

            val oa2: ObjectAnimator = ObjectAnimator.ofFloat(ll, "translationY", 800f, 0f)
            oa2.duration = 1000

            if(clicksOnGo==1){
                sv.visibility = View.VISIBLE
                rv.animate().alpha(1.0f).setDuration(2000);
                calcButton.animate().alpha(1.0f).setDuration(2000);
//                oa1.start()
                tv.animate().alpha(0.0f)
                oa2.start()
            }
        }





        val calculateButton = findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener(){

            var map = courseadapter!!.map
            var numerator: Double =0.0
            var denominator: Double= courseadapter!!.totalCredits

            for(item in map)
                numerator+=item.value.first* gradeMap[item.value.second]!!

            var sgpa: Double=numerator/denominator
            Log.i("SGPA is",sgpa.toString())
//            Log.i(courseadapter!!.gradeSum.toString(),"GradeSum is")
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


