package com.zhy.sample_okhttp;

import android.content.Context;
import android.util.TypedValue;

class ScreenUtil {
    public static int dip2px(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,context.getResources().getDisplayMetrics());
    }
}
