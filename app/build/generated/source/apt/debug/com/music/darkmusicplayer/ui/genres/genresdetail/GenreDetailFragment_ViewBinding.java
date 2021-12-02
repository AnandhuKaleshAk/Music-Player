// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.genres.genresdetail;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GenreDetailFragment_ViewBinding implements Unbinder {
  private GenreDetailFragment target;

  private View view7f09011a;

  @UiThread
  public GenreDetailFragment_ViewBinding(final GenreDetailFragment target, View source) {
    this.target = target;

    View view;
    target.mAppBarLayout = Utils.findRequiredViewAsType(source, R.id.app_bar, "field 'mAppBarLayout'", AppBarLayout.class);
    target.mToolbarAlbum = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbarAlbum'", Toolbar.class);
    target.mRecyclerViewSongGenre = Utils.findRequiredViewAsType(source, R.id.recyclerview_album_detail, "field 'mRecyclerViewSongGenre'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.image_abumdetail_back, "field 'mBackAlbumDetailImageView' and method 'onBackClicked'");
    target.mBackAlbumDetailImageView = Utils.castView(view, R.id.image_abumdetail_back, "field 'mBackAlbumDetailImageView'", ImageView.class);
    view7f09011a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBackClicked();
      }
    });
    target.mCoverAlbumDetailImageView = Utils.findRequiredViewAsType(source, R.id.image_albumdetail_cover, "field 'mCoverAlbumDetailImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GenreDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mAppBarLayout = null;
    target.mToolbarAlbum = null;
    target.mRecyclerViewSongGenre = null;
    target.mBackAlbumDetailImageView = null;
    target.mCoverAlbumDetailImageView = null;

    view7f09011a.setOnClickListener(null);
    view7f09011a = null;
  }
}
