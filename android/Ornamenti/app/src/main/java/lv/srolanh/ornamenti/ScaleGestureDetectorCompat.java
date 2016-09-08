package lv.srolanh.ornamenti;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.ScaleGestureDetector;

/**
 * Created by srolanh on 16.1.7.
 */
public class ScaleGestureDetectorCompat {

    @TargetApi(11)
    public static float getCurrentSpanX(ScaleGestureDetector scaleGestureDetector) {
        if (Build.VERSION.SDK_INT >= 11) {
            return scaleGestureDetector.getCurrentSpanX();
        } else {
            return scaleGestureDetector.getCurrentSpan();
        }
    }

    @TargetApi(11)
    public static float getCurrentSpanY(ScaleGestureDetector scaleGestureDetector) {
        if (Build.VERSION.SDK_INT >= 11) {
            return scaleGestureDetector.getCurrentSpanY();
        } else {
            return scaleGestureDetector.getCurrentSpan();
        }
    }
}
