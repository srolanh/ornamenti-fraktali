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

    private ArrayList<ArrayList<Integer>> image, prevImage;
    private int level;
    private ArrayList<ArrayList<Integer>> defaultImage;
    public boolean repeatMiddle;
    public boolean repeatQuarter;
    private int screenWidth;
    private int screenHeight;
    private SharedPreferences preferences;
    private String prefRepeatMethod;
    private Bitmap bitmap;
    private static final Paint bitmapPaint = new Paint();
    private Canvas bitmapCanvas;
    private int rSize;
    private final Context context;
    private final MainGenerator generator;
    private ScaleGestureDetector sgd;
    private float zoomFactor = 1.0f;
    private boolean imageChanged;

    public OrnamentView(Context context) {
        this(context, null, 0);
    }

    public OrnamentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrnamentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.generator = new MainGenerator(context);
        this.screenWidth = MainActivity.dimensions[0];
        this.screenHeight = MainActivity.dimensions[1];
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.prefRepeatMethod = preferences.getString("pref_gen_repeat", "MIDDLE");
        switch (this.prefRepeatMethod) {
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
        this.defaultImage = new ArrayList<>(1);
        this.defaultImage.add(new ArrayList<Integer>(1));
        this.defaultImage.get(0).add(1);
        this.sgd = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                zoomFactor *= detector.getScaleFactor();
                zoomFactor = Math.max(1.0f, Math.min(zoomFactor, 5.0f));
                ViewCompat.postInvalidateOnAnimation(OrnamentView.this);
                return true;
            }
        });
        this.imageChanged = true;
    }

    private File getStorageFile() {
        boolean[] storageState = MainActivity.getExternalStorageState();
        if (!storageState[0]) {
            Toast.makeText(this.context, "Atmiņa nav pieejama", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!storageState[1]) {
            Toast.makeText(this.context, "Atmiņa nav rakstāma", Toast.LENGTH_SHORT).show();
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

    public void setImage(ArrayList<ArrayList<Integer>> image, int level, int imageID) {
        this.image = image;
        this.prevImage = this.defaultImage;
        this.level = level;
        this.generator.imageID = imageID;
        this.generator.setLevel(level);
        this.onImageChange();
    }

    public ArrayList<ArrayList<Integer>> getImage() {
        return this.image;
    }

    public int getLevel() {
        return this.level;
    }

    public void updateImage(boolean inverse) {
        this.prevImage = this.image;
        this.image = this.generator.genFractal(this.image, inverse, this.level + 1,
                repeatMiddle, repeatQuarter);
        this.level += 1;
        this.onImageChange();
    }

    public void saveImage(Context context) {
        File imageFile = getStorageFile();
        if (imageFile == null) {
            return;
        }
        try {
            FileOutputStream output = new FileOutputStream(imageFile);
            this.bitmap.compress(Bitmap.CompressFormat.PNG, 90, output);
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
        this.rSize = (int) Math.floor(this.screenWidth / this.image.get(0).size());
        if (this.rSize == 0) {
            this.rSize = 1;
        }
        int bitmapWidth = this.rSize * this.image.get(0).size();
        int bitmapHeight = this.rSize * this.image.size();
        this.bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565);
        this.bitmapCanvas = new Canvas(this.bitmap);
        if (this.generator.getLevel() == 0 && this.level > 1) {
            this.level = 0;
        } else if (this.generator.getLevel() != this.level) {
            this.generator.setLevel(this.level);
        }
        this.imageChanged = (this.image != this.prevImage);
        this.invalidate();
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
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(zoomFactor, zoomFactor);
        try {
            if (this.imageChanged) {
                Log.d("Graphics", "Recalculating image");
                MainGenerator.drawImage(this.context, this.bitmapCanvas, this.image, this.rSize);
                this.bitmap = resizeBitmapToScreen(this.bitmap, this.screenWidth, this.screenHeight);
                this.imageChanged = false;
            }
            Log.d("Graphics", "Redrawing image");
            canvas.drawBitmap(this.bitmap, 0, 0, bitmapPaint);
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
            MainGenerator.showOutOfMemoryErrorDialog(this.context);
            //this.setImage(MainGenerator.errorHandler, 0, this.imageID);
        }
        canvas.restore();
    }

}
