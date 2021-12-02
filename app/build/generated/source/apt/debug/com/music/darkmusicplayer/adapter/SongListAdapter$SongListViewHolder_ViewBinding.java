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

public class SongListAdapter$SongListViewHolder_ViewBinding implements Unbinder {
  private SongListAdapter.SongListViewHolder target;

  @UiThread
  public SongListAdapter$SongListViewHolder_ViewBinding(SongListAdapter.SongListViewHolder target,
      View source) {
    this.target = target;

    target.mSongNameItemText = Utils.findRequiredViewAsType(source, R.id.name_songItem_text, "field 'mSongNameItemText'", TextView.class);
    target.mSongArtistItemText = Utils.findRequiredViewAsType(source, R.id.artist_songItem_text, "field 'mSongArtistItemText'", TextView.class);
    target.mSongAlbumArtItemImage = Utils.findRequiredViewAsType(source, R.id.icon_songItem_image, "field 'mSongAlbumArtItemImage'", ImageView.class);
    target.mMoreOptionImageView = Utils.findRequiredViewAsType(source, R.id.more_folderitem_image, "field 'mMoreOptionImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SongListAdapter.SongListViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSongNameItemText = null;
    target.mSongArtistItemText = null;
    target.mSongAlbumArtItemImage = null;
    target.mMoreOptionImageView = null;
  }
}
