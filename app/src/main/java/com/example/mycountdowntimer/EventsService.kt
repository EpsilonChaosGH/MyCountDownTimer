package com.example.mycountdowntimer

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.WindowManager


typealias EventListener = (events: List<Event>) -> Unit

class EventsService {

    private val listeners = mutableSetOf<EventListener>()
    private var events = mutableListOf<Event>()

    fun addListener(listener: EventListener) {
        listeners.add(listener)
        listener.invoke(events)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(events) }
    }

    fun changeEvent(newEvent: Event) {
        events.mapIndexed {i, oldEvent ->
            if (oldEvent.id == newEvent.id) {
                events = ArrayList(events)
                events[i] = newEvent
                notifyChanges()
            }
        }
    }

    fun addEvent(event: Event) {
        events = ArrayList(events)
        events.add(event)
        notifyChanges()
    }

    fun deleteEvent(event: Event) {
        val eventToDelete = events.indexOfFirst { it.id == event.id }
        if (eventToDelete == -1) return
        events = ArrayList(events)
        events.removeAt(eventToDelete)
        notifyChanges()
    }
}