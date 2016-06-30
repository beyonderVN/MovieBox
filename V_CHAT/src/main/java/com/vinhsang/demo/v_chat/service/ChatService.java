package com.vinhsang.demo.v_chat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.vinhsang.demo.v_chat.application.ChatApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.content.ContentValues.TAG;

public class ChatService extends Service {
    private static String LOG_TAG = "ChatService";
    //chatsocket
    private Socket mSocket;
    private String key = "khaitamtri";//$.session.get("email"); //key this website
    private String keyR = Get_key_R(); // key random
    private String keyT = key + "_" + keyR;

    private String Get_key_R() {
        DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss_SSS");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        return dateFormat.format(date);
    }

    // Lưu trữ dữ liệu thời tiết.
    private static final Map<String, String> chatData = new HashMap<String, String>();

    private final IBinder binder = new LocalChatBinder();

    public class LocalChatBinder extends Binder {

        public ChatService getService() {
            return ChatService.this;
        }
    }

    public void test() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("key", String.valueOf(key));
            obj.put("keyR", keyR);
            obj.put("role", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("join", obj);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            sendUpdate(data.toString());
            Log.i(TAG, "data: " + data);
            String message;
            try {
                message = data.getString("message");
            } catch (JSONException e) {
                return;
            }
            Log.i(TAG, "MessageCatchByService: " + message);


        }
    };

    public ChatService() {
        Log.i(TAG, "ChatService: ");

    }
    public interface UpdateListener {
        public void onUpdate(String value);
    }
    private final ArrayList<UpdateListener> mListeners
            = new ArrayList<UpdateListener>();
    private final Handler mHandler = new Handler();
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on(keyT, onNewMessage);
        mSocket.connect();
        //signInNodeJSChatServer();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind"+intent.toString());

        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG, "onRebind"+intent.toString());
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG, "onUnbind"+intent.toString());
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    // Trả về thông tin thời tiết ứng với địa điểm của ngày hiện tại.
    public String getTime() {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");

        String time = df.format(now);

        return time;
    }
    public void registerListener(UpdateListener listener) {

        mListeners.add(listener);
        Log.d(TAG, "registerListener: "+listener);
        Log.d(TAG, "registerListener: "+mListeners.size());

    }

    public void unregisterListener(UpdateListener listener) {
        Log.d(TAG, "unregisterListener: "+listener);
        mListeners.remove(listener);
    }

    private void sendUpdate(String value) {
        Log.d(TAG, "sendUpdate: "+value);
        for (int i=mListeners.size()-1; i>=0; i--) {
            mListeners.get(i).onUpdate(value);
        }
    }

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "run: ",null );
        }
    };
}
