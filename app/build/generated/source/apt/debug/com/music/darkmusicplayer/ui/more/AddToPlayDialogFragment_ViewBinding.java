// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.more;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddToPlayDialogFragment_ViewBinding implements Unbinder {
  private AddToPlayDialogFragment target;

  private View view7f090049;

  @UiThread
  public AddToPlayDialogFragment_ViewBinding(final AddToPlayDialogFragment target, View source) {
    this.target = target;

    View view;
    target.mRecyclerViewMorePlayList = Utils.findRequiredViewAsType(source, R.id.morePlaylist_recyclerview, "field 'mRecyclerViewMorePlayList'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.add_playlist_moreImage, "method 'createToPlayList'");
    view7f090049 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.createToPlayList();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddToPlayDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewMorePlayList = null;

    view7f090049.setOnClickListener(null);
    view7f090049 = null;
  }
}
