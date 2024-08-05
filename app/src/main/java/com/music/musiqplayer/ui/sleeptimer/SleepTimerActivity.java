package com.music.musiqplayer.ui.sleeptimer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.music.musiqplayer.AppUtils;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.SleepAdapter;
import com.music.musiqplayer.data.model.SleepTimer;
import com.music.musiqplayer.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SleepTimerActivity extends BaseActivity implements  ISleepTimerView,SleepAdapter.OnSleepSelectedListener{


    @BindView(R.id.recycler_view_timer)
    RecyclerView mRecyclerViewTimer;

    @BindView(R.id.title_toolbar_textView)
    TextView mTitleToolBarTextView;

    @BindView(R.id.back_toolbar_image)
    ImageView mBackImageView;

    @BindView(R.id.onOff_timerManuel_radio)
    RadioButton mTimerManuelRadioButton;


    @BindView(R.id.manualTimerInputEditText)
    EditText  mManualTimerEditText;


    private SleepAdapter mSleepAdapter;

    private List<SleepTimer> mSleepTimerList;
    private AppUtils mAppUtils;
    private static final String TAG = "SleepTimerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_timer);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    private void init(){
        mSleepTimerList=new ArrayList<>();
        mBackImageView.setOnClickListener(view -> {
          finish();
        });
        mTitleToolBarTextView.setText(getResources().getString(R.string.text_title_sleep));
        mSleepAdapter=new SleepAdapter(mSleepTimerList, this);
        mRecyclerViewTimer.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewTimer.setAdapter(mSleepAdapter);
        mSleepAdapter.setOnSleepSelected(this);
        mAppUtils=new AppUtils(this);
    }

    @OnCheckedChanged(R.id.onOff_timerManuel_radio)
    void onTimerSelected(CompoundButton button, boolean checked) {
        String manualTimer=mManualTimerEditText.getText().toString();
        if(!manualTimer.equals("")) {
            SleepTimer sleepTimer = new SleepTimer(manualTimer, true);
            mAppUtils.setTimerOnOFf(true);
            mAppUtils.setTime(Long.parseLong(sleepTimer.getSleepTime()) * 60000, 7);
            stopTimer();
            startTimer(Long.parseLong(sleepTimer.getSleepTime()) * 60000);
            for (int i = 0; i < mSleepTimerList.size(); i++) {
                SleepTimer mSleepTimer = mSleepTimerList.get(i);
                mSleepTimerList.remove(i);
                mSleepTimer.setChecked(false);
                mSleepTimerList.add(i, mSleepTimer);
            }
            mSleepAdapter.setSleepTimerList(mSleepTimerList);
        }
    }
    
    private void loadData(){
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_close),true));
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_10min),false));
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_20min),false));
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_30min),false));
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_60min),false));
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_90min),false));
        mSleepTimerList.add(new SleepTimer(getResources().getString(R.string.text_sleep_120min),false));
        mSleepAdapter.setSleepTimerList(mSleepTimerList);
        if(mAppUtils.getTimerOnOff()){
            int currentTimePosition=mAppUtils.getTimerPosition();
            if(currentTimePosition==7){
                mTimerManuelRadioButton.setChecked(true);
                for(int i=0;i<mSleepTimerList.size();i++){
                    SleepTimer mSleepTimer=mSleepTimerList.get(i);
                    mSleepTimerList.remove(i);
                    mSleepTimer.setChecked(false);
                    mSleepTimerList.add(i,mSleepTimer);
                }
                mSleepAdapter.setSleepTimerList(mSleepTimerList);
            }
            onSleepSelected(mSleepTimerList.get(currentTimePosition),currentTimePosition);
        }
    }




    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSleepSelected(SleepTimer sleepTimer, int position) {
        stopTimer();
        for(int i=0;i<mSleepTimerList.size();i++){
             SleepTimer mSleepTimer=mSleepTimerList.get(i);
             mSleepTimerList.remove(i);
             if(i==position){
                 mSleepTimer.setChecked(true);
             }else{
                 mSleepTimer.setChecked(false);
             }
            mSleepTimerList.add(i,mSleepTimer);
        }
        mTimerManuelRadioButton.setChecked(false);
       mSleepAdapter.setSleepTimerList(mSleepTimerList);

        if(position==0){
            mAppUtils.setTimerOnOFf(false);
            mAppUtils.setTime(0,position);
            stopTimer();
        }else{
            mAppUtils.setTimerOnOFf(true);
            mAppUtils.setTime(Long.parseLong(sleepTimer.getSleepTime())*60000,position);
            stopTimer();
            startTimer(Long.parseLong(sleepTimer.getSleepTime())*60000);
        }
    }
}