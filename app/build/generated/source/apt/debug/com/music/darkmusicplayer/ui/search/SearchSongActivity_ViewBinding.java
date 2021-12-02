// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.search;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchSongActivity_ViewBinding implements Unbinder {
  private SearchSongActivity target;

  private View view7f0901d1;

  private View view7f0901d6;

  @UiThread
  public SearchSongActivity_ViewBinding(SearchSongActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchSongActivity_ViewBinding(final SearchSongActivity target, View source) {
    this.target = target;

    View view;
    target.mSearchSongEditText = Utils.findRequiredViewAsType(source, R.id.search_song_editText, "field 'mSearchSongEditText'", EditText.class);
    target.mRecyclerViewSongSearch = Utils.findRequiredViewAsType(source, R.id.recyclerview_search, "field 'mRecyclerViewSongSearch'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.search_back_image, "method 'onSearchBackButtonClicked'");
    view7f0901d1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchBackButtonClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.search_close_imageview, "method 'onSearchCloseButtonClicked'");
    view7f0901d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchCloseButtonClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchSongActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSearchSongEditText = null;
    target.mRecyclerViewSongSearch = null;

    view7f0901d1.setOnClickListener(null);
    view7f0901d1 = null;
    view7f0901d6.setOnClickListener(null);
    view7f0901d6 = null;
  }
}
