package com.hitomi.basic.view.titlebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * App 通用标题栏布局<br/>
 * <p>
 *     使用构建器去构建常见样式的标题栏, 构建的元素包括左侧操作区域、标题区域、右侧操作区域 <br/>
 *     以及位于右侧操作区域的左侧的右侧子项操作区域. 可以构建的区域可以显示文字或者图标.
 * </p>
 *
 * Created by hitomi on 2016/12/27.
 */
public class TitleBarLayout extends RelativeLayout {

    TitleBarController controller;

    public TitleBarLayout(Context context) {
        this(context, null);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        controller = new TitleBarController(context, this);
    }

    public static Builder config(Context context) {
        return new Builder(context);
    }

    /**
     * 标题栏的构建器, 用于构建标题栏相关的一系列元素, 支持通过
     * 构建器使用 {@link Builder#create()} 方法去创建
     * {@link TitleBarLayout} 组件, 同时构建的元素也会适用在
     * 创建的 {@link TitleBarLayout} 之上; <br/>
     * 如果不通过该构建器创建 {@link TitleBarLayout} 组件, 也可
     * 以将参数适用在自己创建的 {@link TitleBarLayout} 之上, 调
     * 用 {@link Builder#setup(TitleBarLayout)} 即可
     */
    public static class Builder {
        private final TitleBarController.TitleParams params;

        public Builder(Context context) {
            params = new TitleBarController.TitleParams(context);
        }

