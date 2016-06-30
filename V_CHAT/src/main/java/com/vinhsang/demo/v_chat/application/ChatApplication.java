package com.vinhsang.demo.v_chat.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.vinhsang.demo.v_chat.Constants;
import com.vinhsang.demo.v_chat.activity.chatbox.fragment.ChatFragment;
import com.vinhsang.demo.v_chat.activity.chatbox.fragment.modelview.ChatMessage;
import com.vinhsang.demo.v_chat.activity.login.BeautifullLoginActivity;
import com.vinhsang.demo.v_chat.activity.main.fragment.MessagesFragment3;
import com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.modelview.Message;
import com.vinhsang.demo.v_chat.common.model.User;
import com.vinhsang.demo.v_chat.connection.HTTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.vinhsang.demo.v_chat.Constants.CHAT_SERVER_URL2;

public class ChatApplication extends Application {
    private static final String TAG = "ChatApplication";


    //For ChatFrament
    public static Message currentMessage = new Message();

    //HTTP
    HTTP http;
    String url = CHAT_SERVER_URL2+"/Vchat/MessageController/get_list_message.htm?key=khaitamtri&keyR=7_38_44_680&keysub=u1";
    //SOCKET
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //private ChatService chatService;

    //OTHER
    public Socket getSocket() {
        return mSocket;
    }

    public static boolean onSignIn = false;
    boolean isConnected = false;

    ArrayList<Message> messages = new ArrayList<Message>();


    //WORK
    public static final int ON_NEW_MESSAGE = 1;
    public static final int SIGN_IN_SUCCESS = 1;
    public static final int ON_LOSE_CONNECTION = 3;
    public static final int ON_CONNECTION_RESUME = 4;
    public static final int SIGNIN_FAIL = 0;
    public static final int ON_GET_PREVIEW_CHAT = 2;

    //---INTERFACES---
    public interface MessagesFragmentInterface {
        public String getTagString();

        public void doWork(int work, Object args);
    }

    MessagesFragmentInterface messagesFragment;
    MessagesFragmentInterface chatFragment;
    MessagesFragmentInterface loginActivity;

    //SIGN_IN
    String key = "khaitamtri";//$.session.get("email"); //key this website
    String keyR = ""; // key random
    String keyT = "";
    int role = 0;
    String keysub = "";
    String fullname = "";


    private String Get_key_R() {
        DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss_SSS");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        return dateFormat.format(date);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();

        //system SocketIO event
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        //app event



        mSocket.connect();


        http = HTTP.getInstance();


    }


    public void registerListener(MessagesFragmentInterface listener) {
        Log.d(TAG, "registerListener: " + listener.getTagString());
        switch (listener.getTagString()) {
            case MessagesFragment3.TAG:
                messagesFragment = listener;
                for (Message message : messages
                        ) {
                    messagesFragment.doWork(ON_NEW_MESSAGE, message);
                }

                break;
            case ChatFragment.TAG:
                chatFragment = listener;
                break;
            case BeautifullLoginActivity.TAG:
                loginActivity = listener;
                break;
        }


    }

    public void unRegisterListener(MessagesFragmentInterface listener) {
        Log.d(TAG, "unRegisterListener: " + listener.getTagString());
        switch (listener.getTagString()) {
            case MessagesFragment3.TAG:
                break;
            case ChatFragment.TAG:
                chatFragment = null;
                break;
            case BeautifullLoginActivity.TAG:
                loginActivity = null;
                break;
        }


    }

