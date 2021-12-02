package com.music.darkmusicplayer.ui.equalizer;

import com.music.darkmusicplayer.data.model.Equalizer;

public class Settings {
    public static boolean        isEqualizerEnabled  = true;
    public static boolean        isEqualizerReloaded = true;
    public static int[]          seekbarpos          = new int[5];
    public static int            presetPos;
    public static short          reverbPreset        = -1;
    public static short          bassStrength        = -1;
    public static Equalizer equalizerModel;
    public static double         ratio               = 1.0;
    public static boolean        isEditing           = false;
}
