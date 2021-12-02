// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.genres;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GenresFragment_ViewBinding implements Unbinder {
  private GenresFragment target;

  @UiThread
  public GenresFragment_ViewBinding(GenresFragment target, View source) {
    this.target = target;

    target.mRecyclerViewGenre = Utils.findRequiredViewAsType(source, R.id.recycler_view_genre, "field 'mRecyclerViewGenre'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GenresFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewGenre = null;
  }
}
