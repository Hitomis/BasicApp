package com.hitomi.basic.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by hitomi on a 2016/12/21.
 */

public class AsyncRoundImageView extends RoundImageView {

    private Picasso picasso;

    public AsyncRoundImageView(Context context) {
        this(context, null);
    }

    public AsyncRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        picasso = Picasso.with(context);
    }

    public void asyncLoadImageUrl(String url) {
        picasso.load(url).into(this);
    }

    public void asyncLoadImageUri(Uri uri) {
        picasso.load(uri).into(this);
    }

    public void asyncLoadImageFile(File file) {
        picasso.load(file).into(this);
    }

    public void asyncLoadImageRes(int res) {
        picasso.load(res).into(this);
    }


}
