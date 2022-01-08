package com.example.sgpacalculator

import android.util.Log

class courses {
    private var coursename: String?=null
    private var credits: Double = 0.0
    var isChecked: Boolean = false
    var indexGrade: Int = 0

    @JvmName("getGrade1")
    fun getIndexGrade():Int{
        return indexGrade
    }
    @JvmName("setGrade1")
    fun setIndexGrade(indexGrade: Int) {
        this.indexGrade = indexGrade
    }
    fun getCredits():Double?{
        return credits
    }
    fun setCredits(credits: Double) {
        this.credits = credits;
    }
    fun getCoursename():String?{
        return coursename
    }
    fun setCoursename(courseName: String) {
        this.coursename = courseName;
    }
    fun getisChecked(): Boolean?{
        return isChecked
    }
    fun setisChecked(isChecked: Boolean){
        this.isChecked = isChecked
    }
}