package com.vinhsang.demo.v_chat.activity.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.adapter.MessageAdapter;
import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.modelview.Message;
import com.vinhsang.demo.v_chat.application.ChatApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.socket.client.Socket;


public class MessagesFragment3 extends Fragment implements ChatApplication.MessagesFragmentInterface {
    private static final String ARG_TAB_NAME = "tab_name";
    private static final String TAB_NAME = "Messages";
    public static final String TAG = "MessagesFragment3";
    public String getTabName() {
        return TAB_NAME;
    }

    @Bind(com.vinhsang.demo.R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(com.vinhsang.demo.R.id.fab2)
    FloatingActionButton floatingActionButton;
    private Context mContext;
    private Activity mActivity;

    //
    //chatsocket
    private Socket mSocket;
    private String key = "khaitamtri1";//$.session.get("email"); //key this website
    private String keyR = Get_key_R(); // key random
    private String keyT = key + "_" + keyR;
    //
    private MessageAdapter mAdapter;
    private List<Message> mMessages = new ArrayList<Message>();

    private String Get_key_R() {
        DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss_SSS");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        return dateFormat.format(date);
    }

    public MessagesFragment3() {
        // Required empty public constructor
    }

    public static MessagesFragment3 newInstance() {
        MessagesFragment3 fragment = new MessagesFragment3();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAB_NAME, TAB_NAME);
        fragment.setArguments(args);
        return fragment;
    }

    public static MessagesFragment3 newInstance(String tabName) {
        MessagesFragment3 fragment = new MessagesFragment3();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAB_NAME, tabName);
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

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new MessageAdapter(activity, mMessages);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setAdapter(mAdapter);


        //app.signInNodeJSChatServer();
    }
    ChatApplication app;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(com.vinhsang.demo.R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void scrollToBottom() {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }
    private void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    private void addMessage(Message message) {
        Log.d(TAG, "addMessage: " + message.getMessage());

        mAdapter.addMessage(message);
        mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemInserted(mMessages.size() - 1);

        scrollToBottom();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }





    @Override
    public String getTagString() {
        return TAG;
    }

    @Override
    public void doWork(final int work, final Object object) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "doWork");
                Message message = (Message) object;
                switch (work){
                    case ChatApplication.ON_NEW_MESSAGE:
                        Log.d(TAG, "ChatApplication2.ON_NEW_MESSAGEL: "+message);
                        addMessage(message);
                        break;
                }
            }
        });

    }

}
