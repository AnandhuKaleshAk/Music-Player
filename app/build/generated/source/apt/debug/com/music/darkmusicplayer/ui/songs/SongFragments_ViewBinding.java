// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.songs;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SongFragments_ViewBinding implements Unbinder {
  private SongFragments target;

  @UiThread
  public SongFragments_ViewBinding(SongFragments target, View source) {
    this.target = target;

    target.mRecyclerViewSong = Utils.findRequiredViewAsType(source, R.id.recycler_view_songList, "field 'mRecyclerViewSong'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SongFragments target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewSong = null;
  }
}
