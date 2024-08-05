package com.music.musiqplayer.ui.genres;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.GenresAdapter;
import com.music.musiqplayer.data.model.Genres;
import com.music.musiqplayer.ui.base.BaseFragment;
import com.music.musiqplayer.ui.genres.genresdetail.GenreDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenresFragment extends BaseFragment  implements IGenreView, GenresAdapter.OnGenreSelectedListener {



    @BindView(R.id.recycler_view_genre)
    RecyclerView mRecyclerViewGenre;
    private GenresAdapter mGenresAdapter;
    private FragmentManager mFragmentManager;
    private static final String GENRE_NAME = "genreName";
    private static final String ALBUM_ID = "albumID";

    private List<Genres> genresList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUp();
        new GenrePresenter(this).onGenreLoaded();
        Disposable songListDispose = ((MyApplication) getActivity().getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            if (it instanceof EventBus.sendSelectedFragment) {
                                EventBus.sendSelectedFragment sendSelectedFragment = (EventBus.sendSelectedFragment) it;

                                if (sendSelectedFragment.getFragmentName().equals("genre")) {
                                    if(genresList.size()==0){
                                        new GenrePresenter(this).onGenreLoaded();
                                    }
                                }
                            }
                        }
                );
        addDisposible(songListDispose);

    }


    private void setUp() {
        mFragmentManager = getFragmentManager();
        mGenresAdapter= new GenresAdapter(null, getActivity());
        mRecyclerViewGenre.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerViewGenre.setAdapter(mGenresAdapter);
        mGenresAdapter.setOnGenreSelectedListener(this);
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onGenresLoaded(List<Genres> genresList) {
          mGenresAdapter.setGenresList(genresList);
          this.genresList.clear();
          this.genresList.addAll(genresList);
    }

    @Override
    public void onGenreClicked(Genres genres) {
        Bundle arguments = new Bundle();
        arguments.putLong(ALBUM_ID,genres.albumID);
        arguments.putString(GENRE_NAME,genres.getGenreName());
        BaseFragment mGenreDetailFragment = new GenreDetailFragment();
        mGenreDetailFragment.setArguments(arguments);
        String backToStackName =  mGenreDetailFragment.getClass().getName();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
        fragmentTransaction.addToBackStack(backToStackName);
        fragmentTransaction.replace(R.id.container_genres,mGenreDetailFragment);
        fragmentTransaction.commit();

    }
}
