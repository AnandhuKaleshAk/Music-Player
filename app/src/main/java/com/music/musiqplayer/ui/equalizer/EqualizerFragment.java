package com.music.musiqplayer.ui.equalizer;

import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.music.musiqplayer.R;
import com.music.musiqplayer.customviews.AnalogController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EqualizerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EqualizerFragment extends Fragment {

    public static final String ARG_AUDIO_SESSIOIN_ID = "audio_session_id";
    public static int themeColor = Color.parseColor("#cbd124");
    public Equalizer mEqualizer;
    public BassBoost bassBoost;


    SwitchCompat equalizerSwitch;


    public PresetReverb presetReverb;

    @BindView(R.id.back_toolbarEqualizer_image)
    ImageView backEqualizerButton;
    int y = 0;

    @BindView(R.id.hz80Seekar)
    SeekBar mSeekbar80hZ;


    @BindView(R.id.hz230Seekar)
    SeekBar mSeekbar230hZ;





    SeekBar[] seekBarFinal = new SeekBar[5];
    AnalogController bassController;
    AnalogController reverbController;

    private int audioSesionId;

    static boolean showBackButton = true;
    LinearLayout mLinearLayout;

    float[] points;
    short numberOfFrequencyBands;

    public static EqualizerFragment newInstance(int audioSessionId) {
        Bundle args = new Bundle();
        args.putInt(ARG_AUDIO_SESSIOIN_ID, audioSessionId);
        EqualizerFragment fragment = new EqualizerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static Builder newBuilder() {
        return new Builder();
    }


    public static class Builder {
        private int id = -1;

        public Builder setAudioSessionId(int id) {
            this.id = id;
            return this;
        }

        public Builder setAccentColor(int color) {
            themeColor = color;
            return this;
        }

        public Builder setShowbackButton(boolean show) {
            showBackButton = show;
            return this;
        }

        public EqualizerFragment build() {
            return EqualizerFragment.newInstance(id);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUPEqualizer();
    }

    private void setUPEqualizer() {
        Settings.isEditing = true;
        if (getArguments() != null && getArguments().containsKey(ARG_AUDIO_SESSIOIN_ID)) {
            audioSesionId = getArguments().getInt(ARG_AUDIO_SESSIOIN_ID);
        }
        mEqualizer = new Equalizer(0, audioSesionId);
        bassBoost = new BassBoost(0, audioSesionId);
        bassBoost.setEnabled(Settings.isEqualizerEnabled);
        BassBoost.Settings bassBoostSettingsTemp = bassBoost.getProperties();
        BassBoost.Settings bassBoostSetting = new BassBoost.Settings(bassBoostSettingsTemp.toString());

        bassBoostSetting.strength = Settings.equalizerModel.getBassStrength();
        bassBoost.setProperties(bassBoostSetting);

        presetReverb = new PresetReverb(0, audioSesionId);
        presetReverb.setPreset(Settings.equalizerModel.getReverbPreset());
        presetReverb.setEnabled(Settings.isEqualizerEnabled);

        mEqualizer.setEnabled(Settings.isEqualizerEnabled);

        if (Settings.presetPos == 0) {
            for (short bandIdx = 0; bandIdx < mEqualizer.getNumberOfBands(); bandIdx++) {
                mEqualizer.setBandLevel(bandIdx, (short) Settings.seekbarpos[bandIdx]);
            }
        } else {
            mEqualizer.usePreset((short) Settings.presetPos);
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        equalizerSwitch = view.findViewById(R.id.enableDisableEqualizerSwitch);
        equalizerSwitch.setChecked(Settings.isEqualizerEnabled);
        equalizerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEqualizer.setEnabled(isChecked);
                bassBoost.setEnabled(isChecked);
                presetReverb.setEnabled(isChecked);
                Settings.isEqualizerEnabled = isChecked;
                Settings.equalizerModel.setEqualizerEnabled(isChecked);
            }
        });

        bassController = view.findViewById(R.id.controllerBass);
        reverbController = view.findViewById(R.id.controller3D);
        bassController.setLabel(getResources().getString(R.string.text_equalizer_basss));
        reverbController.setLabel(getResources().getString(R.string.text_equalizer_3d));

        bassController.circlePaint.setColor(themeColor);
        bassController.linePaint.setColor(themeColor);
        bassController.invalidate();
        reverbController.circlePaint2.setColor(themeColor);
        bassController.linePaint.setColor(themeColor);
        reverbController.invalidate();

        if (!Settings.isEqualizerReloaded) {
            int x = 0;
            if (bassBoost != null) {
                try {
                    x = ((bassBoost.getRoundedStrength() * 19) / 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (presetReverb != null) {
                try {
                    y = (presetReverb.getPreset() * 19) / 6;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (x == 0) {
                bassController.setProgress(1);
            } else {
                bassController.setProgress(x);
            }

            if (y == 0) {
                reverbController.setProgress(1);
            } else {
                reverbController.setProgress(y);
            }
        } else {
            int x = ((Settings.bassStrength * 19) / 1000);
            y = (Settings.reverbPreset * 19) / 6;
            if (x == 0) {
                bassController.setProgress(1);
            } else {
                bassController.setProgress(x);
            }

            if (y == 0) {
                reverbController.setProgress(1);
            } else {
                reverbController.setProgress(y);
            }
        }

        bassController.setOnProgressChangedListener(new AnalogController.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                Settings.bassStrength = (short) (((float) 1000 / 19) * (progress));
                try {
                    bassBoost.setStrength(Settings.bassStrength);
                    Settings.equalizerModel.setBassStrength(Settings.bassStrength);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        reverbController.setOnProgressChangedListener(new AnalogController.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                Settings.reverbPreset = (short) ((progress * 6) / 19);
                Settings.equalizerModel.setReverbPreset(Settings.reverbPreset);
                try {
                    presetReverb.setPreset(Settings.reverbPreset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                y = progress;
            }
        });

        mLinearLayout = view.findViewById(R.id.equalizerContainer);

        numberOfFrequencyBands = 5;
        points = new float[numberOfFrequencyBands];

        final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
        final short upperEqualizerBandLevel = mEqualizer.getBandLevelRange()[1];
        mSeekbar80hZ.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
          if (Settings.isEqualizerReloaded) {
                points[0] = Settings.seekbarpos[0] - lowerEqualizerBandLevel;
                mSeekbar80hZ.setProgress(Settings.seekbarpos[0] - lowerEqualizerBandLevel);
            } else {
                points[0] = mEqualizer.getBandLevel((short) 0) - lowerEqualizerBandLevel;
                mSeekbar80hZ.setProgress(mEqualizer.getBandLevel((short) 0) - lowerEqualizerBandLevel);
                Settings.seekbarpos[0] = mEqualizer.getBandLevel((short) 0);
                Settings.isEqualizerReloaded = true;
            }

        mSeekbar80hZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    System.out.println("progretess"+lowerEqualizerBandLevel);
                    mEqualizer.setBandLevel((short) 0, (short) (progress + lowerEqualizerBandLevel));
                    System.out.println("band level"+mEqualizer.getBandLevel((short) 0));
                    points[0] = mEqualizer.getBandLevel((short) 0) - lowerEqualizerBandLevel;
                    Settings.seekbarpos[0] = (progress + lowerEqualizerBandLevel);
                    Settings.equalizerModel.getSeekbarpos()[0] = (progress + lowerEqualizerBandLevel);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Settings.presetPos = 0;
                    Settings.equalizerModel.setPresetPos(0);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


        mSeekbar230hZ.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
        if (Settings.isEqualizerReloaded) {
            points[1] = Settings.seekbarpos[1] - lowerEqualizerBandLevel;
            mSeekbar230hZ.setProgress(Settings.seekbarpos[1] - lowerEqualizerBandLevel);
        } else {
            points[1] = mEqualizer.getBandLevel((short)1) - lowerEqualizerBandLevel;
            mSeekbar80hZ.setProgress(mEqualizer.getBandLevel((short) 1) - lowerEqualizerBandLevel);
            Settings.seekbarpos[1] = mEqualizer.getBandLevel((short) 1);
            Settings.isEqualizerReloaded = true;
        }

        mSeekbar230hZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println("progretess"+lowerEqualizerBandLevel);
                mEqualizer.setBandLevel((short) 1, (short) (progress + lowerEqualizerBandLevel));
                System.out.println("band level"+mEqualizer.getBandLevel((short) 1));
                points[1] = mEqualizer.getBandLevel((short) 1) - lowerEqualizerBandLevel;
                Settings.seekbarpos[1] = (progress + lowerEqualizerBandLevel);
                Settings.equalizerModel.getSeekbarpos()[1] = (progress + lowerEqualizerBandLevel);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Settings.presetPos = 0;
                Settings.equalizerModel.setPresetPos(0);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





//        for (short i = 0; i < numberOfFrequencyBands; i++) {
//
//            final short equalizerBandIndex = i;
//            final TextView frequencyHeaderTextView = new TextView(getContext());
//            frequencyHeaderTextView.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ));
//            frequencyHeaderTextView.setGravity(Gravity.CENTER_HORIZONTAL);
//            frequencyHeaderTextView.setTextColor(Color.parseColor("#FFFFFF"));
//            frequencyHeaderTextView.setText((mEqualizer.getCenterFreq(equalizerBandIndex) / 1000) + "Hz");
//
//            LinearLayout seekBarRowLayout = new LinearLayout(getContext());
//            seekBarRowLayout.setOrientation(LinearLayout.VERTICAL);
//
//            TextView lowerEqualizerBandLevelTextView = new TextView(getContext());
//            lowerEqualizerBandLevelTextView.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//            ));
//            lowerEqualizerBandLevelTextView.setTextColor(Color.parseColor("#FFFFFF"));
//            lowerEqualizerBandLevelTextView.setText((lowerEqualizerBandLevel / 100) + "dB");
//
//            TextView upperEqualizerBandLevelTextView = new TextView(getContext());
//            lowerEqualizerBandLevelTextView.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            ));
//            upperEqualizerBandLevelTextView.setTextColor(Color.parseColor("#FFFFFF"));
//            upperEqualizerBandLevelTextView.setText((upperEqualizerBandLevel / 100) + "dB");
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            layoutParams.weight = 1;
//
//            SeekBar seekBar = new SeekBar(getContext());
//            TextView textView = new TextView(getContext());
//            switch (i) {
//                case 0:
//                    seekBar = view.findViewById(R.id.hz80Seekar);
//                    textView = view.findViewById(R.id.hz80TextView);
//                    break;
//                case 1:
//                    seekBar = view.findViewById(R.id.hz230Seekar);
//                    textView = view.findViewById(R.id.hz230TextView);
//                    break;
//                case 2:
//                    seekBar = view.findViewById(R.id.hz910Seekar);
//                    textView = view.findViewById(R.id.hz910TextView);
//                    break;
//                case 3:
//                    seekBar = view.findViewById(R.id.hz3600Seekar);
//                    textView = view.findViewById(R.id.hz3600TextView);
//                    break;
//                case 4:
//                    seekBar = view.findViewById(R.id.hz14000Seekar);
//                    textView = view.findViewById(R.id.hz14000TextView);
//                    break;
//            }
//            seekBarFinal[i] = seekBar;
//            seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN));
//            seekBar.getThumb().setColorFilter(new PorterDuffColorFilter(themeColor, PorterDuff.Mode.SRC_IN));
//            seekBar.setId(i);
////            seekBar.setLayoutParams(layoutParams);
//            seekBar.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);
//
//            textView.setText(frequencyHeaderTextView.getText());
//            textView.setTextColor(Color.WHITE);
//            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//            if (Settings.isEqualizerReloaded) {
//                points[i] = Settings.seekbarpos[i] - lowerEqualizerBandLevel;
//                seekBar.setProgress(Settings.seekbarpos[i] - lowerEqualizerBandLevel);
//            } else {
//                points[i] = mEqualizer.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel;
//                seekBar.setProgress(mEqualizer.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel);
//                Settings.seekbarpos[i] = mEqualizer.getBandLevel(equalizerBandIndex);
//                Settings.isEqualizerReloaded = true;
//            }
//
//            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                    System.out.println("progretess"+lowerEqualizerBandLevel);
//
//                    mEqualizer.setBandLevel(equalizerBandIndex, (short) (progress + lowerEqualizerBandLevel));
//
//                    System.out.println("band level"+mEqualizer.getBandLevel(equalizerBandIndex));
//
//                    points[seekBar.getId()] = mEqualizer.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel;
//                    Settings.seekbarpos[seekBar.getId()] = (progress + lowerEqualizerBandLevel);
//                    Settings.equalizerModel.getSeekbarpos()[seekBar.getId()] = (progress + lowerEqualizerBandLevel);
//
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//                    Settings.presetPos = 0;
//                    Settings.equalizerModel.setPresetPos(0);
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//
//                }
//            });
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_equalizer, container, false);
    }
}