        /**
         * 给标题栏设置标题字符
         * @param title 标题文字
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        /**
         * 统一给标题栏设置标题字符、标题字符的颜色、标题字符的尺寸
         * @param title 标题文字
         * @param titleColor 标题字符的颜色
         * @param titleSize 标题字符的尺寸 unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitle(String title, int titleColor, float titleSize) {
            params.title = title;
            params.titleColor = titleColor;
            params.titleSize = titleSize;
            return this;
        }

        /**
         * 给标题栏的标题位置设置一个 TextView 作为标题，不传递布局参数，默认使用默认布局参数
         * 宽高都为{@link RelativeLayout.LayoutParams#WRAP_CONTENT}
         * @param tvTitle TextView
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitleView(TextView tvTitle) {
            params.tvTitle = tvTitle;
            return this;
        }

        /**
         * 给标题栏设置一个 TextView 作为标题，并传递自己的布局参数
         * @param tvTitle 用作标题的 TextView 控件
         * @param titleRlp TextView 的布局参数, {@link RelativeLayout.LayoutParams}
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitleView(TextView tvTitle, RelativeLayout.LayoutParams titleRlp) {
            params.tvTitle = tvTitle;
            params.titleRlp = titleRlp;
            return this;
        }

        /**
         * 给标题栏设置标题字符的颜色
         * @param titleColor 标题字符的颜色, 格式为颜色代码
         *                   可以通过 {@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitleColor(int titleColor) {
            params.titleColor = titleColor;
            return this;
        }

        /**
         * 给标题栏设置标题字符的尺寸
         * @param titleSize 标题字符的尺寸, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitleSize(float titleSize) {
            params.titleSize = titleSize;
            return this;
        }

        /**
         * 给标题栏设置背景色
         * @param barColor 标题栏的背景色, int 格式的颜色代码
         *                 可以通过{@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setBarColor(int barColor) {
            params.barColor = barColor;
            return this;
        }

        /**
         * 设置标题栏左侧的字符串
         * @param leftText 标题栏左侧的字符串
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftText(String leftText) {
            params.leftText = leftText;
            return this;
        }

        /**
         * 设置标题栏左侧的字符串的颜色
         * @param leftTextColor 左侧的字符串的颜色, 格式为颜色代码
         *                      可以通过 {@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftTextColor(int leftTextColor) {
            params.leftTextColor = leftTextColor;
            return this;
        }

        /**
         * 设置标题栏左侧的字符串的尺寸
         * @param leftTextSize 左侧的字符串的尺寸, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftTextSize(int leftTextSize) {
            params.leftTextSize = leftTextSize;
            return this;
        }

        /**
         * 给标题栏统一设置左侧的字符，左侧字符的颜色以及左侧字符的尺寸
         * @param leftText 左侧的字符
         * @param leftTextColor 左侧字符的颜色
         * @param leftTextSize 左侧字符的尺寸, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftText(String leftText, int leftTextColor, int leftTextSize) {
            params.leftText = leftText;
            params.leftTextColor = leftTextColor;
            params.leftTextSize = leftTextSize;
            return this;
        }

        /**
         * 给标题栏左侧设置一个 TextView 控件, 作为左侧的操作单元.
         * 当左侧同时需要图标和文字显示的时候，此方法可以满足这个条件.
         * @param leftTextView TextView 组件
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftTextView(TextView leftTextView) {
            params.tvleft = leftTextView;
            return this;
        }

        /**
         * 设置一个本地资源的图标作为左侧显示的操作单元
         * @param leftIcon 本地资源图标 R.mipmap.resid
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftIcon(int leftIcon) {
            params.leftIcon = leftIcon;
            return this;
        }

        /**
         * 设置一个 Bitmap 作为左侧显示的操作单元
         * @param leftBitmap Bitmap
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftBitmap(Bitmap leftBitmap) {
            params.leftBitmap = leftBitmap;
            return this;
        }

        /**
         * 给标题栏设置一个本地资源的图标，并制定该图标的尺寸大小
         * @param leftIcon 本地资源的图标 R.mipmap.resid
         * @param leftImageSize 图标的尺寸大小, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftIcon(int leftIcon, int leftImageSize) {
            params.leftIcon = leftIcon;
            params.leftImageSize = leftImageSize;
            return this;
        }

        /**
         * 给标题栏设置一个 Bitmap，并制定该 Bitmap 的尺寸大小
         * @param leftBitmap Bitmap
         * @param leftImageSize Bitmap的尺寸大小, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftBitmap(Bitmap leftBitmap, int leftImageSize) {
            params.leftBitmap = leftBitmap;
            params.leftImageSize = leftImageSize;
            return this;
        }

        /**
         * 为标题栏左侧图标(包括本地资源格式的图标以及 Bitmap )设定尺寸大小
         * @param leftImageSize 图标的尺寸大小, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftImageSize(int leftImageSize) {
            params.leftImageSize = leftImageSize;
            return this;
        }

        /**
         * 设置标题栏左侧操作单元的左外边距
         * @param leftMargin 左侧的外边距, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftMargin(int leftMargin) {
            params.leftMargin = leftMargin;
            return this;
        }

        /**
         * 设置标题栏左侧操作单元的内边距, 同时设置leftPadding、topPadding、rightPadding、bottomPadding <br/>
         * 主要用于扩大该操作单元的可触摸点击范围区域, 有一个良好的触摸点击交互体验
         * @param leftRegion 内边距值, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftRegion(int leftRegion) {
            params.leftRegion = leftRegion;
            return this;
        }

        /**
         * 设置标题栏右侧操作单元为字符串显示
         * @param rightText 标题栏右侧显示的字符串
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightText(String rightText) {
            params.rightText = rightText;
            return this;
        }

        /**
         * 设置标题栏右侧字符串的颜色
         * @param rightTextColor 字符串的颜色代码
         *                       可以通过{@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightTextColor(int rightTextColor) {
            params.rightTextColor = rightTextColor;
            return this;
        }

        /**
         * 设置标题栏右侧字符串的尺寸大小
         * @param rightTextSize 右侧字符串的尺寸大小, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightTextSize(int rightTextSize) {
            params.rightTextSize = rightTextSize;
            return this;
        }

        /**
         * 给标题栏右侧统一设置字符串、字符串颜色、字符串尺寸
         * @param rightText 显示的字符串
         * @param rightTextColor 字符串颜色
         *                       可以通过{@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @param rightTextSize 字符串尺寸, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightText(String rightText, int rightTextColor, int rightTextSize) {
            params.rightText = rightText;
            params.rightTextColor = rightTextColor;
            params.rightTextSize = rightTextSize;
            return this;
        }

        /**
         * 给标题栏右侧设置一个 TextView 作为显示的操作单元, 当右侧操作单元同时需要显示文字与图表
         * 此方法可以满足
         * @param rightTextView TextView
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightTextView(TextView rightTextView) {
            params.tvRight = rightTextView;
            return this;
        }

        /**
         * 给标题右侧设置本地资源的图标
         * @param rightIcon 本地资源的图标
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightIcon(int rightIcon) {
            params.rightIcon = rightIcon;
            return this;
        }

        /**
         * 给标题栏右侧设置 Bitmap
         * @param rightBitmap Bitmap
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightBitmap(Bitmap rightBitmap) {
            params.rightBitmap = rightBitmap;
            return this;
        }

        /**
         * 给标题栏右侧设置本地资源的图标, 并制定该图标的尺寸
         * @param rightIcon 本地资源的图标
         * @param rightImageSize 本地资源的图标尺寸, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightIcon(int rightIcon, int rightImageSize) {
            params.rightIcon = rightIcon;
            params.rightImageSize = rightImageSize;
            return this;
        }

        /**
         *  给标题栏右侧设置 Bitmap, 并制定该 Bitmap 的尺寸
         * @param rightBitmap Bitmap
         * @param rightImageSize Bitmap 的尺寸, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightBitmap(Bitmap rightBitmap, int rightImageSize) {
            params.rightBitmap = rightBitmap;
            params.rightImageSize = rightImageSize;
            return this;
        }

        /**
         * 设置标题栏右侧图标(包括本地资源的图标以及 Bitmap )的尺寸大小
         * @param rightImageSize 尺寸值, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightImageSize(int rightImageSize) {
            params.rightImageSize = rightImageSize;
            return this;
        }

        /**
         * 设置右侧操作单元的右侧外边距
         * @param rightMargin 右侧外边距, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightMargin(int rightMargin) {
            params.rightMargin = rightMargin;
            return this;
        }

        /**
         * 设置标题栏右侧操作单元的内边距, 同时设置leftPadding、topPadding、rightPadding、bottomPadding <br/>
         * 主要用于扩大该操作单元的可触摸点击范围区域, 有一个良好的触摸点击交互体验
         * @param rightRegion 内边距值, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightRegion(int rightRegion) {
            params.rightRegion = rightRegion;
            return this;
        }

        /**
         * 设置右侧子项操作单元字符串, 该字符串将显示在右侧操作单元的左侧
         * @param rightSubText  右侧子项操作单元字符串
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubText(String rightSubText) {
            params.rightSubText = rightSubText;
            return this;
        }

        /**
         * 设置右侧子项操作单元字符串颜色
         * @param rightSubTextColor 右侧子项操作单元字符串颜色代码
         *                          可以通过 {@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubTextColor(int rightSubTextColor) {
            params.rightSubTextColor = rightSubTextColor;
            return this;
        }

        /**
         * 设置右侧子项操作单元字符串尺寸
         * @param rightSubTextSize 右侧子项操作单元字符串尺寸, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubTextSize(int rightSubTextSize) {
            params.rightSubTextSize = rightSubTextSize;
            return this;
        }

        /**
         * 给右侧子项操作单元统一设置字符串、颜色以及尺寸, 该字符串显示在右侧操作单元的左侧
         * @param rightSubText 右侧子项操作单元显示的字符串
         * @param rightSubTextColor 右侧子项操作单元显示的字符串颜色
         *                          可以通过 {@link android.graphics.Color#parseColor} 方法获取颜色代码
         * @param rightSubTextSize 右侧子项操作单元显示的字符串尺寸, unit : sp
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubText(String rightSubText, int rightSubTextColor, int rightSubTextSize) {
            params.rightSubText = rightSubText;
            params.rightSubTextColor = rightSubTextColor;
            params.rightSubTextSize = rightSubTextSize;
            return this;
        }

        /**
         * 给标题栏右侧子项操作单元设置一个本地资源图标
         * @param rightSubIcon 本地资源图标 R.mimp.resid
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubIcon(int rightSubIcon) {
            params.rightSubIcon = rightSubIcon;
            return this;
        }

        /**
         * 给标题栏右侧子项操作单元设置一个 Bitmap
         * @param rightSubBitmap Bitmap
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubBitmap(Bitmap rightSubBitmap) {
            params.rightSubBitmap = rightSubBitmap;
            return this;
        }

        /**
         * 给标题栏右侧子项操作单元设置一个本地资源图标并且指定该图标的尺寸
         * @param rightSubIcon 本地资源图标
         * @param rightSubImageSize 本地资源图标尺寸, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubIcon(int rightSubIcon, int rightSubImageSize) {
            params.rightSubIcon = rightSubIcon;
            params.rightSubImageSize = rightSubImageSize;
            return this;
        }

        /**
         * 给标题栏右侧子项操作单元设置一个 Bitmap 并且指定该 Bitmap 的尺寸
         * @param rightSubBitmap Bitmap
         * @param rightSubImageSize Bitmap 的尺寸, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubBitmap(Bitmap rightSubBitmap, int rightSubImageSize) {
            params.rightSubBitmap = rightSubBitmap;
            params.rightSubImageSize = rightSubImageSize;
            return this;
        }

        /**
         * 设置标题栏右侧子项操作单元图标(包括本地资源图标以及 Bitmap )的尺寸
         * @param rightSubImageSize 图标尺寸, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubImageSize(int rightSubImageSize) {
            params.rightSubImageSize = rightSubImageSize;
            return this;
        }

        /**
         * 设置标题栏右侧子项操作单元离右侧操作单元的右侧外边距
         * @param rightSubMargin 右侧外边距, unit : px;
         * @return
         */
        public Builder setRightSubMargin(int rightSubMargin) {
            params.rightSubMargin = rightSubMargin;
            return this;
        }

