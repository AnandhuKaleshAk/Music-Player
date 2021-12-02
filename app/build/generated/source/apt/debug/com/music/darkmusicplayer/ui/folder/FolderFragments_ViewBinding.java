// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.folder;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FolderFragments_ViewBinding implements Unbinder {
  private FolderFragments target;

  @UiThread
  public FolderFragments_ViewBinding(FolderFragments target, View source) {
    this.target = target;

    target.mRecyclerViewFolder = Utils.findRequiredViewAsType(source, R.id.recycler_view_folder, "field 'mRecyclerViewFolder'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FolderFragments target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewFolder = null;
  }
}
