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

public class GenresAdapter$GenreViewHolder_ViewBinding implements Unbinder {
  private GenresAdapter.GenreViewHolder target;

  @UiThread
  public GenresAdapter$GenreViewHolder_ViewBinding(GenresAdapter.GenreViewHolder target,
      View source) {
    this.target = target;

    target.mGenreImageItemImage = Utils.findRequiredViewAsType(source, R.id.image_genre_genreItem, "field 'mGenreImageItemImage'", ImageView.class);
    target.mGenreNameItemText = Utils.findRequiredViewAsType(source, R.id.text_name_genreItem, "field 'mGenreNameItemText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GenresAdapter.GenreViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mGenreImageItemImage = null;
    target.mGenreNameItemText = null;
  }
}
