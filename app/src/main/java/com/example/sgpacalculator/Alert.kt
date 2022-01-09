package com.example.sgpacalculator

import android.app.AlertDialog
import android.content.Context

class Alert (context: Context?) {
    var alert: AlertDialog
    fun show() {
        alert.show()
    }

    init {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Network Error!")
        builder1.setCancelable(true)
        builder1.setNegativeButton(
            "Okay"
        ) { dialog, id -> dialog.cancel() }
        alert = builder1.create()
    }
}

