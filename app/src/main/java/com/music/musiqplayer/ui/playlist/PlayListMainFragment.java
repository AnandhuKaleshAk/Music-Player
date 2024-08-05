package com.music.musiqplayer.ui.playlist;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.music.musiqplayer.R;
import com.music.musiqplayer.ui.base.BaseFragment;

import butterknife.ButterKnife;

public class PlayListMainFragment extends BaseFragment {

    FragmentManager mFragmentManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist_main, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initValues();
        loadFragment(new PlayListFragment());
    }




    private void initValues() {
        mFragmentManager = getFragmentManager();
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
            fragmentTransaction.replace(R.id.container_playlist, fragment);
            fragmentTransaction.commit();
        }
    }
}
