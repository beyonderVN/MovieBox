package com.vinhsang.demo.v_chat.activity.chatbox.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.vinhsang.demo.v_chat.activity.chatbox.ChatActivity;
import com.vinhsang.demo.v_chat.activity.chatbox.fragment.adapter.ChatMessageAdapter;
import com.vinhsang.demo.v_chat.activity.chatbox.fragment.modelview.ChatMessage;
import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.modelview.Message;
import com.vinhsang.demo.v_chat.application.ChatApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatFragment extends Fragment implements ChatApplication.MessagesFragmentInterface {

    public static final String TAG = "ChatFragment";
    @Bind(com.vinhsang.demo.R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(com.vinhsang.demo.R.id.message_input)
    TextView mMessageInput;
    @Bind(com.vinhsang.demo.R.id.send_button)
    CircleImageView mSendButton;
    private Context mContext;
    private Activity mActivity;

    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mMessages = new ArrayList<ChatMessage>();
    private String mKeyRC;
    private String mFullname;
    ChatApplication app;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static ChatFragment newInstance(String tabName) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        mContext = getActivity();
        mActivity = getActivity();
        app = (ChatApplication) getActivity().getApplication();
        app.registerListener(this);
        mKeyRC = mActivity.getIntent().getExtras().getString(Message.KEY_RC);
        mFullname = mActivity.getIntent().getExtras().getString(Message.FULLNAME);
        Log.d(TAG, "onCreate:ChatMessage.FULLNAME : " + mFullname);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new ChatMessageAdapter(activity, mMessages);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        app.getChatMessagesByUser(mKeyRC);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(com.vinhsang.demo.R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);

        mMessageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == com.vinhsang.demo.R.id.login || id == EditorInfo.IME_NULL) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        //setupUI(view);


        return view;
    }

    public static void hideSoftKeyboard(Activity activity) {
         //Log.d(TAG, "hideSoftKeyboard: ");
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //send message event


        //send message event when press Enter


        mActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText||view == (View) mSendButton)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mActivity);
                    return false;
                }

            });

        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    private void sendMessage() {
        if (mMessageInput == null) return;
        String message = mMessageInput.getText().toString();
        if (!message.isEmpty()) {
            ChatMessage sendMessage = app.sendChatMessage(message);
        }
        mMessageInput.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void scrollToBottom() {
        //Log.d(TAG, "scrollToBottom");
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
        //mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        //app.unRegisterListener(this);
    }

    @Override
    public String getTagString() {
        return TAG;
    }

    @Override
    public void doWork(int work, Object args) {
        switch (work) {
            case ChatApplication.ON_GET_PREVIEW_CHAT:
                Log.d(TAG, "ChatApplication.ON_GET_PREVIEW_CHAT: " + args);
                ArrayList<ChatMessage> messages = (ArrayList<ChatMessage>) args;
                for (ChatMessage message : messages) {
                    addChatMessage(message);
                }
                break;
            case ChatApplication.ON_NEW_MESSAGE:
                Log.d(TAG, "ChatApplication.ON_GET_PREVIEW_CHAT: " + args);
                ChatMessage message = (ChatMessage) args;
                addChatMessage(message);
                break;
            case ChatApplication.ON_LOSE_CONNECTION:
                Log.d(TAG, "ChatApplication.ON_LOSE_CONNECTION: " + args);
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        progress = ProgressDialog.show(mActivity, "Connection error !",
                                "Connection error !", true);
                    }
                });

                break;
            case ChatApplication.ON_CONNECTION_RESUME:
                Log.d(TAG, "ChatApplication.ON_CONNECTION_RESUME: " + args);
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {

                        progress.dismiss();
                        Intent intent = new Intent(mActivity, ChatActivity.class);
                        intent.putExtra(Message.KEY_RC,mKeyRC);
                        intent.putExtra(Message.FULLNAME,mFullname);
                        startActivity(intent);

                    }
                });

                break;

        }
    }

    private void addChatMessage(ChatMessage message) {
        //Log.d(TAG, "addMessage: " + message.getMessage());

        mAdapter.addMessage(message);
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();

    }
    ProgressDialog progress;

}
