package com.vinhsang.demo.v_chat.common;

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

import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.modelview.Message;
import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.adapter.MessageAdapter;
import com.vinhsang.demo.v_chat.application.ChatApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.content.ContentValues.TAG;


public class MessagesFragment extends Fragment {
    private static final String ARG_TAB_NAME = "tab_name";
    private static final String TAB_NAME = "Messages";

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

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance() {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAB_NAME, TAB_NAME);
        fragment.setArguments(args);
        return fragment;
    }

    public static MessagesFragment newInstance(String tabName) {
        MessagesFragment fragment = new MessagesFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(com.vinhsang.demo.R.layout.fragment_simple, container, false);
        ButterKnife.bind(this, view);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(keyT, onNewMessage);
        mSocket.connect();
        test();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void scrollToBottom() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }
    private void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    private void addMessage(JSONObject data) {
        Log.d(TAG, "addMessage: " + data);

        mAdapter.addMessage(new Message.Builder().build(data));
        mAdapter.notifyItemInserted(mMessages.size() - 1);

        scrollToBottom();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void test() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("key", String.valueOf(key));
            obj.put("keyR", keyR);
            obj.put("role", 0);
            obj.put("keysub", "u4");
            obj.put("fullname", "HNP4");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("join", obj);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    Log.i(TAG, "data: " + args[0]);
                    addMessage(data);

                }
            });

        }
    };


}
