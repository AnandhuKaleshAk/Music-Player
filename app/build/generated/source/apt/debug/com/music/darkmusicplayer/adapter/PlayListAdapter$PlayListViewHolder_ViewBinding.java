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

public class PlayListAdapter$PlayListViewHolder_ViewBinding implements Unbinder {
  private PlayListAdapter.PlayListViewHolder target;

  @UiThread
  public PlayListAdapter$PlayListViewHolder_ViewBinding(PlayListAdapter.PlayListViewHolder target,
      View source) {
    this.target = target;

    target.mNamePlayListItemText = Utils.findRequiredViewAsType(source, R.id.name_playListItem_text, "field 'mNamePlayListItemText'", TextView.class);
    target.mNoSongsPlayListItemText = Utils.findRequiredViewAsType(source, R.id.noSongs_playListItem_text, "field 'mNoSongsPlayListItemText'", TextView.class);
    target.mIconPlayListItemImageView = Utils.findRequiredViewAsType(source, R.id.icon_playListItem_image, "field 'mIconPlayListItemImageView'", ImageView.class);
    target.mMoreFolderPlayListImageView = Utils.findRequiredViewAsType(source, R.id.more_folderPlayListitem_image, "field 'mMoreFolderPlayListImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayListAdapter.PlayListViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mNamePlayListItemText = null;
    target.mNoSongsPlayListItemText = null;
    target.mIconPlayListItemImageView = null;
    target.mMoreFolderPlayListImageView = null;
  }
}
