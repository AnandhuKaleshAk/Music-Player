package com.music.darkmusicplayer;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.equalizer.EqualizerSettings;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    private SharedPreferences preferences;
    private Context context;

    public AppUtils(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE);
    }

    public EqualizerSettings getEqualizerSettings(){
        Gson gson = new Gson();
        return  gson.fromJson(preferences.getString(Constants.EQUALIZER, "{}"), EqualizerSettings.class);
    }

    public void saveEqualizerSettings(EqualizerSettings equalizerSettings){
        Gson gson = new Gson();
        preferences.edit().putString(Constants.EQUALIZER, gson.toJson(equalizerSettings)).apply();
    }

    public void storeAudio(List<Song>  list){
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(Constants.AUDIO_LIST, json);
        editor.apply();
    }
    public List<Song> loadAudio() {
        Gson gson = new Gson();
        String json = preferences.getString(Constants.AUDIO_LIST, null);
        Type type = new TypeToken<List<Song>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void setTimerOnOFf(boolean isOnOff){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.TIMER_ONOFF, isOnOff);
        editor.apply();
    }

    public void setTime(long time,int position){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(Constants.AUDIO_INDEX,time);
        editor.putInt(Constants.TIMER_INDEX,position);
        editor.apply();
    }

    public int getTimerPosition(){
        return preferences.getInt(Constants.TIMER_INDEX,0);
    }


    public Long getTime(){
        return preferences.getLong(Constants.TIME,0);
    }
    public boolean getTimerOnOff(){
        return preferences.getBoolean(Constants.TIMER_ONOFF, false);
    }

    public void storeAudioIndex(int index) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.AUDIO_INDEX, index);
        editor.apply();
    }

    public int loadAudioIndex() {
        return preferences.getInt(Constants.AUDIO_INDEX, -0);//return -1 if no data found
    }

}
