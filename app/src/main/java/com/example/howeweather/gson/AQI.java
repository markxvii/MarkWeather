package com.example.howeweather.gson;

/**
 * Created by howeyin on 2017/2/13.
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
