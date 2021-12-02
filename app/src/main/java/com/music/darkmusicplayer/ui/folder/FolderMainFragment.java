package com.music.darkmusicplayer.ui.folder;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.ui.albums.AlbumFragment;
import com.music.darkmusicplayer.ui.base.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;


public class FolderMainFragment extends BaseFragment {

    private FragmentManager mFragmentManager;

    private static final String TAG = "FolderMainFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_folder_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        ButterKnife.bind(this, view);
        initValues();
        loadFragment(new FolderFragments());
    }


    private void initValues() {
        mFragmentManager = getFragmentManager();

    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
            fragmentTransaction.replace(R.id.container_folder, fragment);
            fragmentTransaction.commit();
        }
    }
}
