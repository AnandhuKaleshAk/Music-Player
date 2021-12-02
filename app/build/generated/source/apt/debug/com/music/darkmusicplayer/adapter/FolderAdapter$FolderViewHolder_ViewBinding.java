// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FolderAdapter$FolderViewHolder_ViewBinding implements Unbinder {
  private FolderAdapter.FolderViewHolder target;

  @UiThread
  public FolderAdapter$FolderViewHolder_ViewBinding(FolderAdapter.FolderViewHolder target,
      View source) {
    this.target = target;

    target.mIconFolderItemImageView = Utils.findRequiredViewAsType(source, R.id.icon_folderItem_image, "field 'mIconFolderItemImageView'", ImageView.class);
    target.mNameFolderItemTextView = Utils.findRequiredViewAsType(source, R.id.name_folderItem_text, "field 'mNameFolderItemTextView'", TextView.class);
    target.mSongPathFolderItemTextView = Utils.findRequiredViewAsType(source, R.id.pathsong_folderItem_text, "field 'mSongPathFolderItemTextView'", TextView.class);
    target.mSongCountFolderItemTextView = Utils.findRequiredViewAsType(source, R.id.songcount_folderItem_text, "field 'mSongCountFolderItemTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FolderAdapter.FolderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIconFolderItemImageView = null;
    target.mNameFolderItemTextView = null;
    target.mSongPathFolderItemTextView = null;
    target.mSongCountFolderItemTextView = null;
  }
}
