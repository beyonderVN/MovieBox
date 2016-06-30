package com.vinhsang.demo.v_chat.activity.chatbox.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinhsang.demo.v_chat.activity.chatbox.fragment.modelview.ChatMessage;
import com.vinhsang.demo.v_chat.common.view.CircularTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private String TAG = "ChatMessageAdapter";
    private List<ChatMessage> mMessages;
    private int[] mUsernameColors;

    public ChatMessageAdapter(Context context, List<ChatMessage> messages) {
        mMessages = messages;
        mUsernameColors = context.getResources().getIntArray(com.vinhsang.demo.R.array.username_colors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case ChatMessage.TYPE_LOG:
                layout = com.vinhsang.demo.R.layout.item_log;
                break;
            case ChatMessage.TYPE_CLIENT_MESSAGE:
                layout = com.vinhsang.demo.R.layout.item_message;
                break;
            case ChatMessage.TYPE_MY_MESSAGE:
                layout = com.vinhsang.demo.R.layout.item_my_message;
                break;
            default:
                layout = com.vinhsang.demo.R.layout.item_log;
                break;

        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final ChatMessage message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
        viewHolder.setUsername(message.getUsername());
        boolean checkMessageDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString;
        Date currentDate = new Date();
        dateString = dateFormat.format(currentDate);
        //Log.d(TAG, "onBindViewHolder: "+dateString+"|"+message.getmDate());
        checkMessageDate = message.getmDate().equals(dateString);
        viewHolder.setTime(checkMessageDate?message.getmHour():message.getmDate());
        viewHolder.setNewNumber(String.valueOf(message.getNewNumber()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public void addMessage(final ChatMessage newItem) {
        try {

            mMessages.add(newItem);

        } catch (Exception e) {
            Log.e(TAG, "addMessage: ", e);

        }


    }


    public void replaceItem(final ChatMessage newItem, final int position) {
        Log.d(TAG, "replaceItem: " + position);
        mMessages.set(position, newItem);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mMessageView;
        private TextView mTime;
        private CircularTextView mNewMessageNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsernameView = (TextView) itemView.findViewById(com.vinhsang.demo.R.id.username);
            mMessageView = (TextView) itemView.findViewById(com.vinhsang.demo.R.id.message);
            mTime = (TextView) itemView.findViewById(com.vinhsang.demo.R.id.time);
            mNewMessageNumber = (CircularTextView) itemView.findViewById(com.vinhsang.demo.R.id.new_number);

        }

        public void setUsername(String username) {
            if (null == mUsernameView) return;
            mUsernameView.setText(username);
            mUsernameView.setTextColor(getUsernameColor(username));
        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }
        public void setTime(String time) {
            if (null == mTime) return;
            mTime.setText(time);
        }
        public void setNewNumber(String number) {
            if (null == mNewMessageNumber) return;
            if(number == "0"){
                mNewMessageNumber.setVisibility(View.GONE);
            }
            mNewMessageNumber.setVisibility(View.VISIBLE);
            mNewMessageNumber.setText(number);
        }


        private int getUsernameColor(String username) {

            return mUsernameColors[3];
        }


    }

}
