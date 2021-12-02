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

public class AlbumListAdapter$AlbumViewHolder_ViewBinding implements Unbinder {
  private AlbumListAdapter.AlbumViewHolder target;

  @UiThread
  public AlbumListAdapter$AlbumViewHolder_ViewBinding(AlbumListAdapter.AlbumViewHolder target,
      View source) {
    this.target = target;

    target.mAbumImageItemImage = Utils.findRequiredViewAsType(source, R.id.image_album_albumItem, "field 'mAbumImageItemImage'", ImageView.class);
    target.mAlbumNameItemText = Utils.findRequiredViewAsType(source, R.id.text_name_albumItem, "field 'mAlbumNameItemText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlbumListAdapter.AlbumViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mAbumImageItemImage = null;
    target.mAlbumNameItemText = null;
  }
}
