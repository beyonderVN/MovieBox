package com.vinhsang.demo.v_chat.activity.chatbox.fragment.modelview;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    public static final String TAG = "ChatMessage";

    //flag for determining layout for message
    public static final int TYPE_LOG = 0;
    public static final int TYPE_CLIENT_MESSAGE = 1;
    public static final int TYPE_MY_MESSAGE = 2;

    //Role cho client va user
    public static final int ROLE_CLIENT = 1;
    public static final int ROLE_MY = 0;

    //flag R on message
    public static final int R_SUCCESS = 0;
    public static final int R_FROM_CLIENT = 2;
    public static final int R_ERROR = -1;

    public static final int R_TYPE_CHAT_MESSAGE = -99;
    public static final int R_FROM_ME = 3;


    public static final String KEY_RC = "KEY_RC";
    public static final String FULLNAME = "FULLNAME";

    private int R;

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    private int mType;

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    private String mMessage="";
    private String mUsername="";
    private String mKey="",mKeyR="",mKeyC="",mKeyRC="";

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    private String mDate,mHour;

    public int getNewNumber() {
        return newNumber;
    }

    public void setNewNumber(int newNumber) {
        this.newNumber = newNumber;
    }

    private int newNumber;

    public String getmKeyRC() {
        return mKeyRC;
    }

    public void setmKeyRC(String mKeyRC) {
        this.mKeyRC = mKeyRC;
    }

    public ChatMessage() {}

    public int getType() {
        return mType;
    };

    public String getMessage() {
        return mMessage;
    };

    public String getUsername() {
        return mUsername;
    };
    public void setUsername(String username) {
        mUsername =username;
    };

    public int getTypeMessage() {
        return mType;
    }

    public String getmHour() {
        return mHour;
    }

    public void setmHour(String mHour) {
        this.mHour = mHour;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public static class Builder {
        private int mType;
        private String mUsername;
        private String mMessage;
        private String mKey,mKeyRC,mKeyC,mDate,mHour;

        public Builder() {

        }
        public Builder(int type) {
            mType = type;
        }

        public Builder username(String username) {
            mUsername = username;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public ChatMessage build() {
            ChatMessage message = new ChatMessage();
            message.mType = mType;
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            return message;
        }
        public ChatMessage build(JSONObject data) {
            JSONObject jsonObject = data;
            ChatMessage message = new ChatMessage();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat hourFormat = new SimpleDateFormat("HH:mm");

            String dateString,hourString;

            Date currentDate = new Date();
            dateString = dateFormat.format(currentDate);
            hourString = hourFormat.format(currentDate);


            try {
                int role= -1 ;
                //
                role = Integer.valueOf(jsonObject.has("R")&&!jsonObject.isNull("R")?jsonObject.getString("R"):"-99") ;

                message.setR(role);
                switch (role){
                    case R_SUCCESS:
                    case R_ERROR:
                        message.mType = TYPE_LOG;
                        message.mUsername = "System";
                        message.mMessage = jsonObject.getString("message");
                        message.mDate = dateString;
                        message.mHour = hourString;

                        break;
                    case R_TYPE_CHAT_MESSAGE:
                        int roleChatMessage ;
                        roleChatMessage = Integer.valueOf(jsonObject.getString("role"));
                        if(roleChatMessage==ROLE_CLIENT){
                            message.mType = TYPE_CLIENT_MESSAGE;
                        }else{
                            message.mType = TYPE_MY_MESSAGE;
                        }
                        message.mMessage = jsonObject.getString("message");
                        message.mKeyR = jsonObject.getString("keyR");
                        message.mDate = jsonObject.has("date")?jsonObject.getString("date"):dateString;
                        message.mHour = jsonObject.has("hour")?jsonObject.getString("hour"):hourString;
                        message.newNumber = 1;
                        break;

                    case R_FROM_CLIENT:

                        message.mType = TYPE_CLIENT_MESSAGE;

                        message.mMessage = jsonObject.getString("message");
                        message.mKeyR = jsonObject.getString("keyR");
                        message.mKeyRC = jsonObject.getString("keyRC");
                        message.mDate = jsonObject.getString("date");
                        message.mHour = jsonObject.getString("hour");
                        message.newNumber = 1;
                        break;
                    case R_FROM_ME:

                        message.mType = TYPE_MY_MESSAGE;

                        message.mMessage = jsonObject.getString("message");
                        message.mKeyR = jsonObject.getString("keyR");
                        message.mDate = jsonObject.getString("date");
                        message.mHour = jsonObject.getString("hour");
                        message.newNumber = 1;
                        break;
                    default:
                        message.mType = TYPE_LOG;
                        message.mMessage = jsonObject.toString();
                        message.mDate = dateString;
                        message.mHour = hourString;
                        break;

                }

                return message;
            } catch (JSONException e) {
                Log.e(TAG, "build: "+message.getR()+": "+e.getMessage() );
                message.mType = TYPE_LOG;
                message.mMessage = jsonObject.toString();
                message.mDate = dateString;
                message.mHour = hourString;
                return message;
            }


        }
    }
}
