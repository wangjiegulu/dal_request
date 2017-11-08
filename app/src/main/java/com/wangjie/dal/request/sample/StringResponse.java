package com.wangjie.dal.request.sample;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 08/11/2017.
 */
public class StringResponse {
    private JsonObject coord;
    private JsonArray weather;
    private String base;
    private JsonObject main;

    @Override
    public String toString() {
        return "StringResponse{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                '}';
    }
}
