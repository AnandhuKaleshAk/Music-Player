package com.music.darkmusicplayer.adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.data.model.Album;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder> {

    private List<Album> mAlbumList;
    private Context mContext;
    private static final String TAG = "AlbumListAdapter";
    private OnAlbumSelectedListener mOnAlbumSelectedListener;

    public AlbumListAdapter(List<Album> mAlbumList, Context mContext) {
        this.mAlbumList = mAlbumList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AlbumListAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_album_list, parent, false);
        return new AlbumViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListAdapter.AlbumViewHolder holder, int position) {
        Album album = mAlbumList.get(position);
        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");
//
        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, album.getAlbumID());
        Log.d(TAG, "onBindViewHolder: " + abumArturi.getPath());
        Glide.with(mContext)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(holder.mAbumImageItemImage);


        if (album.equals("")) {
            holder.mAlbumNameItemText.setText(mContext.getResources().getString(R.string.unknown));
        } else {
            holder.mAlbumNameItemText.setText(album.getAlbum());
        }
        holder.itemView.setOnClickListener(view -> {
                 mOnAlbumSelectedListener.onAlbumSelected(album.getAlbumID());
        });
    }


    public void setmAlbumList(List<Album> albumList) {
        this.mAlbumList = albumList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAlbumList != null) {
            return mAlbumList.size();
        }
        return 0;
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_album_albumItem)
        ImageView mAbumImageItemImage;

        @BindView(R.id.text_name_albumItem)
        TextView mAlbumNameItemText;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

 public interface OnAlbumSelectedListener {
        void onAlbumSelected(long albumId);
    }

    public void setOnAlbumSelectedListener(OnAlbumSelectedListener mOnAlbumSelectedListener) {
        this.mOnAlbumSelectedListener=mOnAlbumSelectedListener;
    }
}
