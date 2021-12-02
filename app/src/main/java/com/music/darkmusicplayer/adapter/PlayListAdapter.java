package com.music.darkmusicplayer.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.more.AddToPlayDialogFragment;
import com.music.darkmusicplayer.ui.more.SongDetailDialogFragment;
import com.music.darkmusicplayer.utils.FileUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    private ArrayList<PlayList> mPlayLists;

    private Context mContext;

    private static final String TAG = "PlayListAdapter";

    private OnPlayListItemClickedListener mOnPlayListItemClickedListener;

    public PlayListAdapter(ArrayList<PlayList> mPlayLists, Context mContext) {
        this.mPlayLists = mPlayLists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_playlist_list, parent, false);
        return new PlayListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        String noOfsongs = mPlayLists.get(position).getNoOfSongs() + " " + mContext.getResources().getString(R.string.no_of_songs);
        holder.mNamePlayListItemText.setText(mPlayLists.get(position).getPlayListName());
        holder.mNoSongsPlayListItemText.setText(noOfsongs);
        if (position == 0) {
            holder.mIconPlayListItemImageView.setImageResource(mPlayLists.get(position).getImageId());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayLists.get(position).getNoOfSongs() != 0) {
                    mOnPlayListItemClickedListener.onPlayListItemSelected(position, mPlayLists.get(position));
                } else {
                  showDeleteDialog(mContext.getResources().getString(R.string.no_songs),mContext.getResources().getString(R.string.title_nosongs));

                }
            }
        });

        if (position >=3) {
            holder.mMoreFolderPlayListImageView.setVisibility(View.VISIBLE);
        } else {
           holder.mMoreFolderPlayListImageView.setVisibility(View.GONE);
        }

        holder.mMoreFolderPlayListImageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.mMoreFolderPlayListImageView);
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

                popupMenu.inflate(R.menu.pop_playlist_menu);
                popupMenu.show();


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_playlist_pop_menu:
                                mOnPlayListItemClickedListener.onDeletePlayListSelected(position,mPlayLists.get(position));
                                return true;
                        }

                        return false;
                    }
                });

            }
        });



    }

    public void showDeleteDialog(String message,String title) {
        new MaterialAlertDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(mContext.getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  FileUtils.deleteTracks(deleteIds,mContext);
                         dialogInterface.dismiss();

                    }
                })
                .setNegativeButton(mContext.getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .show();
    }

    public interface OnPlayListItemClickedListener {
        void onPlayListItemSelected(int position, PlayList mPlayLists);
        void onDeletePlayListSelected(int position,PlayList playList);

    }

    public void setOnPlayListClickListener(OnPlayListItemClickedListener mOnPlayListClickListener) {
        this.mOnPlayListItemClickedListener = mOnPlayListClickListener;
    }

    @Override
    public int getItemCount() {
        if (mPlayLists != null) {
            return mPlayLists.size();
        }
        return 0;
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        //
        @BindView(R.id.name_playListItem_text)
        TextView mNamePlayListItemText;

        @BindView(R.id.noSongs_playListItem_text)
        TextView mNoSongsPlayListItemText;

        @BindView(R.id.icon_playListItem_image)
        ImageView mIconPlayListItemImageView;
//
//
        @BindView(R.id.more_folderPlayListitem_image)
        ImageView mMoreFolderPlayListImageView;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
