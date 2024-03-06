package com.example.mykotlintestapp.first

data class LoginInfo(

    var id: Int ,
    var name: String ,

    val loginTime: Long ,

    var role: String,

    var isSwiped: Boolean = false

)