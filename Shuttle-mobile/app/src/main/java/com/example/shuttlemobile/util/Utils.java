package com.example.shuttlemobile.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

public class Utils {
    public static final String ServerOrigin = "http://192.168.1.30:8080/";

    private Utils() {}

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Converts 'dp' into 'px'
     * @param context The application context. Use <code>getContext()</code>.
     * @param dp The relative dp value.
     * @return Equivalent pixel value, varying for different devices.
     */
    public static double dp2px(Context context, double dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float)dp, context.getResources().getDisplayMetrics());
    }
}
