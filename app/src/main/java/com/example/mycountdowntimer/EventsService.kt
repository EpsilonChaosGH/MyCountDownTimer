package com.example.mycountdowntimer

import android.os.CountDownTimer
import android.util.Log


typealias EventListener = (events: List<Event>) -> Unit

class EventsService {

    private var timer: CountDownTimer? = null
    private val listeners = mutableSetOf<EventListener>()
    private var events = mutableListOf<Event>()

    fun addListener(listener: EventListener) {
        listeners.add(listener)
        listener.invoke(events)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(events) }
    }

    fun startCountDownTimer(event: Event) {

        timer?.cancel()
        timer = object : CountDownTimer(toMillis(event.time), 1000) {

            override fun onTick(time: Long) {
                val currentIndex = events.indexOfFirst { it.id == event.id }
                events = ArrayList(events)
                events[currentIndex] = event.copy(currentTime = time.toString())
                notifyChanges()
            }

            override fun onFinish() {
            }

        }.start()
    }

    private fun toMinutes(time: Long): String {
        return (time / 60_000).toString()
    }

    private fun toMillis(time: String): Long {
        return (time.toInt() * 60_000).toLong()
    }

    fun changeEvent(newEvent: Event) {
        val currentIndex = events.indexOfFirst { it.id == newEvent.id }
        events = ArrayList(events)
        events[currentIndex] = newEvent
        notifyChanges()
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