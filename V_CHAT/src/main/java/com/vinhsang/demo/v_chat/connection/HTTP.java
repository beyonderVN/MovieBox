package com.vinhsang.demo.v_chat.connection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Long on 6/14/2016.
 */
public class HTTP {
    public static String TAG  = "HTTP";
    private static HTTP ourInstance = new HTTP();

    public static HTTP getInstance() {
        return ourInstance;
    }
    OkHttpClient client ;
    String URL = "http://localhost:8080/Vchat/MessageController/get_list_message.htm?key=khaitamtri&keyR=7_38_44_680&keysub=u1";

    private HTTP() {
        Log.d(TAG, "Create HTTP");
        client = new OkHttpClient();

    }

    public Object run(String url) throws IOException, ExecutionException, InterruptedException {
        return new runTask().execute(url).get();
    }

    public String run2(String url) throws IOException, ExecutionException, InterruptedException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        Log.d(TAG, "run: response:"+ response.body().string());
        return response.body().string();
    }
    class runTask extends AsyncTask<String, Void, Object> {


        private String result;
        protected Object doInBackground(String... urls) {
            try {
                String url = urls[0].toString();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();

                //important : response.body().string() -- can just use once
                result = response.body().string();
                return result;
            } catch (Exception e) {
                Log.e(TAG, "runTask/doInBackground: ",e );
                return e.getMessage();
            }
        }

        protected void onPostExecute(Object feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");



    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
