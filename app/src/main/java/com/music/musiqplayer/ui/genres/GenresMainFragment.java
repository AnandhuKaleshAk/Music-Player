package com.music.musiqplayer.ui.genres;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.music.musiqplayer.R;
import com.music.musiqplayer.ui.base.BaseFragment;

import butterknife.ButterKnife;


public class GenresMainFragment extends BaseFragment {


    FragmentManager mFragmentManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initValues();
        loadFragment(new GenresFragment());
    }



    private void initValues() {
        mFragmentManager = getFragmentManager();
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
            fragmentTransaction.replace(R.id.container_genres, fragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres_main, container, false);
    }
}