    private Emitter.Listener onNewMessage2 = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            Log.i(TAG, "data: " + args[0]);
        }
    };


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onConnect ");
            if (!isConnected) {
                isOnSignIn();
                isConnected = true;
                if (chatFragment != null){

                    chatFragment.doWork(ON_CONNECTION_RESUME, null);
                }
            }

        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onDisconnect ");
            isConnected = false;
            if (chatFragment != null){

                chatFragment.doWork(ON_LOSE_CONNECTION, null);
            }

        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "onConnectError ");

        }
    };


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(TAG, "onNewMessage is running: " + args[0]);
            JSONObject data = (JSONObject) args[0];

            Message message;
            try {

                message = new Message.Builder().build(data);
                if (message.getR() == Message.R_SUCCESS) {
                    onSignIn = true;
                }
                messages.add(message);
                if (messagesFragment != null) {
                    messagesFragment.doWork(ON_NEW_MESSAGE, message);
                }
                Log.d(TAG, "currentMessage.getmKeyRC()== message.getmKeyRC() " + message.getmKeyRC() + message.getmKeyRC());
                Log.d(TAG, "message.getR()==Message.R_FROM_ME " + currentMessage.getR() + ": " + Message.R_FROM_ME);
                if (chatFragment != null &&
                        (currentMessage.getmKeyRC().equals(message.getmKeyRC())
                                || message.getR() == Message.R_FROM_ME)
                        ) {
                    ChatMessage chatMessage = new ChatMessage.Builder().build(data);
                    chatFragment.doWork(ON_NEW_MESSAGE, chatMessage);
                }


            } catch (Exception e) {
                return;
            }

        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            Log.d(TAG, "onUserJoined");
            JSONObject data = (JSONObject) args[0];
            String username;
            int numUsers;
            try {
                username = data.getString("username");
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }


        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            String username;
            int numUsers;
            try {
                username = data.getString("username");
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }


        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            String username;
            try {
                username = data.getString("username");
            } catch (JSONException e) {
                return;
            }


        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject data = (JSONObject) args[0];
            String username;
            try {
                username = data.getString("username");
            } catch (JSONException e) {
                return;
            }


        }
    };


    public int SignIn(String user,String pass) {
        int status;
        status =  checkLogin (user, pass);
        if(loginActivity!=null) {
            loginActivity.doWork(status, new Object());
        }
        if(status == SIGN_IN_SUCCESS){
            signInNodeJSChatServer();
            keepLogin(user,pass);
        }
        return status;


    }
    public void keepLogin(String user,String pass){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.commit();
    }

    private void updateAfterLogin(){
        key = user.getParent();
        fullname = user.getName();
    }
    public void getChatMessagesByUser(String keyRC) {

        try {

            //String url = "http://10.0.12.14:8080/Vchat/MessageController/get_list_message.htm?key=khaitamtri&keyR=7_38_44_680&keysub=u1" ;
            String url = CHAT_SERVER_URL2+"/Vchat/MessageController/" +
                    "get_list_message.htm?" +
                    "key=khaitamtri&" +
                    "keyR=" + keyRC +
                    "&keysub=" + keysub;
            Log.d(TAG, "getChatMessagesByUser: ");
            Log.d(TAG, "url: " + url);
            String response = null;
            response = (String) http.run(url);
            Log.d(TAG, "getChatMessagesByUser: " + response);
            JSONArray jsonArray = new JSONArray(response);

            List<ChatMessage> chatMessages = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChatMessage message = new ChatMessage.Builder().build(jsonObject);
                chatMessages.add(message);
            }
            if (chatFragment != null) {
                Log.d(TAG, "chatMessages.size: " + chatMessages.size());
                chatFragment.doWork(ON_GET_PREVIEW_CHAT, chatMessages);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void signInNodeJSChatServer() {
        JSONObject obj = new JSONObject();
        Log.d(TAG, "signInNodeJSChatServer is running");
        keyR = Get_key_R();
        mSocket.off(keyT, onNewMessage);
        keyT = key + "_" + keyR;
        keysub = user.getEmail();
        fullname = user.getName();
        try {
            obj.put("key", String.valueOf(key));
            obj.put("keyR", keyR);
            obj.put("role", role);
            obj.put("keysub", keysub);
            obj.put("fullname", fullname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "signInNodeJSChatServer: obj: "+obj);
        Log.d(TAG, "signInNodeJSChatServer: keyT: "+keyT);
        mSocket.on(keyT, onNewMessage);
        mSocket.emit("join", obj);
    }

    public ChatMessage sendChatMessage(String messageContent) {
        JSONObject obj = new JSONObject();
        Message myCurentMessage = currentMessage;
        String key = this.key;
        String keysub = this.keysub;
        String keyR = this.keyR;
        String keyC = this.key;
        String keyRC = myCurentMessage.getmKeyRC();
        String keyBK = "1";
        String myMessageContent = messageContent;
        int role = this.role;
        String fullname = this.fullname;

        try {
            obj.put("key", String.valueOf(key));
            obj.put("keysub", keysub);
            obj.put("keyR", keyR);
            //
            obj.put("keyC", String.valueOf(key));
            obj.put("keyRC", keyRC);
            obj.put("keyBK", keyBK);
            obj.put("message", myMessageContent);
            obj.put("role", role);
            obj.put("fullname", fullname);
            Log.d(TAG, "sendChatMessage: " + obj);
        } catch (JSONException e) {

            Log.e(TAG, "sendChatMessage: " + e.getMessage(), null);
            e.printStackTrace();
            return null;
        }

        ChatMessage sendMessage = new ChatMessage.Builder().build(obj);
        sendMessage.setmType(Message.TYPE_MY_CHAT_MESSAGE);
        mSocket.emit("send", obj);
        return sendMessage;
    }
    public static User user = new User();


    public int checkLogin (String email, String pass) {
        String url = CHAT_SERVER_URL2+"/Vchat/UserController/check_login.htm?" +
                "email=" + email + "&" +
                "pass=" + pass;
        Log.d(TAG, "checkLogin:url: "+url);
        int status = SIGNIN_FAIL;
        String response;
        try {
            response = (String) http.run(url);

            Log.d(TAG, "checkLogin: " + response);
            JSONObject jsonObject = new JSONObject(response);
            int responseError = Integer.valueOf(jsonObject.getString("error")) ;
            if(responseError==-1){
                status = SIGNIN_FAIL;
            }
            if(responseError == 0){
                user = new User(jsonObject);
                user.setEmail(email);
                status = SIGN_IN_SUCCESS;

            }
            return status;
        } catch (IOException e) {
            e.printStackTrace();
            return status;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return status;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return status;
        } catch (JSONException e) {
            e.printStackTrace();
            return status;
        }
    }
    public void logout() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
        keysub = "";
        fullname = "";
        messages = new ArrayList<>();
        currentMessage = new Message();
        onSignIn = false;
        mSocket.disconnect();
        mSocket.off(keyT,onNewMessage);
        mSocket.connect();
    }

    public boolean isOnSignIn(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        String user;
        String pass;
        user = settings.getString("user","");
        pass = settings.getString("pass","");
        if(user!=""){
            if(SignIn(user,pass) == SIGN_IN_SUCCESS);
            return true;
        }
        return false;
    }
//
//    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//        byte[] encrypted = cipher.doFinal(clear);
//        return encrypted;
//    }
//
//    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//        byte[] decrypted = cipher.doFinal(encrypted);
//        return decrypted;
//    }
//    public void testEncrypt() throws Exception {
//        String dkm ="dkm";
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] b= new byte[0];
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            b = dkm.getBytes(StandardCharsets.UTF_8);
//        }
//
//        byte[] keyStart = "this is a key".getBytes();
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        sr.setSeed(keyStart);
//        kgen.init(128, sr); // 192 and 256 bits may not be available
//        SecretKey skey = kgen.generateKey();
//        byte[] key = skey.getEncoded();
//
//// encrypt
//        byte[] encryptedData = encrypt(key,b);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Log.d(TAG, "testEncrypt: decryptedData = "+new String(encryptedData, StandardCharsets.UTF_8));
//        }
//// decrypt
//        byte[] decryptedData = decrypt(key,encryptedData);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Log.d(TAG, "testEncrypt: decryptedData = "+new String(decryptedData, StandardCharsets.UTF_8));
//        }
//    }
//    {
//        try {
//            testEncrypt();
//
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
    //    public ServiceConnection chatServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d(TAG, "onServiceConnected: ");
//            ChatService.LocalChatBinder binder = (ChatService.LocalChatBinder) service;
//
//            chatService = binder.getService();
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };
//
//    public ChatService getChatService() {
//        return chatService;
//    }
}
