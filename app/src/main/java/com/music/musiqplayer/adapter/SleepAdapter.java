package com.music.musiqplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musiqplayer.R;
import com.music.musiqplayer.data.model.SleepTimer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SleepAdapter  extends RecyclerView.Adapter<SleepAdapter.SleepViewHolder> {

    private List<SleepTimer> mSleepTimerList;
    private Context mContext;
     private OnSleepSelectedListener mOnSleepSelectedListener;
    public SleepAdapter(List<SleepTimer> mSleepTimerList, Context mContext) {
        this.mSleepTimerList = mSleepTimerList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SleepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sleep_timer,parent, false);
     return    new SleepViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SleepViewHolder holder, int position) {

        SleepTimer mSleepTimer=mSleepTimerList.get(position);
        holder.mMinuteTimerTextView.setText(mSleepTimer.getSleepTime()+" minutes");
        if(mSleepTimer.getChecked()){
            holder.mOnOffTimerRadioButton.setChecked(true);
        }else{
            holder.mOnOffTimerRadioButton.setChecked(false);
        }

        holder.mOnOffTimerRadioButton.setOnClickListener(view -> {
             mOnSleepSelectedListener.onSleepSelected(mSleepTimer,position);

        });

    }


    @Override
    public int getItemCount() {
        if (mSleepTimerList != null) {
            return mSleepTimerList.size();
        }
        return 0;
    }

    public void setSleepTimerList(List<SleepTimer> sleepTimerList) {
        this.mSleepTimerList=sleepTimerList;
        notifyDataSetChanged();
    }


   public interface OnSleepSelectedListener{
        void onSleepSelected(SleepTimer sleepTimer,int position);
    }

    public void setOnSleepSelected(OnSleepSelectedListener mOnSleepSelectedListener){
        this.mOnSleepSelectedListener=mOnSleepSelectedListener;
    }


    class SleepViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.onOff_timer_radio)
        RadioButton mOnOffTimerRadioButton;

        @BindView(R.id.minute_timerItem_textview)
        TextView mMinuteTimerTextView;

        public SleepViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
