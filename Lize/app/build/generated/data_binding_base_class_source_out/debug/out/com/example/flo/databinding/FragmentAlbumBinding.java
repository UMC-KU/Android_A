// Generated by view binder compiler. Do not edit!
package com.example.flo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.example.flo.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAlbumBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView albumBackIv;

  @NonNull
  public final TabLayout albumContentTb;

  @NonNull
  public final ViewPager2 albumContentVp;

  @NonNull
  public final ImageView albumImgIv;

  @NonNull
  public final TextView albumInfoTv;

  @NonNull
  public final ImageView albumLikeOffIv;

  @NonNull
  public final ImageView albumLpIv;

  @NonNull
  public final ImageView albumMoreIv;

  @NonNull
  public final TextView albumSingerMainTv;

  @NonNull
  public final TextView albumTitleMainTv;

  private FragmentAlbumBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView albumBackIv,
      @NonNull TabLayout albumContentTb, @NonNull ViewPager2 albumContentVp,
      @NonNull ImageView albumImgIv, @NonNull TextView albumInfoTv,
      @NonNull ImageView albumLikeOffIv, @NonNull ImageView albumLpIv,
      @NonNull ImageView albumMoreIv, @NonNull TextView albumSingerMainTv,
      @NonNull TextView albumTitleMainTv) {
    this.rootView = rootView;
    this.albumBackIv = albumBackIv;
    this.albumContentTb = albumContentTb;
    this.albumContentVp = albumContentVp;
    this.albumImgIv = albumImgIv;
    this.albumInfoTv = albumInfoTv;
    this.albumLikeOffIv = albumLikeOffIv;
    this.albumLpIv = albumLpIv;
    this.albumMoreIv = albumMoreIv;
    this.albumSingerMainTv = albumSingerMainTv;
    this.albumTitleMainTv = albumTitleMainTv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAlbumBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAlbumBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_album, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAlbumBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.album_back_iv;
      ImageView albumBackIv = ViewBindings.findChildViewById(rootView, id);
      if (albumBackIv == null) {
        break missingId;
      }

      id = R.id.album_content_tb;
      TabLayout albumContentTb = ViewBindings.findChildViewById(rootView, id);
      if (albumContentTb == null) {
        break missingId;
      }

      id = R.id.album_content_vp;
      ViewPager2 albumContentVp = ViewBindings.findChildViewById(rootView, id);
      if (albumContentVp == null) {
        break missingId;
      }

      id = R.id.album_img_iv;
      ImageView albumImgIv = ViewBindings.findChildViewById(rootView, id);
      if (albumImgIv == null) {
        break missingId;
      }

      id = R.id.album_info_tv;
      TextView albumInfoTv = ViewBindings.findChildViewById(rootView, id);
      if (albumInfoTv == null) {
        break missingId;
      }

      id = R.id.album_like_off_iv;
      ImageView albumLikeOffIv = ViewBindings.findChildViewById(rootView, id);
      if (albumLikeOffIv == null) {
        break missingId;
      }

      id = R.id.album_lp_iv;
      ImageView albumLpIv = ViewBindings.findChildViewById(rootView, id);
      if (albumLpIv == null) {
        break missingId;
      }

      id = R.id.album_more_iv;
      ImageView albumMoreIv = ViewBindings.findChildViewById(rootView, id);
      if (albumMoreIv == null) {
        break missingId;
      }

      id = R.id.album_singer_main_tv;
      TextView albumSingerMainTv = ViewBindings.findChildViewById(rootView, id);
      if (albumSingerMainTv == null) {
        break missingId;
      }

      id = R.id.album_title_main_tv;
      TextView albumTitleMainTv = ViewBindings.findChildViewById(rootView, id);
      if (albumTitleMainTv == null) {
        break missingId;
      }

      return new FragmentAlbumBinding((ConstraintLayout) rootView, albumBackIv, albumContentTb,
          albumContentVp, albumImgIv, albumInfoTv, albumLikeOffIv, albumLpIv, albumMoreIv,
          albumSingerMainTv, albumTitleMainTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
