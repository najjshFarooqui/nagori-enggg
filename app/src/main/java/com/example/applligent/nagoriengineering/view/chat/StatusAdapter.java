package com.example.applligent.nagoriengineering.view.chat;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.model.Status;
import com.example.applligent.nagoriengineering.model.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ItemHolder> {
    List<Status> statuses;
    Context context;


    public StatusAdapter(List<Status> statuses, Context context) {
        this.statuses = statuses;
        this.context = context;

    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.message_layout, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ItemHolder itemHolder, int i) {
        itemHolder.bindTo(statuses.get(i));
    }


    @Override
    public int getItemCount() {
        return statuses.size();
    }

    class  ItemHolder extends RecyclerView.ViewHolder {


        TextView time;
        TextView statuss;
        TextView name;

        public FirebaseAuth mAuth;
        public DatabaseReference databaseReference;




        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.message_user);
            time = itemView.findViewById(R.id.message_time);
            statuss = itemView.findViewById(R.id.message_text);


            mAuth = FirebaseAuth.getInstance();


        }

        protected void bindTo(Status status) {


            name.setText(status.getDisplayName());

            if (name.getText().toString().equals(GeneralPreference.getNameLoaded(context))) {
                name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));


                String s = status.getHourMinute();
                String hourmin = s.substring(11, 16);
                time.setText(hourmin);
                statuss.setText(status.getMessage());




            }
        }
    }
}





