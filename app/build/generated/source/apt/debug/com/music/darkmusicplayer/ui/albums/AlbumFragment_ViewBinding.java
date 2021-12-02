// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.albums;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlbumFragment_ViewBinding implements Unbinder {
  private AlbumFragment target;

  @UiThread
  public AlbumFragment_ViewBinding(AlbumFragment target, View source) {
    this.target = target;

    target.mRecyclerViewAlbum = Utils.findRequiredViewAsType(source, R.id.recycler_view_album, "field 'mRecyclerViewAlbum'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlbumFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewAlbum = null;
  }
}
