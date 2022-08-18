package com.example.mycountdowntimer


data class Event(
    val id: Long,
    var title: String = "",
    var time: String = "",
    var currentTime: String = ""
)