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

public class SongPagerAdapter_ViewBinding implements Unbinder {
  private SongPagerAdapter target;

  @UiThread
  public SongPagerAdapter_ViewBinding(SongPagerAdapter target, View source) {
    this.target = target;

    target.mItemAlbumImage = Utils.findRequiredViewAsType(source, R.id.icon_playItem_image, "field 'mItemAlbumImage'", ImageView.class);
    target.mItemSongNameTextView = Utils.findRequiredViewAsType(source, R.id.name_playItem_text, "field 'mItemSongNameTextView'", TextView.class);
    target.mItemArtistNameTextView = Utils.findRequiredViewAsType(source, R.id.artist_playItem_text, "field 'mItemArtistNameTextView'", TextView.class);
    target.mItemAlbumFullImageView = Utils.findRequiredViewAsType(source, R.id.image_full_album_item_play, "field 'mItemAlbumFullImageView'", ImageView.class);
    target.mItemPlayPauseImageView = Utils.findRequiredViewAsType(source, R.id.image_play_bottom, "field 'mItemPlayPauseImageView'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SongPagerAdapter target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mItemAlbumImage = null;
    target.mItemSongNameTextView = null;
    target.mItemArtistNameTextView = null;
    target.mItemAlbumFullImageView = null;
    target.mItemPlayPauseImageView = null;
  }
}
