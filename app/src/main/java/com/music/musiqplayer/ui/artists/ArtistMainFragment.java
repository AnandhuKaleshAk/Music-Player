package com.music.musiqplayer.ui.artists;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.music.musiqplayer.R;
import com.music.musiqplayer.ui.base.BaseFragment;

import butterknife.ButterKnife;

public class ArtistMainFragment extends BaseFragment {

    FragmentManager mFragmentManager;
    private static final String TAG = "ArtistMainFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onViewCreated: artist main");
        initValues();
        loadFragment(new ArtistFragment());
    }


    private void initValues() {
        mFragmentManager = getFragmentManager();
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
            fragmentTransaction.replace(R.id.container_artist, fragment);
            fragmentTransaction.commit();
        }
    }




}