package com.music.musiqplayer.ui.albums;

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

import java.util.List;

import butterknife.ButterKnife;


public class AlbumMainFragment extends BaseFragment {


    FragmentManager mFragmentManager;
    private static final String TAG = "AlbumMainFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValues();
        loadFragment(new AlbumFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    private void initValues(){
        Log.d(TAG, "initValues: ");
        mFragmentManager=getFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                List<Fragment> f = mFragmentManager.getFragments();
                Fragment frag = f.get(0);
                String currentFragment = frag.getClass().getSimpleName();
                Log.d(TAG, "onBackStackChanged: "+currentFragment);

            }
        });

    }



    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            String backToStackName = fragment.getClass().getName();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
            fragmentTransaction.addToBackStack(backToStackName);
            fragmentTransaction.replace(R.id.container_album, fragment);

            fragmentTransaction.commit();
        }

    }

}
