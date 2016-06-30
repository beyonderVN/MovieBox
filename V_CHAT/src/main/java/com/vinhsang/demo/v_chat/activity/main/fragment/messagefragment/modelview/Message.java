package com.vinhsang.demo.v_chat.activity.main.fragment.messagefragment.modelview;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    //flag for determining layout for message
    public static final String TAG = "Message";

    public static final int NONE = -99;
    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_LOG = 1;
    public static final int TYPE_ACTION = 2;
    public static final int TYPE_CHAT_MESSAGE = 3;
    public static final int TYPE_MY_CHAT_MESSAGE = 4;

    public static final int R_SUCCESS = 0;
    public static final int R_ONLINE_CLIENT = 1;
    public static final int R_ERROR = -1;
    public static final int R_FROM_CLIENT = 2;
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

    public String getmKeyR() {
        return mKeyR;
    }

    public void setmKeyR(String mKeyR) {
        this.mKeyR = mKeyR;
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

    public Message() {}

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

        public Message build() {
            Message message = new Message();
            message.mType = mType;
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            return message;
        }
        public Message build(JSONObject data) {
            JSONObject jsonObject = data;
            Message message = new Message();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat hourFormat = new SimpleDateFormat("HH:mm");

            String dateString,hourString;

            Date currentDate = new Date();
            dateString = dateFormat.format(currentDate);
            hourString = hourFormat.format(currentDate);


            try {
                int role= -1 ;
                //

                role = Integer.valueOf(jsonObject.has("R")&&!jsonObject.isNull("R")?jsonObject.getString("R"):"-2") ;
                if(jsonObject.has("date")){

                    dateString = jsonObject.getString("date");


                }
                if(jsonObject.has("hour")){
                    hourString = jsonObject.getString("hour");
                    SimpleDateFormat fromFormat = new SimpleDateFormat("hh:mm:ss:SSS");
                    SimpleDateFormat myFormat = new SimpleDateFormat("hh:mm");

                    hourString = myFormat.format(fromFormat.parse(hourString));

                }
                if(jsonObject.has("key")){
                    message.setmKeyR(jsonObject.getString("key"));
                }
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
                    case R_ONLINE_CLIENT:
                        message.mType = TYPE_MESSAGE;
                        message.mUsername = jsonObject.getString("fullname") ;
                        message.mMessage = "is Online";
                        message.mKeyR = jsonObject.getString("keyR");
                        message.mDate = dateString;
                        message.mHour = hourString;
                        message.newNumber = 1;
                        break;
                    case R_FROM_CLIENT:
                        message.mType = TYPE_MESSAGE;
                        message.mUsername = jsonObject.getString("fullname");
                        message.mMessage = jsonObject.getString("message");
                        message.mKeyR = jsonObject.getString("keyR");
                        message.mKeyRC = jsonObject.getString("keyRC");
                        message.mDate = dateString;
                        message.mHour = hourString;
                        message.newNumber = 1;
                        break;
                    case R_FROM_ME:
                        message.mType = NONE;
                        message.mUsername = "";
                        message.mMessage = jsonObject.getString("message");
                        message.mKeyR = jsonObject.getString("keyR");
                        message.mDate = dateString;
                        message.mHour = hourString;
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
                Log.e(TAG, "build: ",e );
                message.mType = TYPE_LOG;
                message.mMessage = jsonObject.toString();
                message.mDate = dateString;
                message.mHour = hourString;
                return message;
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e(TAG, "build: ",e );
                message.mType = TYPE_LOG;
                message.mMessage = jsonObject.toString() + e.getMessage();
                message.mDate = dateString;
                message.mHour = hourString;
                return message;
            }


        }
    }
}
