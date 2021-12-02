package com.music.darkmusicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.data.model.PlayList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MorePlayListAdapter extends RecyclerView.Adapter<MorePlayListAdapter.MorePlayListViewHolder>{

    private List<PlayList> mPlayLists;
    private Context mContext;

    public OnMorePlayListSelected morePlayListSelected;

    public MorePlayListAdapter(ArrayList<PlayList> mPlayLists, Context mContext) {
        this.mPlayLists = mPlayLists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MorePlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_more_playlist, parent, false);
        return new MorePlayListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MorePlayListViewHolder holder, int position) {
       PlayList playList=mPlayLists.get(position);
       holder.mNameMorePlayListItemText.setText(playList.getPlayListName());
       holder.mNameMorePlayListItemText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               morePlayListSelected.onSelected(playList);
           }
       });
    }


    public void setPlayList(List<PlayList> mPlayList) {
        this.mPlayLists=mPlayList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mPlayLists != null) {
            return mPlayLists.size();
        }
        return 0;
    }

   public interface  OnMorePlayListSelected{
        void onSelected(PlayList playList);
    }

    public void setMorePlayListSelected(OnMorePlayListSelected morePlayListSelected){
        this.morePlayListSelected=morePlayListSelected;
    }



    public class MorePlayListViewHolder extends RecyclerView.ViewHolder {
        //
        @BindView(R.id.name_morePlayList_text)
        TextView mNameMorePlayListItemText;

        public MorePlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
