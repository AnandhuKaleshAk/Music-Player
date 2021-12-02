package com.music.darkmusicplayer.ui.more;

import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MorePlayListPresenter implements IMorePlayListPresenter {


    private AppRepository mAppRepository;
    private static final String TAG = "MorelayListPresenter";
    private IMorePlayListView morePlayListView;

    public MorePlayListPresenter(IMorePlayListView morePlayListView) {
        this.morePlayListView = morePlayListView;
        mAppRepository=new AppRepository(morePlayListView.getContext());
    }

    @Override
    public void loadPlayList() {
        Disposable getPlayListDisposible=mAppRepository.getPlaylist()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result->{
                    morePlayListView.onMorePlayListLoaded(result);
                        }
                        );
      morePlayListView.addDisposible(getPlayListDisposible);
    }
    public void updatePlayList(PlayList mPlayList){
        Disposable getPlayListDisposible=mAppRepository.updatePlaylist(mPlayList)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(result->{

                        }
                );
        morePlayListView.addDisposible(getPlayListDisposible);

    }



    public void insertPlayList(PlayList playList){
        Disposable mCreatePlayList=mAppRepository.insertPlayList(playList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result->{
                          //  morePlayListView.onMorePlayListCreated();
                        }
                );

        morePlayListView.addDisposible(mCreatePlayList);
    }
}
