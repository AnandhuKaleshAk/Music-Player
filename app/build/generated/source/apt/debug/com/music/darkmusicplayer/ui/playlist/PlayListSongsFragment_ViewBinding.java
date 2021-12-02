// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.playlist;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayListSongsFragment_ViewBinding implements Unbinder {
  private PlayListSongsFragment target;

  @UiThread
  public PlayListSongsFragment_ViewBinding(PlayListSongsFragment target, View source) {
    this.target = target;

    target.mRecyclerViewSongPlayList = Utils.findRequiredViewAsType(source, R.id.recycler_view_songPlayList, "field 'mRecyclerViewSongPlayList'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayListSongsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewSongPlayList = null;
  }
}
