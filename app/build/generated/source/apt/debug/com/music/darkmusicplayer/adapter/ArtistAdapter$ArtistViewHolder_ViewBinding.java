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

public class ArtistAdapter$ArtistViewHolder_ViewBinding implements Unbinder {
  private ArtistAdapter.ArtistViewHolder target;

  @UiThread
  public ArtistAdapter$ArtistViewHolder_ViewBinding(ArtistAdapter.ArtistViewHolder target,
      View source) {
    this.target = target;

    target.mArtistImageItemImage = Utils.findRequiredViewAsType(source, R.id.image_artist_artisttem, "field 'mArtistImageItemImage'", ImageView.class);
    target.mArtistNameItemText = Utils.findRequiredViewAsType(source, R.id.text_name_artistItem, "field 'mArtistNameItemText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArtistAdapter.ArtistViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mArtistImageItemImage = null;
    target.mArtistNameItemText = null;
  }
}
