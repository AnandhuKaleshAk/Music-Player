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
import com.music.darkmusicplayer.data.model.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<Folder> mFolderList;
    private Context mContext;

    private OnFolderItemClickedListener mOnFolderItemClickedListener;


    public FolderAdapter(List<Folder> mFolderList, Context mContext) {
        this.mFolderList = mFolderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_folder_item, parent, false);
        return new FolderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.FolderViewHolder holder, int position) {
          String noOfsongs=mFolderList.get(position).getNoOfSongs()+" "+mContext.getResources().getString(R.string.no_of_songs);
          holder.mNameFolderItemTextView.setText(mFolderList.get(position).getName());
          holder.mSongPathFolderItemTextView.setText(mFolderList.get(position).getPath());
          holder.mSongCountFolderItemTextView.setText(noOfsongs);
          holder.itemView.setOnClickListener(view -> {
                 mOnFolderItemClickedListener.onFolderClicked(position,mFolderList.get(position).getSongs());
          });




    }

    @Override
    public int getItemCount() {
        if (mFolderList != null) {
            return mFolderList.size();
        }
        return 0;
    }

    public void setFolderList(List<Folder> mFolderList) {
        this.mFolderList=mFolderList;
        notifyDataSetChanged();
    }


    public class FolderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_folderItem_image)
        ImageView mIconFolderItemImageView;

        @BindView(R.id.name_folderItem_text)
        TextView mNameFolderItemTextView;

        @BindView(R.id.pathsong_folderItem_text)
        TextView mSongPathFolderItemTextView;

        @BindView(R.id.songcount_folderItem_text)
        TextView mSongCountFolderItemTextView;


        public FolderViewHolder(@NonNull     View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnFolderItemClickedListener{
       void onFolderClicked(int position, List<Song> mSongList);
    }

    public void setmOnFolderItemClickedListener(OnFolderItemClickedListener mOnFolderItemClickedListener){
        this.mOnFolderItemClickedListener=mOnFolderItemClickedListener;
    }

}
