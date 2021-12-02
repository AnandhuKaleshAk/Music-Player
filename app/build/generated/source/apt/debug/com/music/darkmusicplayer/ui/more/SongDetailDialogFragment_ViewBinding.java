// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.more;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SongDetailDialogFragment_ViewBinding implements Unbinder {
  private SongDetailDialogFragment target;

  private View view7f09006d;

  @UiThread
  public SongDetailDialogFragment_ViewBinding(final SongDetailDialogFragment target, View source) {
    this.target = target;

    View view;
    target.mMorePathDetails = Utils.findRequiredViewAsType(source, R.id.path_moreDetails_textview, "field 'mMorePathDetails'", TextView.class);
    target.mTimeMoreDetails = Utils.findRequiredViewAsType(source, R.id.time_moreDetails_textview, "field 'mTimeMoreDetails'", TextView.class);
    target.mSizeMoreDetails = Utils.findRequiredViewAsType(source, R.id.size_moreDetails_textview, "field 'mSizeMoreDetails'", TextView.class);
    target.mTitleMoreDetails = Utils.findRequiredViewAsType(source, R.id.title_moreDetails_textview, "field 'mTitleMoreDetails'", TextView.class);
    target.mAlbumMoreDetails = Utils.findRequiredViewAsType(source, R.id.album_moreDetails_textview, "field 'mAlbumMoreDetails'", TextView.class);
    target.mArtistMoreDetails = Utils.findRequiredViewAsType(source, R.id.artist_moreDetails_textview, "field 'mArtistMoreDetails'", TextView.class);
    view = Utils.findRequiredView(source, R.id.cancel_moreDetail_textview, "method 'cancelMoreDetail'");
    view7f09006d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancelMoreDetail();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SongDetailDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mMorePathDetails = null;
    target.mTimeMoreDetails = null;
    target.mSizeMoreDetails = null;
    target.mTitleMoreDetails = null;
    target.mAlbumMoreDetails = null;
    target.mArtistMoreDetails = null;

    view7f09006d.setOnClickListener(null);
    view7f09006d = null;
  }
}
