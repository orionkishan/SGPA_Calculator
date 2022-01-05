package com.example.sgpacalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner

class sgpa_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sgpa)


        val autoCompleteTextViewBranch = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewBranch)
        val autoCompleteTextViewSemester = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewSemester)

        val branch = resources.getStringArray(R.array.Branch)
        val semester = resources.getStringArray(R.array.Semester)

        val branchAdapter = ArrayAdapter(this, R.layout.dropdown_items, branch)
        val semesterAdapter = ArrayAdapter(this, R.layout.dropdown_items, semester)

        autoCompleteTextViewBranch.setAdapter(branchAdapter)
        autoCompleteTextViewSemester.setAdapter(semesterAdapter)

        val goButton =  findViewById<Button>(R.id.goButton)

        goButton.setOnClickListener(){
            // load the recycler view

        }

    }
}