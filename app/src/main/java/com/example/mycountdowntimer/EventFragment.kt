package com.example.mycountdowntimer

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycountdowntimer.databinding.FragmentEventListBinding
import com.example.mycountdowntimer.databinding.ImputNameBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class EventFragment : Fragment() {
    private lateinit var binding: FragmentEventListBinding

    private val eventsService: EventsService
        get() = (requireActivity().applicationContext as App).eventsService

    private var adapter = EventAdapter(object : EventActionListener {

        override fun deleteEvent(event: Event) {
            eventsService.deleteEvent(event)
        }

        override fun changeEvent(event: Event) {
            createEvent(event, CHANGE_KEY)
        }

        override fun changeTime(event: Event) {
            createEvent(event, CHANGE_KEY)
        }

        override fun startEvent(event: Event) {
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEventListBinding.inflate(inflater, container, false)

        binding.addEventBt.setOnClickListener {
            createEvent(Event(Random().nextLong()),ADD_KEY)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter

        eventsService.addListener(eventListener)

        return binding.root
    }

    private val eventListener: EventListener = {
        adapter.events = it
    }

    private fun createEvent(event: Event, key: String) {
        val dialogBinding = ImputNameBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireActivity())
            .setTitle("Add Name and Title")
            .setView(dialogBinding.root)
            .setPositiveButton("OK", null)
            .create()
        dialog.setOnShowListener {
            dialogBinding.inputEditText.requestFocus()
            dialogBinding.inputEditText.setText(event.title)
            dialogBinding.inputTimeEditText.setText(event.time)
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredName = dialogBinding.inputEditText.text.toString()
                val enteredTime = dialogBinding.inputTimeEditText.text.toString()
                if (enteredName.isBlank()) {
                    dialogBinding.inputEditText.error = "EMPTY"
                    return@setOnClickListener
                } else if (enteredTime.isBlank()) {
                    dialogBinding.inputTimeEditText.error = "EMPTY"
                    return@setOnClickListener
                }
                when (key) {
                    ADD_KEY -> {
                        val newEvent = Event(event.id, enteredName, enteredTime)
                        eventsService.addEvent(newEvent)
                        dialog.dismiss()
                    }
                    CHANGE_KEY -> {
                        val nevEvent = Event(event.id, enteredName, enteredTime)
                        eventsService.changeEvent(nevEvent)
                        dialog.dismiss()
                    }
                }
            }
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }
    companion object{
        const val ADD_KEY = "ADD_KEY"
        const val CHANGE_KEY = "CHANGE_KEY"
    }
}
