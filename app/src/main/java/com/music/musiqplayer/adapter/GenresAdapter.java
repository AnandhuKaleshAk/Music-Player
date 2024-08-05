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
import com.music.musiqplayer.data.model.Genres;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

    private List<Genres> mGenresList;
    private Context mContext;
    private OnGenreSelectedListener mOnGenreSelectedListener;

    public GenresAdapter(List<Genres> mGenresList, Context mContext) {
        this.mGenresList = mGenresList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_genres_item, parent, false);
        return new GenreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {

        Genres mGenres = mGenresList.get(position);

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");
        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, mGenres.getAlbumID());

        Glide.with(mContext)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(holder.mGenreImageItemImage);

        if (mGenres.equals("")) {
            holder.mGenreNameItemText.setText(mContext.getResources().getString(R.string.unknown));
        } else {
            holder.mGenreNameItemText.setText(mGenres.getGenreName());
        }
        holder.itemView.setOnClickListener(view -> {
             mOnGenreSelectedListener.onGenreClicked(mGenres);
        });

    }

    @Override
    public int getItemCount() {
        if (mGenresList != null) {
            return mGenresList.size();
        }
        return 0;
    }

    public void setGenresList(List<Genres> mGenresList) {
        this.mGenresList = mGenresList;
        notifyDataSetChanged();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_genre_genreItem)
        ImageView mGenreImageItemImage;

        @BindView(R.id.text_name_genreItem)
        TextView mGenreNameItemText;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnGenreSelectedListener {
        void onGenreClicked(Genres genres);
    }

    public void setOnGenreSelectedListener(OnGenreSelectedListener mOnGenreSelectedListener) {
        this.mOnGenreSelectedListener = mOnGenreSelectedListener;
    }


}
