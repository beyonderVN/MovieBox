package com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinhsang.demo.v_chat.activity.chatbox.ChatActivity;
import com.vinhsang.demo.v_chat.application.ChatApplication;
import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.modelview.Message;
import com.vinhsang.demo.v_chat.common.view.CircularTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private String TAG = "MessageAdapter";
    private List<Message> mMessages;
    private int[] mUsernameColors;

    public MessageAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        mUsernameColors = context.getResources().getIntArray(com.vinhsang.demo.R.array.username_colors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Message.TYPE_MESSAGE:
                layout = com.vinhsang.demo.R.layout.item_message2;
                break;
            case Message.TYPE_LOG:
                layout = com.vinhsang.demo.R.layout.item_log;
                break;
            case Message.TYPE_ACTION:
                layout = com.vinhsang.demo.R.layout.item_action;
                break;
            case Message.TYPE_CHAT_MESSAGE:
                layout = com.vinhsang.demo.R.layout.item_message;
                break;
            case Message.TYPE_MY_CHAT_MESSAGE:
                layout = com.vinhsang.demo.R.layout.item_my_message;
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
        final Message message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
        viewHolder.setUsername(message.getUsername());
        boolean checkMessageDate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString;
        Date currentDate = new Date();
        dateString = dateFormat.format(currentDate);
//        Log.d(TAG, "onBindViewHolder: "+dateString+"|"+message.getmDate());
//        Log.d(TAG, "onBindViewHolder: "+message.getmDate().equals(dateString));
//        Log.d(TAG, "onBindViewHolder: "+message.getMessage());
        checkMessageDate = message.getmDate().equals(dateString);
        viewHolder.setTime(checkMessageDate?message.getmHour():message.getmDate());
        viewHolder.setNewNumber(String.valueOf(message.getNewNumber()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getmKeyRC()==null||message.getmKeyRC()=="") return;
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra(Message.KEY_RC,message.getmKeyRC());
                intent.putExtra(Message.FULLNAME,message.getUsername());
                ChatApplication.currentMessage = message;
                v.getContext().startActivity(intent);
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

    public void addMessage(final Message newItem) {
        try {
            if(newItem.getType()==Message.NONE)return;
            if(newItem.getType()!=Message.R_FROM_CLIENT){
                for (int i = 0; i < mMessages.size(); i++) {
                    if (mMessages.get(i).getmKeyRC() != null) {
                        if (mMessages.get(i).getmKeyRC().equals(newItem.getmKeyRC())) {
                            Log.d(TAG, "keyRC: " + i + mMessages.get(i).getmKeyRC() + "/" + newItem.getmKeyRC());
                            replaceItem(newItem, i);
                            return;
                        }
                    }
                }
            }

            mMessages.add(newItem);

        } catch (Exception e) {
            Log.e(TAG, "addMessage: ", e);

        }


    }


    public void replaceItem(final Message newItem, final int position) {
        Log.d(TAG, "replaceItem: " + position);
        mMessages.set(position, newItem);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(com.vinhsang.demo.R.id.username)
        TextView mUsernameView;
        @Bind(com.vinhsang.demo.R.id.message)
        TextView mMessageView;

        @Bind(com.vinhsang.demo.R.id.time)
        TextView mTime;
        @Bind(com.vinhsang.demo.R.id.new_number)
        CircularTextView mNewMessageNumber;

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

        // number of new message
        public void setNewNumber(String number) {
            if (null == mNewMessageNumber) return;
            if(number == "0"){
                mNewMessageNumber.setVisibility(View.GONE);
            }
            //mNewMessageNumber.setVisibility(View.VISIBLE);
            //mNewMessageNumber.setText(number);
        }


        private int getUsernameColor(String username) {
            return mUsernameColors[3];
        }


    }

}
