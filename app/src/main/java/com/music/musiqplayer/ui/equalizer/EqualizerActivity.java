package com.music.musiqplayer.ui.equalizer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.music.musiqplayer.AppUtils;
import com.music.musiqplayer.R;
import com.music.musiqplayer.data.model.Equalizer;

public class EqualizerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AppUtils mAppUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equalizer);
        initiliazeFragment();
        loadEqualizerSettings();
    }

    private void loadEqualizerSettings(){
        mAppUtils=new AppUtils(this);
        EqualizerSettings equalizerSettings=mAppUtils.getEqualizerSettings();
        Equalizer equalizer=new Equalizer();
        equalizer.setBassStrength(equalizerSettings.bassStrength);
        equalizer.setPresetPos(equalizerSettings.presetPos);
        equalizer.setReverbPreset(equalizerSettings.reverbPreset);
        equalizer.setSeekbarpos(equalizerSettings.seekbarpos);

        Settings.isEqualizerEnabled = true;
        Settings.isEqualizerReloaded = true;
        Settings.bassStrength = equalizerSettings.bassStrength;
        Settings.presetPos = equalizerSettings.presetPos;
        Settings.reverbPreset =equalizerSettings.reverbPreset;
        Settings.seekbarpos = equalizerSettings.seekbarpos;
        Settings.equalizerModel = equalizer;
    }

    private void saveEqualizerSettings(){
        if (Settings.equalizerModel != null){
            EqualizerSettings settings = new EqualizerSettings();
            settings.bassStrength = Settings.equalizerModel.getBassStrength();
            settings.presetPos = Settings.equalizerModel.getPresetPos();
            settings.reverbPreset = Settings.equalizerModel.getReverbPreset();
            settings.seekbarpos = Settings.equalizerModel.getSeekbarpos();
            mAppUtils.saveEqualizerSettings(settings);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveEqualizerSettings();
    }

    private void  initiliazeFragment(){
        mediaPlayer = MediaPlayer.create(this, R.raw.lenka);
        final int sessionId = mediaPlayer.getAudioSessionId();
        mediaPlayer.setLooping(true);
        EqualizerFragment equalizerFragment=EqualizerFragment.newBuilder()
                .setAccentColor(Color.parseColor("#4caf50"))
                .setAudioSessionId(sessionId)
                .build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.equalizerFrameLayout, equalizerFragment)
                .commit();
    }


}