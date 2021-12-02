// Generated code from Butter Knife. Do not modify!
package com.music.darkmusicplayer.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.utils.CustomViewPager;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view7f090123;

  private View view7f090125;

  private View view7f090126;

  private View view7f09011e;

  private View view7f090121;

  private View view7f090124;

  private View view7f0901d9;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mDrawerLayout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'mDrawerLayout'", DrawerLayout.class);
    target.mTabLayout = Utils.findRequiredViewAsType(source, R.id.tabs, "field 'mTabLayout'", TabLayout.class);
    target.mNavigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'mNavigationView'", NavigationView.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.main_viewPager, "field 'mViewPager'", CustomViewPager.class);
    target.mPlayingViewPager = Utils.findRequiredViewAsType(source, R.id.now_playing_viewPager, "field 'mPlayingViewPager'", ViewPager.class);
    target.mSlidingRelativeLayout = Utils.findRequiredViewAsType(source, R.id.slidingRelativeLayout, "field 'mSlidingRelativeLayout'", RelativeLayout.class);
    target.mSongNameItemTextView = Utils.findRequiredViewAsType(source, R.id.text_name_itemPlay, "field 'mSongNameItemTextView'", TextView.class);
    target.mSongArtistItemTextView = Utils.findRequiredViewAsType(source, R.id.text_artist_itemPlay, "field 'mSongArtistItemTextView'", TextView.class);
    target.mSongTimeItemTextView = Utils.findRequiredViewAsType(source, R.id.text_start_itemPlay, "field 'mSongTimeItemTextView'", TextView.class);
    target.mSongEndItemTextView = Utils.findRequiredViewAsType(source, R.id.text_end_itemPlay, "field 'mSongEndItemTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.image_play_itemPlay, "field 'mSongPlayPauseItemImageView' and method 'onClickPlayPause'");
    target.mSongPlayPauseItemImageView = Utils.castView(view, R.id.image_play_itemPlay, "field 'mSongPlayPauseItemImageView'", ImageView.class);
    view7f090123 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickPlayPause();
      }
    });
    view = Utils.findRequiredView(source, R.id.image_repeat_itemPlay, "field 'mSongRepeatItemImageView' and method 'onClickRepeat'");
    target.mSongRepeatItemImageView = Utils.castView(view, R.id.image_repeat_itemPlay, "field 'mSongRepeatItemImageView'", ImageView.class);
    view7f090125 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickRepeat();
      }
    });
    view = Utils.findRequiredView(source, R.id.image_shuffle_itemPlay, "field 'mSongShuffleItemImageView' and method 'onClickShuffle'");
    target.mSongShuffleItemImageView = Utils.castView(view, R.id.image_shuffle_itemPlay, "field 'mSongShuffleItemImageView'", ImageView.class);
    view7f090126 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickShuffle();
      }
    });
    view = Utils.findRequiredView(source, R.id.image_favourite_itemPlay, "field 'mSongFavouriteImageView' and method 'favorites'");
    target.mSongFavouriteImageView = Utils.castView(view, R.id.image_favourite_itemPlay, "field 'mSongFavouriteImageView'", ImageView.class);
    view7f09011e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.favorites();
      }
    });
    target.mSeekBarMusic = Utils.findRequiredViewAsType(source, R.id.seekBar_music, "field 'mSeekBarMusic'", SeekBar.class);
    view = Utils.findRequiredView(source, R.id.image_next_itemPlay, "method 'onClickNext'");
    view7f090121 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickNext();
      }
    });
    view = Utils.findRequiredView(source, R.id.image_prev_itemPlay, "method 'onClickPrev'");
    view7f090124 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickPrev();
      }
    });
    view = Utils.findRequiredView(source, R.id.search_home, "method 'onSearchViewClicked'");
    view7f0901d9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mDrawerLayout = null;
    target.mTabLayout = null;
    target.mNavigationView = null;
    target.mViewPager = null;
    target.mPlayingViewPager = null;
    target.mSlidingRelativeLayout = null;
    target.mSongNameItemTextView = null;
    target.mSongArtistItemTextView = null;
    target.mSongTimeItemTextView = null;
    target.mSongEndItemTextView = null;
    target.mSongPlayPauseItemImageView = null;
    target.mSongRepeatItemImageView = null;
    target.mSongShuffleItemImageView = null;
    target.mSongFavouriteImageView = null;
    target.mSeekBarMusic = null;

    view7f090123.setOnClickListener(null);
    view7f090123 = null;
    view7f090125.setOnClickListener(null);
    view7f090125 = null;
    view7f090126.setOnClickListener(null);
    view7f090126 = null;
    view7f09011e.setOnClickListener(null);
    view7f09011e = null;
    view7f090121.setOnClickListener(null);
    view7f090121 = null;
    view7f090124.setOnClickListener(null);
    view7f090124 = null;
    view7f0901d9.setOnClickListener(null);
    view7f0901d9 = null;
  }
}
