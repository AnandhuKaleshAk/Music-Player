// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.playlist;

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

public class PlayListFragment_ViewBinding implements Unbinder {
  private PlayListFragment target;

  private View view7f090048;

  @UiThread
  public PlayListFragment_ViewBinding(final PlayListFragment target, View source) {
    this.target = target;

    View view;
    target.mRecyclerViewPlayList = Utils.findRequiredViewAsType(source, R.id.playlist_recyclerview, "field 'mRecyclerViewPlayList'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.add_playlist_image, "method 'addPlaylist'");
    view7f090048 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addPlaylist();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewPlayList = null;

    view7f090048.setOnClickListener(null);
    view7f090048 = null;
  }
}
