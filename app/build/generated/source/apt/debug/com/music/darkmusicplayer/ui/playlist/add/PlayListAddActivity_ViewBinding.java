// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.playlist.add;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayListAddActivity_ViewBinding implements Unbinder {
  private PlayListAddActivity target;

  private View view7f0901c8;

  private View view7f0900b1;

  @UiThread
  public PlayListAddActivity_ViewBinding(PlayListAddActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PlayListAddActivity_ViewBinding(final PlayListAddActivity target, View source) {
    this.target = target;

    View view;
    target.mRecyclerViewSelectSong = Utils.findRequiredViewAsType(source, R.id.song_select_recyclerview, "field 'mRecyclerViewSelectSong'", RecyclerView.class);
    target.mSelectSongCountTextview = Utils.findRequiredViewAsType(source, R.id.count_selectSong_text, "field 'mSelectSongCountTextview'", TextView.class);
    target.mPlayListToolBar = Utils.findRequiredViewAsType(source, R.id.playlist_toolbar, "field 'mPlayListToolBar'", Toolbar.class);
    target.mAllSongCheckBox = Utils.findRequiredViewAsType(source, R.id.all_selectSong_checkbox, "field 'mAllSongCheckBox'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.save_selectSong_image, "method 'saveSelectedSongs'");
    view7f0901c8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveSelectedSongs();
      }
    });
    view = Utils.findRequiredView(source, R.id.discard_selectSong_image, "method 'discardSelectedSongs'");
    view7f0900b1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.discardSelectedSongs();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayListAddActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewSelectSong = null;
    target.mSelectSongCountTextview = null;
    target.mPlayListToolBar = null;
    target.mAllSongCheckBox = null;

    view7f0901c8.setOnClickListener(null);
    view7f0901c8 = null;
    view7f0900b1.setOnClickListener(null);
    view7f0900b1 = null;
  }
}
