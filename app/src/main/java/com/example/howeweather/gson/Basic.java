package com.example.howeweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by howeyin on 2017/2/13.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
