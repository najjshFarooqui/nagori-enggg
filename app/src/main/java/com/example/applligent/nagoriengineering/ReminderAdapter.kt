package com.example.applligent.nagoriengineering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.applligent.nagoriengineering.model.ReminderModel
import com.example.applligent.nagoriengineering.view.AlarmActivity

class ReminderAdapter(var reminders: List<ReminderModel>) : RecyclerView.Adapter<ReminderAdapter.ItemHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ReminderAdapter.ItemHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.reminder_layout, viewGroup, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindTo(reminders[position])
    }

    override fun getItemCount(): Int {
        return reminders.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var message: TextView
        internal var time: TextView
        internal var name: TextView
        internal var title: TextView

        init {
            name = itemView.findViewById(R.id.name)
            message = itemView.findViewById(R.id.message_reminder)
            time = itemView.findViewById(R.id.time_reminder)
            title = itemView.findViewById(R.id.title)
            itemView.setOnClickListener {
                val item = reminders[adapterPosition].toString()
                val intent = Intent(itemView.context, AlarmActivity::class.java)
                intent.putExtra("remin", item)
                itemView.context.startActivity(intent)
            }
        }

        fun bindTo(reminders: ReminderModel) {
            title.text = reminders.title
            name.text = reminders.user
            message.text = reminders.message
            time.text = reminders.sendingTime


        }
    }

}
