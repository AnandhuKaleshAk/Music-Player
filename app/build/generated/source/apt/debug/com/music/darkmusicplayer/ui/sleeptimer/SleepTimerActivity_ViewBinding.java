// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.sleeptimer;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SleepTimerActivity_ViewBinding implements Unbinder {
  private SleepTimerActivity target;

  private View view7f090196;

  @UiThread
  public SleepTimerActivity_ViewBinding(SleepTimerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SleepTimerActivity_ViewBinding(final SleepTimerActivity target, View source) {
    this.target = target;

    View view;
    target.mRecyclerViewTimer = Utils.findRequiredViewAsType(source, R.id.recycler_view_timer, "field 'mRecyclerViewTimer'", RecyclerView.class);
    target.mTitleToolBarTextView = Utils.findRequiredViewAsType(source, R.id.title_toolbar_textView, "field 'mTitleToolBarTextView'", TextView.class);
    target.mBackImageView = Utils.findRequiredViewAsType(source, R.id.back_toolbar_image, "field 'mBackImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.onOff_timerManuel_radio, "field 'mTimerManuelRadioButton' and method 'onTimerSelected'");
    target.mTimerManuelRadioButton = Utils.castView(view, R.id.onOff_timerManuel_radio, "field 'mTimerManuelRadioButton'", RadioButton.class);
    view7f090196 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onTimerSelected(p0, p1);
      }
    });
    target.mManualTimerEditText = Utils.findRequiredViewAsType(source, R.id.manualTimerInputEditText, "field 'mManualTimerEditText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SleepTimerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerViewTimer = null;
    target.mTitleToolBarTextView = null;
    target.mBackImageView = null;
    target.mTimerManuelRadioButton = null;
    target.mManualTimerEditText = null;

    ((CompoundButton) view7f090196).setOnCheckedChangeListener(null);
    view7f090196 = null;
  }
}
