package lv.srolanh.ornamenti;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by srolanh on 16.27.6.
 */
public class OrnamentView extends View {

    private ArrayList<ArrayList<Boolean>> image;
    private int level;
    public boolean repeatMiddle;
    public boolean repeatQuarter;
    private int screenWidth;
    private int screenHeight;
    private Bitmap bitmap;
    private static final Paint bitmapPaint = new Paint();
    private Canvas bitmapCanvas;
    private int rSize;
    private final Context context;
    private final MainGenerator generator;
    private ScaleGestureDetector sgd;
    private float zoomFactor = 1.0f;
    private boolean imageChanged;
    private int activePointerID = -1;
    private float lastTouchX, lastTouchY;
    private float posX = 0.0f;
    private float posY = 0.0f;
    public int canvasWidth = 0, canvasHeight = 0;

    public OrnamentView(Context context) {
        this(context, null, 0);
    }

    public OrnamentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrnamentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        SharedPreferences preferences;
        String prefRepeatMethod;
        ArrayList<ArrayList<Boolean>> defaultImage;
        this.context = context;
        generator = new MainGenerator(context);
        screenWidth = MainActivity.dimensions[0];
        screenHeight = MainActivity.dimensions[1];
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefRepeatMethod = preferences.getString("pref_gen_repeat", "MIDDLE");
        switch (prefRepeatMethod) {
            case "NONE":
                repeatMiddle = false;
                repeatQuarter = false;
                break;
            case "MIDDLE":
                repeatMiddle = true;
                repeatQuarter = false;
                break;
            case "QUARTER":
                repeatMiddle = true;
                repeatQuarter = true;
                break;
            default:
                throw new IllegalArgumentException("Undefined argument for repeat method value");
        }
        defaultImage = new ArrayList<>(1);
        defaultImage.add(new ArrayList<Boolean>(1));
        defaultImage.get(0).add(true);
        sgd = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                zoomFactor *= detector.getScaleFactor();
                zoomFactor = Math.max(1.0f, Math.min(zoomFactor, 5.0f));
                ViewCompat.postInvalidateOnAnimation(OrnamentView.this);
                return true;
            }
        });
        imageChanged = true;
    }

    private File getStorageFile() {
        boolean[] storageState = MainActivity.getExternalStorageState();
        if (!storageState[0]) {
            Toast.makeText(context, "Atmiņa nav pieejama", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!storageState[1]) {
            Toast.makeText(context, "Atmiņa nav rakstāma", Toast.LENGTH_SHORT).show();
        }
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Ornamenti");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                //Log.i("FileOperation", "Directory created successfully");
            } else {
                //Log.w("FileOperation", "Possible problem with directory creation");
            }
        }
        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmm", Locale.US).format(new Date());
        File storageFile;
        int counter = 0;
        String filename = "Ornaments_" + timestamp + "_" + counter + ".png";
        storageFile = new File(directory.getPath() + File.separator + filename);
        while (storageFile.exists()) {
            counter++;
            filename = "Ornaments_" + timestamp + "_" + counter + ".png";
            storageFile = new File(directory.getPath() + File.separator + filename);
        }
        return storageFile;
    }

    public void setImage(ArrayList<ArrayList<Boolean>> image, int level, int imageID) {
        this.image = image;
        this.level = level;
        generator.imageID = imageID;
        generator.setLevel(level);
        onImageChange();
    }

    public ArrayList<ArrayList<Boolean>> getImage() {
        return image;
    }

    public int getLevel() {
        return level;
    }

    public void updateImage(boolean inverse) {
        image = generator.genFractal(image, inverse, level + 1,
                repeatMiddle, repeatQuarter);
        level += 1;
        onImageChange();
    }

    public void saveImage() {
        File imageFile = getStorageFile();
        if (imageFile == null) {
            return;
        }
        try {
            FileOutputStream output = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, output);
            MediaScannerConnection.scanFile(context, new String[]{imageFile.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            //Log.i("FileOperation", "Scanned file to media successfully");
                        }
                    }
            );
            Toast.makeText(context, "Raksts saglabāts kā: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onImageChange() {
        rSize = (int) Math.floor(screenWidth / image.get(0).size());
        if (rSize == 0) {
            rSize = 1;
        }
        int bitmapWidth = rSize * image.get(0).size();
        int bitmapHeight = rSize * image.size();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565);
        bitmapCanvas = new Canvas(bitmap);
        if (generator.getLevel() == 0 && level > 1) {
            level = 0;
        } else if (generator.getLevel() != level) {
            generator.setLevel(level);
        }
        if (generator.getImageChanged()) {
            imageChanged = true;
            zoomFactor = 1.0f;
            posX = 0.0f;
            posY = 0.0f;
        }
        invalidate();
    }

    public Bitmap resizeBitmapToScreen(Bitmap bitmap, int screenWidth, int screenHeight) {
        float bitmapRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
        float screenRatio = (float) screenWidth / (float) screenHeight;
        int width = screenWidth;
        int height = screenHeight;
        int orientation = context.getResources().getConfiguration().orientation;
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                if (screenRatio > 1) {
                    width = (int) ((float) screenHeight * bitmapRatio);
                } else {
                    height = (int) ((float) screenWidth / bitmapRatio);
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                if (screenRatio < 1) {
                    width = (int) ((float) screenHeight * bitmapRatio);
                } else {
                    height = (int) ((float) screenWidth / bitmapRatio);
                }
                break;
            default:
                throw new IllegalStateException("Unknown orientation");
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        sgd.onTouchEvent(e);
        final int action = MotionEventCompat.getActionMasked(e);
        int pointerID, pointerIndex;
        float x, y, dX, dY;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pointerIndex = MotionEventCompat.getActionIndex(e);
                x = MotionEventCompat.getX(e, pointerIndex);
                y = MotionEventCompat.getY(e, pointerIndex);
                lastTouchX = x;
                lastTouchY = y;
                activePointerID = MotionEventCompat.getPointerId(e, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = MotionEventCompat.findPointerIndex(e, activePointerID);
                x = MotionEventCompat.getX(e, pointerIndex);
                y = MotionEventCompat.getY(e, pointerIndex);
                dX = x - lastTouchX;
                dY = y - lastTouchY;
                posX += dX;
                posY += dY;
                if (posX < -((float) (canvasWidth) * (zoomFactor - 1.0f)) / 2) {
                    posX = -((float) (canvasWidth) * (zoomFactor - 1.0f)) / 2;
                }
                if (posX > 0) {
                    posX = 0;
                }
                if (posY < -((float) (canvasHeight) * (zoomFactor - 1.0f)) / 2) {
                    posY = -((float) (canvasHeight) * (zoomFactor - 1.0f)) / 2;
                }
                if (posY > 0) {
                    posY = 0;
                }
                ViewCompat.postInvalidateOnAnimation(this);
                lastTouchX = x;
                lastTouchY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                activePointerID = -1;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointerIndex = MotionEventCompat.getActionIndex(e);
                pointerID = MotionEventCompat.getPointerId(e, pointerIndex);
                if (pointerID == activePointerID) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = MotionEventCompat.getX(e, newPointerIndex);
                    lastTouchY = MotionEventCompat.getY(e, newPointerIndex);
                    activePointerID = MotionEventCompat.getPointerId(e, newPointerIndex);
                }
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(zoomFactor, zoomFactor);
        canvas.translate(posX, posY);
        try {
            if (imageChanged) {
                MainGenerator.drawImage(context, bitmapCanvas, image, rSize);
                bitmap = resizeBitmapToScreen(bitmap, screenWidth, screenHeight);
                imageChanged = false;
                generator.onImageDisplayed();
            }
            canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
            MainGenerator.showOutOfMemoryErrorDialog(context);
            //setImage(MainGenerator.errorHandler, 0, imageID);
        }
        canvas.restore();
    }

}
