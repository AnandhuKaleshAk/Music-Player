package com.music.darkmusicplayer.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.PlayerService;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SongPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Song> mSongList;
    private boolean doNotifyDataSetChangedOnce = false;

    private LayoutInflater layoutInflater;


    @BindView(R.id.icon_playItem_image)
    ImageView mItemAlbumImage;

    @BindView(R.id.name_playItem_text)
    TextView mItemSongNameTextView;

    @BindView(R.id.artist_playItem_text)
    TextView mItemArtistNameTextView;

    @BindView(R.id.image_full_album_item_play)
    ImageView mItemAlbumFullImageView;

    @BindView(R.id.image_play_bottom)
    ImageView mItemPlayPauseImageView;


    private static final String TAG = "SongPagerAdapter";

    public SongPagerAdapter(Context mContext, List<Song> mSongList) {
        this.mContext = mContext;
        this.mSongList = mSongList;
    }


    public void setmSongList(List<Song> mSongList) {
        this.mSongList=mSongList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.now_playing_song, container, false);
        ButterKnife.bind(this, view);

        Log.d(TAG, "instantiateItem: " + PlayerService.isPlaying+"pos"+position);

        if (MainActivity.isDragged) {
            mItemPlayPauseImageView.setVisibility(View.GONE);
        } else {

            mItemPlayPauseImageView.setVisibility(View.VISIBLE);
            if (PlayerService.isPlaying) {
                mItemPlayPauseImageView.setImageResource(R.drawable.ic_pause_button);

            } else {
                mItemPlayPauseImageView.setImageResource(R.drawable.ic_play_button);
            }
        }

        Song song = mSongList.get(position);
        mItemSongNameTextView.setText(song.getDisplayName());
        mItemArtistNameTextView.setText(song.getArtist());
        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");

        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, song.getAlbumID());

        Glide.with(mContext)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(mItemAlbumImage);

        Glide.with(mContext)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(mItemAlbumFullImageView);

       mItemPlayPauseImageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((MyApplication)mContext.getApplicationContext())
                       .bus()
                       .send(new EventBus.PlayPause(position));
           }
       });
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((MyApplication)mContext.getApplicationContext())
                       .bus()
                       .send(new EventBus.SlideLayout(true));
           }
       });
       container.addView(view);

        return view;

    }


    @Override
    public int getCount()
    {
        return mSongList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }


}
