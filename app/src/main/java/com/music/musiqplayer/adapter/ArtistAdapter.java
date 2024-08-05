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
import com.music.musiqplayer.R;
import com.music.musiqplayer.data.model.Artist;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {


    private List<Artist> mArtistList;
    private Context mContext;
    private OnArtistSelectedListener onArtistSelectedListener;


    public ArtistAdapter(List<Artist> mArtistList, Context mContext) {
        this.mArtistList = mArtistList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_artist_item,parent, false);
        return new ArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
      Artist mArtist=mArtistList.get(position);

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");
        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, mArtist.getAlbumID());

        Glide.with(mContext)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(holder.mArtistImageItemImage);

        if (mArtist.equals("")) {
            holder.mArtistNameItemText.setText(mContext.getResources().getString(R.string.unknown));
        } else {
            holder.mArtistNameItemText.setText(mArtist.getArtist());
        }
        holder.itemView.setOnClickListener(view -> {
            onArtistSelectedListener.onArtistClicked(mArtist);
        });

    }

    public void setArtistList(List<Artist> artistList) {
        this.mArtistList=artistList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mArtistList != null) {
            return mArtistList.size();
        }
        return 0;
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_artist_artisttem)
        ImageView mArtistImageItemImage;

        @BindView(R.id.text_name_artistItem)
        TextView mArtistNameItemText;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnArtistSelectedListener{
        void onArtistClicked(Artist artist);
    }

    public void setOnArtistSelectedListener(OnArtistSelectedListener onArtistSelectedListener){
        this.onArtistSelectedListener=onArtistSelectedListener;
    }

}



