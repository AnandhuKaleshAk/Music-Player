package com.music.darkmusicplayer.ui.more;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.BaseDialogFragment;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SongDetailDialogFragment extends BaseDialogFragment {


    @BindView(R.id.path_moreDetails_textview)
    TextView mMorePathDetails;

    @BindView(R.id.time_moreDetails_textview)
    TextView mTimeMoreDetails;


    @BindView(R.id.size_moreDetails_textview)
    TextView mSizeMoreDetails;

    @BindView(R.id.title_moreDetails_textview)
    TextView mTitleMoreDetails;


    @BindView(R.id.album_moreDetails_textview)
    TextView mAlbumMoreDetails;

    @BindView(R.id.artist_moreDetails_textview)
    TextView mArtistMoreDetails;







    public static final String SONG = "song";
    Song song;

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            song = (Song) bundle.getSerializable(SONG);
        }
        setUpData();
    }

    private void setUpData(){
      mMorePathDetails.setText(song.getPath());
      mTimeMoreDetails.setText(getTimeInSeconds());
      mSizeMoreDetails.setText(getFileSize(song.getSize()));
      mTitleMoreDetails.setText(song.getTitle());
      mAlbumMoreDetails.setText(song.getAlbum());
      mArtistMoreDetails.setText(song.getArtist());
    }

    public String getTimeInSeconds(){
        int secTimeCurrent = Integer.parseInt(String.valueOf(song.getDuration()/ 1000));
        int minuteCurrent = secTimeCurrent / 60;
        secTimeCurrent = secTimeCurrent % 60;
        return String.format("%02d:%02d", minuteCurrent, secTimeCurrent);
    }

    @OnClick(R.id.cancel_moreDetail_textview)
    public void cancelMoreDetail(){
      dismiss();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_detail_dialog, container, false);

    }


    public String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }


}