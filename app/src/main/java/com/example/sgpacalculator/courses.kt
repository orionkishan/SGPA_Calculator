package com.example.sgpacalculator

class courses {
    private var coursename: String?=null
    private var credits: Double = 0.0
    var isChecked: Boolean = false
    var grade: String="A+"

    @JvmName("getGrade1")
    fun getGrade():String{
        return grade
    }
    @JvmName("setGrade1")
    fun setGrade(grade: String) {
        this.grade = grade
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