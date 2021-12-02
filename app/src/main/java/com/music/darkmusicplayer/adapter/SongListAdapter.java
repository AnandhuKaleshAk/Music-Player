package com.music.darkmusicplayer.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.music.darkmusicplayer.AppUtils;
import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.R;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.main.MainActivity;
import com.music.darkmusicplayer.ui.more.AddToPlayDialogFragment;
import com.music.darkmusicplayer.ui.more.SongDetailDialogFragment;
import com.music.darkmusicplayer.utils.FileUtils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {
    private List<Song> mSongList;
    private Context mContext;
    private static final String TAG = "SongListAdapter";
    private AppUtils mAppUtils;

    public static final String SONG = "song";


    public SongListAdapter(List<Song> mSongList, Context mContext) {
        this.mSongList = mSongList;
        this.mContext = mContext;
        mAppUtils=new AppUtils(mContext);
    }

    @NonNull
    @Override
    public SongListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_song_list, parent, false);
        return new SongListViewHolder(itemView);
    }


    public void setmSongList(List<Song> songList) {
        this.mSongList = songList;
        notifyDataSetChanged();
    }
    public void clearSongs(){
        this.mSongList.clear();
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull SongListViewHolder holder, int position) {
        Song song = mSongList.get(position);
        holder.mSongNameItemText.setText(song.getDisplayName());
        holder.mSongArtistItemText.setText(song.getArtist());


        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");


        Uri albumArturi = ContentUris.withAppendedId(sArtworkUri, song.getAlbumID());


        Glide.with(mContext)
                .load(albumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(holder.mSongAlbumArtItemImage);
        Log.d(TAG, "onBindViewHolder: " + albumArturi.getPath());


        holder.itemView.setOnClickListener(v -> {

            ((MyApplication) mContext.getApplicationContext())
                    .bus()
                    .send(new EventBus.SendSongList(position, mSongList));

        });

        holder.mMoreOptionImageView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.mMoreOptionImageView);
            popupMenu.setGravity(Gravity.END);
            try {
                Field[] fields = popupMenu.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popupMenu);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            popupMenu.inflate(R.menu.pop_menu);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.play_pop_menu:
                            ((MyApplication) mContext.getApplicationContext())
                                    .bus()
                                    .send(new EventBus.SendSongList(position, mSongList));
                            popupMenu.dismiss();
                            return true;

                        case R.id.next_pop_menu:
                            ((MyApplication) mContext.getApplicationContext())
                                    .bus()
                                    .send(new EventBus.SendSongList(position + 1, mSongList));
                            popupMenu.dismiss();
                            return true;

                        case R.id.playlist_pop_menu:
                            FragmentActivity activity = (FragmentActivity) (mContext);
                            FragmentManager fm = activity.getSupportFragmentManager();
                            AddToPlayDialogFragment addToPlayDialogFragment = new AddToPlayDialogFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(SONG, mSongList.get(position));
                            addToPlayDialogFragment.setArguments(bundle);
                            addToPlayDialogFragment.show(fm, "fragment_morePlaylist");
                            return true;

                        case R.id.detail_pop_menu:
                            FragmentActivity activityDetail = (FragmentActivity) (mContext);
                            FragmentManager fmDetail = activityDetail.getSupportFragmentManager();
                            SongDetailDialogFragment songDetailDialogFragment = new SongDetailDialogFragment();
                            Bundle bundleDetail = new Bundle();
                            bundleDetail.putSerializable(SONG, mSongList.get(position));
                            songDetailDialogFragment.setArguments(bundleDetail);
                            songDetailDialogFragment.show(fmDetail, "fragment_detaillaylist");
                            return true;

                        case R.id.share_pop_menu:
                            FileUtils.shareTrack(mContext, song.getId());
                            return true;

                        case R.id.delete_pop_menu:
                            long[] deleteIds = {song.getSongId()};
                            showDeleteDialog(song,deleteIds,position);
                            return true;

                    }

                    return false;
                }
            });


        });
    }

    @Override
    public int getItemCount() {
        if (mSongList != null) {
            return mSongList.size();
        }
        return 0;
    }

    public void showDeleteDialog(Song song,long[] deleteIds,int position) {
        new MaterialAlertDialogBuilder(mContext)
                .setTitle(mContext.getString(R.string.text_delete_song))
                .setMessage(mContext.getString(R.string.text_deleted_message) + song.getTitle()+"?")
                .setPositiveButton(mContext.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     //  FileUtils.deleteTracks(deleteIds,mContext);
                        removeTrack(song,position,deleteIds);

                    }
                })
                .setNegativeButton(mContext.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    public void removeTrack(Song song,int position,long[] deleteIds){
        //remove the current track and play the next song in queue

        int currentPlayingPosition=mAppUtils.loadAudioIndex();
        mSongList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount());
        ((MyApplication) mContext.getApplicationContext())
                .bus()
                .send(new EventBus.SendDeleteList(position, mSongList));


        //Delete the song from the list
     //   FileUtils.deleteTracks(deleteIds,mContext);


    }


    public class SongListViewHolder extends RecyclerView.ViewHolder {
        //
        @BindView(R.id.name_songItem_text)
        TextView mSongNameItemText;

        @BindView(R.id.artist_songItem_text)
        TextView mSongArtistItemText;

        @BindView(R.id.icon_songItem_image)
        ImageView mSongAlbumArtItemImage;

        @BindView(R.id.more_folderitem_image)
        ImageView mMoreOptionImageView;


        public SongListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
