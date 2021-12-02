// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.albumdetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class AlbumDetailFragment_ViewBinding implements Unbinder {
  private AlbumDetailFragment target;

  private View view7f09011a;

  private View view7f090127;

  @UiThread
  public AlbumDetailFragment_ViewBinding(final AlbumDetailFragment target, View source) {
    this.target = target;

    View view;
    target.mAppBarLayout = Utils.findRequiredViewAsType(source, R.id.app_bar, "field 'mAppBarLayout'", AppBarLayout.class);
    target.mToolabarLayout = Utils.findRequiredViewAsType(source, R.id.toolBarRelativeLayout, "field 'mToolabarLayout'", RelativeLayout.class);
    target.mToolbarAlbum = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbarAlbum'", Toolbar.class);
    target.mRecyclerViewSongAlbum = Utils.findRequiredViewAsType(source, R.id.recyclerview_album_detail, "field 'mRecyclerViewSongAlbum'", RecyclerView.class);
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
    view = Utils.findRequiredView(source, R.id.image_toolabar_back, "method 'onToolBarBackClicked'");
    view7f090127 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onToolBarBackClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AlbumDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mAppBarLayout = null;
    target.mToolabarLayout = null;
    target.mToolbarAlbum = null;
    target.mRecyclerViewSongAlbum = null;
    target.mBackAlbumDetailImageView = null;
    target.mCoverAlbumDetailImageView = null;

    view7f09011a.setOnClickListener(null);
    view7f09011a = null;
    view7f090127.setOnClickListener(null);
    view7f090127 = null;
  }
}
