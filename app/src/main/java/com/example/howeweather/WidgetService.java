package com.example.howeweather;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.howeweather.gson.Weather;
import com.example.howeweather.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WidgetService extends Service {
    private Timer timer;
    private SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdf1=new SimpleDateFormat("MM.dd E");
    private Weather weather;
    private String tmp;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                initWeather();
                updateView();
            }
        },0, 300000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer=null;
    }
    private void initWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=prefs.getString("weather",null);
        if (weatherString!=null){
            //有缓存时直接载入天气数据
            weather= Utility.handleWeatherResponse(weatherString);
            String city=weather.basic.cityName;
            String degree=weather.now.temperature+"°C";
            String weatherInfo=weather.now.more.info;
            tmp=city+" : "+weatherInfo+" , "+degree;
        }else{
            tmp="还未选择城市";
        }
    }
    private void updateView(){
        //设置元素
        String time=sdf.format(new Date());
        String date=sdf1.format(new Date());
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.weather_widget);
        remoteViews.setTextViewText(R.id.appwidget_text,time);
        remoteViews.setImageViewResource(R.id.appwidget_image,R.mipmap.weather);
        remoteViews.setTextViewText(R.id.appwidget_text_date,date);
        remoteViews.setTextViewText(R.id.appwidget_text_tem,tmp);
        //添加widget点击事件，点击后返回app
        Intent click=new Intent(getApplicationContext(),reset_activity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,click,0);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_all,pi);
        //结束
        AppWidgetManager manager=AppWidgetManager.getInstance(getApplicationContext());
        ComponentName componentName=new ComponentName(getApplicationContext(), WeatherWidget.class);
        manager.updateAppWidget(componentName,remoteViews);
    }
}
