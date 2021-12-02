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

public class PlaylistAddAdapter$PlayListAddViewHolder_ViewBinding implements Unbinder {
  private PlaylistAddAdapter.PlayListAddViewHolder target;

  @UiThread
  public PlaylistAddAdapter$PlayListAddViewHolder_ViewBinding(
      PlaylistAddAdapter.PlayListAddViewHolder target, View source) {
    this.target = target;

    target.mSongNameAddItemText = Utils.findRequiredViewAsType(source, R.id.name_songAddItem_text, "field 'mSongNameAddItemText'", TextView.class);
    target.mSongAlbumArtAddItemImage = Utils.findRequiredViewAsType(source, R.id.icon_songAddItem_image, "field 'mSongAlbumArtAddItemImage'", ImageView.class);
    target.mSongSelectAddItemImage = Utils.findRequiredViewAsType(source, R.id.select_songAddItem_image, "field 'mSongSelectAddItemImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlaylistAddAdapter.PlayListAddViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSongNameAddItemText = null;
    target.mSongAlbumArtAddItemImage = null;
    target.mSongSelectAddItemImage = null;
  }
}
