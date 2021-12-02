// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SleepAdapter$SleepViewHolder_ViewBinding implements Unbinder {
  private SleepAdapter.SleepViewHolder target;

  @UiThread
  public SleepAdapter$SleepViewHolder_ViewBinding(SleepAdapter.SleepViewHolder target,
      View source) {
    this.target = target;

    target.mOnOffTimerRadioButton = Utils.findRequiredViewAsType(source, R.id.onOff_timer_radio, "field 'mOnOffTimerRadioButton'", RadioButton.class);
    target.mMinuteTimerTextView = Utils.findRequiredViewAsType(source, R.id.minute_timerItem_textview, "field 'mMinuteTimerTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SleepAdapter.SleepViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mOnOffTimerRadioButton = null;
    target.mMinuteTimerTextView = null;
  }
}