        /**
         * 设置标题栏右侧子项操作单元的内边距, 同时设置leftPadding、topPadding、rightPadding、bottomPadding <br/>
         * 主要用于扩大该操作单元的可触摸点击范围区域, 有一个良好的触摸点击交互体验
         * @param rightSubRegion 内边距值, unit : px
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubRegion(int rightSubRegion) {
            params.rightSubRegion = rightSubRegion;
            return this;
        }

        /**
         * 设置标题区域的点击监听器
         * @param listener 点击监听器
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setTitleOnClickListener(OnClickListener listener) {
            params.onTitleClickListener = listener;
            return this;
        }

        /**
         * 设置左侧区域的点击监听器
         * @param listener 点击监听器
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setLeftOnClickListener(OnClickListener listener) {
            params.onLeftClickListener = listener;
            return this;
        }

        /**
         * 设置右侧区域的点击监听器
         * @param listener 点击监听器
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightOnClickListener(OnClickListener listener) {
            params.onRightClickListener = listener;
            return this;
        }

        /**
         * 设置右侧子项区域的点击监听器
         * @param listener 点击监听器
         * @return {@link TitleBarLayout.Builder}
         */
        public Builder setRightSubOnClickListener(OnClickListener listener) {
            params.onRightSubClickListener = listener;
            return this;
        }

        /**
         * 通过 {@link Builder} 创建 {@link TitleBarLayout} 实例对象
         * @return {@link TitleBarLayout}
         */
        public TitleBarLayout create() {
            final TitleBarLayout titleBar = new TitleBarLayout(params.context);
            params.apply(titleBar.controller);
            return titleBar;
        }

        /**
         * 将基于 {@link Builder} 构建的一系列元素适用在 {@link TitleBarLayout} 上面
         * @param titleBar {@link TitleBarLayout}
         */
        public void setup(TitleBarLayout titleBar) {
            params.apply(titleBar.controller);
        }

        /**
         * 将基于 {@link Builder} 构建的一系列元素适用在 {@link TitleBarLayout} 上面
         * @param view {@link TitleBarLayout}
         */
        public void setup(View view) {
            if (view instanceof TitleBarLayout) {
                params.apply(((TitleBarLayout) view).controller);
            }
        }
    }
}
