// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.artists;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArtistFragment_ViewBinding implements Unbinder {
  private ArtistFragment target;

  @UiThread
  public ArtistFragment_ViewBinding(ArtistFragment target, View source) {
    this.target = target;

    target.mRecyclerViewArtist = Utils.findRequiredViewAsType(source, R.id.recycler_view_artist, "field 'mRecyclerViewArtist'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArtistFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewArtist = null;
  }
}
