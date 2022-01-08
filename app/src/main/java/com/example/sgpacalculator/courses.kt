package com.example.sgpacalculator

class courses {
    private var coursename: String?=null
    private var credits: Double = 0.0

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
}