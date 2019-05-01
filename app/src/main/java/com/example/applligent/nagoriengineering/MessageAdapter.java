package com.example.applligent.nagoriengineering;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> {
    List<Chat> messages;
    Context context;


    public MessageAdapter(List<Chat> messages, Context context) {
        this.messages = messages;
        this.context = context;
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


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.message_user);
            time = itemView.findViewById(R.id.message_time);
            message = itemView.findViewById(R.id.message_text);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ChatDao chatDao = MyNagoriApplication.Companion.getDatabase(context).chatDao();
                    Chat msg = messages.get(getAdapterPosition());
                    String s = msg.getUserId();
                    chatDao.deleteByUserId(s);

                    return true;

                }
            });

        }

        protected void bindTo(Chat chats) {


            name.setText(chats.getDisplayName());

            if (name.getText().toString().equals(GeneralPreference.getNameLoaded(context))) {
                name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (name.getText().toString().equals("alfiya")) {
                name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            }


            String s = chats.getHourMinute();
            String hourmin = s.substring(11, 16);
            time.setText(hourmin);
            message.setText(chats.getMessage());


        }
    }
}




