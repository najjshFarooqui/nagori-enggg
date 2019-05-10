package com.example.applligent.nagoriengineering;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.example.applligent.nagoriengineering.model.Chat;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;




public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;


    private List<Chat> mChats;
    Context context;


    public ChatRecyclerAdapter(List<Chat> chats, Context context) {
        mChats = chats;
        this.context = context;
    }


    public void add(Chat chat) {
        mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.my_message, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.their_message, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals(mChats.get(position).getUserId(),
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }


    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        Chat chat = mChats.get(position);





        myChatViewHolder.txtChatMessage.setText(chat.getMessage());
        String time = chat.getHourMinute().substring(11,16);
        myChatViewHolder.myTime.setText(time);

    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        Chat chat = mChats.get(position);


        String alphabet = chat.getDisplayName();


        otherChatViewHolder.txtChatMessage.setText(chat.getMessage());
        otherChatViewHolder.txtUserAlphabet.setText(alphabet);
        String time =chat.getHourMinute().substring(11,16);
        otherChatViewHolder.theirTime.setText(time);
    }


    @Override
    public int getItemCount() {
        if (mChats != null) {
            return mChats.size();
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mChats.get(position).getUserId(),
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }


    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, myTime;


        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.message_body_mine);
            myTime = itemView.findViewById(R.id.time_mine);


        }
    }


    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, txtUserAlphabet, theirTime;


        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.message_body_sender);
            txtUserAlphabet = itemView.findViewById(R.id.name_sender);
            theirTime = itemView.findViewById(R.id.time_their);

        }
    }
}
