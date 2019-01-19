package com.example.applligent.nagoriengineering;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.model.ReminderModel;
import com.example.applligent.nagoriengineering.view.AlarmActivity;

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
        TextView name;
        TextView title;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message_reminder);
            time = itemView.findViewById(R.id.time_reminder);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item = reminders.get(getAdapterPosition()).toString();
                    Intent intent = new Intent(itemView.getContext(), AlarmActivity.class);
                    intent.putExtra("remin", item);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        protected void bindTo(ReminderModel reminders) {
            title.setText(reminders.title);
            name.setText(reminders.user);
            message.setText(reminders.message);
            time.setText(reminders.sendingTime);


        }
    }

}
