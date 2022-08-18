package com.example.mycountdowntimer

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycountdowntimer.databinding.CountDownTimerItemBinding
import com.example.mycountdowntimer.databinding.FragmentEventListBinding
import java.text.SimpleDateFormat
import java.util.*

interface EventActionListener {
    fun deleteEvent(event: Event)

    fun changeEvent(event: Event)

    fun changeTime(event: Event)

    fun startEvent(event: Event)
}

class EventDiffCallback(
    private val oldList: List<Event>,
    private val newList: List<Event>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldList[oldItemPosition]
        val newEvent = newList[newItemPosition]
        return oldEvent.id == newEvent.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldList[oldItemPosition]
        val newEvent = newList[newItemPosition]
        return oldEvent == newEvent
    }
}

class EventAdapter(
    private val actionListener: EventActionListener
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>(), View.OnClickListener {

    class EventViewHolder(
        val binding: CountDownTimerItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var events = emptyList<Event>()
        set(newValue) {
            val diffCallback = EventDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
           // notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountDownTimerItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.deleteBt.setOnClickListener(this)
        binding.startBt.setOnClickListener(this)
        binding.eventTextView.setOnClickListener(this)
        binding.timeTextView.setOnClickListener(this)

        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.itemView.tag = event
        holder.binding.deleteBt.tag = event
        holder.binding.startBt.tag = event
        holder.binding.eventTextView.tag = event
        holder.binding.timeTextView.tag = event

        holder.binding.eventTextView.text = event.title
        holder.binding.timeTextView.text = event.time
        holder.binding.currentTimeTextView.text = event.currentTime
    }

    override fun getItemCount(): Int = events.size

    override fun onClick(v: View) {
        val event = v.tag as Event

        when (v.id) {
            R.id.deleteBt -> actionListener.deleteEvent(event)
            R.id.startBt -> actionListener.startEvent(event)
            R.id.eventTextView -> actionListener.changeEvent(event)
            R.id.timeTextView -> actionListener.changeTime(event)
        }
    }


}