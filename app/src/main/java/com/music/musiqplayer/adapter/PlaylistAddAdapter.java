package com.music.musiqplayer.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.data.model.Song;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistAddAdapter extends RecyclerView.Adapter<PlaylistAddAdapter.PlayListAddViewHolder> {


    private List<Song> mSongList;

    private static final String TAG = "PlaylistAddAdapter";
    private Context mContext;

    private HashMap<Integer,Boolean> mSelectedSongs=new HashMap<>();


    public PlaylistAddAdapter(List<Song> mSongList, Context mContext) {
        this.mSongList = mSongList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlayListAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_playlist_select, parent, false);
        return new PlayListAddViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListAddViewHolder holder, int position) {
        Song song = mSongList.get(position);
        holder.mSongNameAddItemText.setText(song.getDisplayName());
        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");

        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, song.getAlbumID());

        Glide.with(mContext)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(holder.mSongAlbumArtAddItemImage);

        if(song.getChecked()){
            holder.mSongSelectAddItemImage.setVisibility(View.VISIBLE);
        }else{
            holder.mSongSelectAddItemImage.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(view -> {
            if(!song.getChecked()){
                song.setChecked(true);
                mSongList.remove(position);
                mSongList.add(position,song);
                notifyDataSetChanged();
                ((MyApplication)mContext.getApplicationContext())
                        .bus()
                        .send(new EventBus.sendAddedSongs(position,mSongList));
            }else{
                song.setChecked(false);
                mSongList.remove(position);
                mSongList.add(position,song);
                notifyDataSetChanged();
                ((MyApplication)mContext.getApplicationContext())
                        .bus()
                        .send(new EventBus.sendAddedSongs(position,mSongList));

            }

        });

    }

    @Override
    public int getItemCount() {
        if (mSongList != null) {
            return mSongList.size();
        }
        return 0;
    }

    public class PlayListAddViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.name_songAddItem_text)
        TextView mSongNameAddItemText;


        @BindView(R.id.icon_songAddItem_image)
        ImageView mSongAlbumArtAddItemImage;


        @BindView(R.id.select_songAddItem_image)
        ImageView mSongSelectAddItemImage;


        public PlayListAddViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setSongList(List<Song> songList) {
        this.mSongList = songList;
        notifyDataSetChanged();
    }

}
