package com.example.mykotlintestapp.dialog

import android.app.AlertDialog
import android.content.Context

class CustomDialog(var context: Context?, private val eventListener: OnEventListener)  {

    private val builder: AlertDialog.Builder
    init {
        builder = AlertDialog.Builder(context)
    }

    fun setTitle(title: String) :AlertDialog.Builder{
        builder.setTitle(title)
        return builder
    }

    fun setBody(message: String) :AlertDialog.Builder{
        builder.setMessage(message)
        return builder
    }

    fun show() {
        builder.create()
            .show()
    }

    fun setPositive(label: String) :AlertDialog.Builder{
        builder.setPositiveButton(label){ dialog, which->
            eventListener.onPositive()
//            dialog.dismiss()
        }
        return builder
    }

    fun setNegative(label: String) :AlertDialog.Builder{
        builder.setNegativeButton(label){ dialog, which->
            eventListener.onNegative()
//            dialog.dismiss()
        }
        return builder
    }



    interface OnEventListener{
        fun onPositive()
        fun onNegative()

    }
}