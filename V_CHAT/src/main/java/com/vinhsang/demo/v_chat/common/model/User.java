package com.vinhsang.demo.v_chat.common.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Long on 6/20/2016.
 */

public class User {
    private String name = "";
    private String parent = "";
    private String email = "";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public User(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }
    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(JSONObject jsonObject) {
        try {
            if (jsonObject.has("name")) {

                this.name = jsonObject.getString("name");
            }
            if (jsonObject.has("parent")) {
                this.parent = jsonObject.getString("parent");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
