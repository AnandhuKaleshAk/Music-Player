// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.equalizer;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.music.darkmusicplayer.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EqualizerFragment_ViewBinding implements Unbinder {
  private EqualizerFragment target;

  @UiThread
  public EqualizerFragment_ViewBinding(EqualizerFragment target, View source) {
    this.target = target;

    target.backEqualizerButton = Utils.findRequiredViewAsType(source, R.id.back_toolbarEqualizer_image, "field 'backEqualizerButton'", ImageView.class);
    target.mSeekbar80hZ = Utils.findRequiredViewAsType(source, R.id.hz80Seekar, "field 'mSeekbar80hZ'", SeekBar.class);
    target.mSeekbar230hZ = Utils.findRequiredViewAsType(source, R.id.hz230Seekar, "field 'mSeekbar230hZ'", SeekBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EqualizerFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.backEqualizerButton = null;
    target.mSeekbar80hZ = null;
    target.mSeekbar230hZ = null;
  }
}
