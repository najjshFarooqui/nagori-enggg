package com.example.applligent.nagoriengineering;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.model.ReminderModel;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ItemHolder> {
    List<ReminderModel> reminders;


    public ReminderAdapter(List<ReminderModel> reminders) {
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ReminderAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.reminder_layout, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bindTo(reminders.get(position));
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView time;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_reminder);
            time = itemView.findViewById(R.id.time_reminder);
        }

        protected void bindTo(ReminderModel reminders) {
            message.setText(reminders.message);
            time.setText(reminders.sendingTime);
        }
    }

}
