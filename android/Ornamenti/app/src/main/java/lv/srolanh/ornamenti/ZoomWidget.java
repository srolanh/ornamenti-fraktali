package lv.srolanh.ornamenti;

import android.content.Context;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by srolanh on 16.1.7.
 */
public class ZoomWidget {

    private Interpolator interpolator;
    private int animationLength;
    private long zoomStartTimestamp;
    private boolean zoomFinished = true;
    private float currentZoom, destZoom;

    ZoomWidget(Context context) {
        interpolator = new DecelerateInterpolator();
        animationLength = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public void forceFinish(boolean finish) {
        zoomFinished = finish;
    }

    public void abortAnimation() {
        zoomFinished = true;
        currentZoom = destZoom;
    }

    public void startZoom(float dest) {
        zoomStartTimestamp = SystemClock.elapsedRealtime();
        destZoom = dest;
        zoomFinished = false;
        currentZoom = 1.0f;
    }

    public boolean computeZoomActiveState() {
        if (zoomFinished) {
            return false;
        }
        long zoomTimeElapsed = SystemClock.elapsedRealtime() - zoomStartTimestamp;
        if (zoomTimeElapsed >= animationLength) {
            zoomFinished = true;
            currentZoom = destZoom;
            return false;
        }
        float interpolatorTime = zoomTimeElapsed * 1.0f / animationLength;
        currentZoom = destZoom * interpolator.getInterpolation(interpolatorTime);
        return true;
    }

    public float getCurrentZoomLevel() {
        return currentZoom;
    }
}
