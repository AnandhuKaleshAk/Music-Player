// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MorePlayListAdapter$MorePlayListViewHolder_ViewBinding implements Unbinder {
  private MorePlayListAdapter.MorePlayListViewHolder target;

  @UiThread
  public MorePlayListAdapter$MorePlayListViewHolder_ViewBinding(
      MorePlayListAdapter.MorePlayListViewHolder target, View source) {
    this.target = target;

    target.mNameMorePlayListItemText = Utils.findRequiredViewAsType(source, R.id.name_morePlayList_text, "field 'mNameMorePlayListItemText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MorePlayListAdapter.MorePlayListViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mNameMorePlayListItemText = null;
  }
}
