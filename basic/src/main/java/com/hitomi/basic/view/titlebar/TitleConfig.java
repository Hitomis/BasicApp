package com.hitomi.basic.view.titlebar;

/**
 * Created by hitomi on 2016/12/27.
 */

public class TitleConfig {

    public static class Builder {

        private String title;

        private int barHeight;

        private int leftIcon;

        private int centerIcon;

        private int rightIcon;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBarHeight(int barHeight) {
            this.barHeight = barHeight;
            return this;
        }

        public Builder setLeftIcon(int leftIcon) {
            this.leftIcon = leftIcon;
            return this;
        }

        public Builder setCenterIcon(int centerIcon) {
            this.centerIcon = centerIcon;
            return this;
        }

        public Builder setRightIcon(int rightIcon) {
            this.rightIcon = rightIcon;
            return this;
        }
    }

    public void create() {

    }

}
