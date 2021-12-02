package com.music.darkmusicplayer.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.adapter.SongListAdapter;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.AppRepository;
import com.music.darkmusicplayer.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchSongActivity extends BaseActivity implements ISearchView{

    @BindView(R.id.search_song_editText)
    EditText mSearchSongEditText;

    @BindView(R.id.recyclerview_search)
    RecyclerView mRecyclerViewSongSearch;

    private List<Song> mSongList=new ArrayList<>();
    private SongListAdapter mSongListAdapter;

    private SearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mSearchPresenter=new SearchPresenter(this);
        mRecyclerViewSongSearch.setLayoutManager(new LinearLayoutManager(this));
        mSongListAdapter = new SongListAdapter(mSongList,  this);
        mRecyclerViewSongSearch.setAdapter(mSongListAdapter);

        mSearchSongEditText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           mSearchPresenter.loadSearchSongs(charSequence.toString());
          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });

    }

    @OnClick(R.id.search_back_image)
    public void onSearchBackButtonClicked(){
        finish();
    }


    @OnClick(R.id.search_close_imageview)
    public void onSearchCloseButtonClicked(){
        mSearchSongEditText.setText("");
    }


    @Override
    public void onSongsLoaded(List<Song> songs) {
        mSongListAdapter.setmSongList(songs);
    }

    @Override
    public Context getContext() {
        return this;
    }
}