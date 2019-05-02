package com.example.applligent.nagoriengineering;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> implements Filterable {
    List<Chat> messages;
    Context context;
    List<String> itemFull;


    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String searchItem : itemFull) {
                    if (searchItem.toLowerCase().contains(filterPattern)) {
                        filteredList.add(searchItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            messages.clear();
            messages.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public MessageAdapter(List<Chat> messages, Context context) {
        this.messages = messages;
        this.context = context;
        itemFull = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MessageAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.message_layout, viewGroup, false);
        Drawable drawable = context.getResources().getDrawable(R.drawable.chat_bubble_right);
        view.setBackground(drawable);


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

    @Override
    public Filter getFilter() {
        return itemFilter;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {


        TextView time;
        TextView message;
        TextView name;


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
                    Toast.makeText(context, "U+1F617", Toast.LENGTH_SHORT).show();
                    return true;

                }
            });

        }

        protected void bindTo(Chat chats) {


            name.setText(chats.getDisplayName());

            if (name.getText().toString().equals(GeneralPreference.getNameLoaded(context))) {
                name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            } else if (name.getText().toString().equals("Alfiya")) {
                name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            }


            String s = chats.getHourMinute();
            String hourmin = s.substring(11, 16);
            time.setText(hourmin);
            message.setText(chats.getMessage());


        }
    }
}




