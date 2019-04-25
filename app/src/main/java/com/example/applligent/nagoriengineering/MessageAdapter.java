package com.example.applligent.nagoriengineering;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.model.Chat;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> {
    List<Chat> messages;

    public MessageAdapter(List<Chat> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.message_layout, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ItemHolder itemHolder, final int i) {
        itemHolder.bindTo(messages.get(i));


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView time;
        TextView message;
        LinearLayout leftParentLayout;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.message_user);
            time = itemView.findViewById(R.id.message_time);
            message = itemView.findViewById(R.id.message_text);


        }

        protected void bindTo(Chat chats) {

            name.setText(chats.getDisplayName());
            String s = chats.getHourMinute();
            String hourmin = s.substring(11, 16);
            time.setText(hourmin);
            message.setText(chats.getMessage());


        }
    }

}
