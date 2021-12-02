package com.music.darkmusicplayer.ui.folder;

import android.util.Log;

import com.music.darkmusicplayer.data.source.AppRepository;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FolderPresenter implements IFolderPresenter {

    private IFolderView mIFolderView;

    private AppRepository mAppRepository;
    private static final String TAG = "FolderPresenter";

    public FolderPresenter(IFolderView mIFolderView) {
        this.mIFolderView = mIFolderView;
        mAppRepository=new AppRepository(mIFolderView.getContext());

    }


    @Override
    public void LoadFolder() {
      //  mIFolderView.showLoading();

        Log.d(TAG, "LoadFolder: ");
        Disposable mFolderDisposible=mAppRepository.getFolders()
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeOn(Schedulers.io())
                   .subscribe(
                           result->{
                          //     mIFolderView.hideLoading();
                               mIFolderView.onFolderLoadedNotDb(result);
                           }
                   );
        mIFolderView.addDisposible(mFolderDisposible);
    }

    public void loadSavedFolder(){
        Log.d(TAG, "LoadFolder: ");
        Disposable mFolderDisposible=mAppRepository.getSavedFolders()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result->{
                            //     mIFolderView.hideLoading();
                            mIFolderView.onFolderLoaded(result);
                            LoadFolder();
                        }
                );
        mIFolderView.addDisposible(mFolderDisposible);
    }

}
