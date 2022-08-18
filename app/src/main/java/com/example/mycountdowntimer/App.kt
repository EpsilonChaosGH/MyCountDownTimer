package com.example.mycountdowntimer

import android.app.Application


class App: Application() {
    val eventsService = EventsService()
}