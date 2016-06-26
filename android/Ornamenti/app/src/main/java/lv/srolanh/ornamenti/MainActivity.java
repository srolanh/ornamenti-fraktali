package lv.srolanh.ornamenti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static int[] dimensions = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        Button gen = (Button) findViewById(R.id.action_gen);
        final RadioGroup group = (RadioGroup) findViewById(R.id.group);
        if (gen != null) {
            gen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    int checkedId;
                    if (group != null) {
                        checkedId = group.getCheckedRadioButtonId();
                    } else {
                        checkedId = -1;
                    }
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.kiegelis:
                            intent = new Intent(v.getContext(), KiegelisActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case R.id.ugunskrusts:
                            intent = new Intent(v.getContext(), UgunskrustsActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case R.id.mugunskrusts:
                            intent = new Intent(v.getContext(), MUgunskrustsActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case R.id.l_ugunskrusts:
                            intent = new Intent(v.getContext(), LUgunskrustsActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case R.id.krusts:
                            intent = new Intent(v.getContext(), KrustsActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        case R.id.zalktis:
                            intent = new Intent(v.getContext(), ZalktisActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                        default:
                            throw new IllegalArgumentException("Undefined checked radio button value");
                    }
                }
            });
        }
        Button info = (Button) findViewById(R.id.action_info);
        Button settings = (Button) findViewById(R.id.action_settings);
        if (info != null) {
            info.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Info")
                            .setMessage("Latviešu ornamentu ģenerators pēc fraktāliskiem principiem\n\n" +
                                    "1. Izvēlies sākuma stāvokli no iespējamajiem stāvokļiem\n\n" +
                                    "2. Nospied \"Ģenerēt\"\n\n" +
                                    "3. Spied pogas, lai ģenerētu nākamā līmeņa ornamentus\n\n" +
                                    "4. Ja nepieciešams, spied un turi, lai saglabātu ornamentu")
                            .setPositiveButton("Labi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        if (settings != null) {
            settings.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        dimensions[0] = size.x;
        dimensions[1] = size.y;
    }

    public static boolean[] getExternalStorageState() {
        boolean storageAvailable;
        boolean storageWritable;
        boolean[] stateArray = new boolean[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            storageAvailable = storageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            storageAvailable = true;
            storageWritable = false;
        } else {
            storageAvailable = storageWritable = false;
        }
        stateArray[0] = storageAvailable;
        stateArray[1] = storageWritable;
        return stateArray;
    }

    public static class OrnamentView extends View {

        private ArrayList<ArrayList<Integer>> image, prevImage;
        private int level;
        private int imageID;
        private ArrayList<ArrayList<Integer>> defaultImage;
        public boolean repeatMiddle;
        public boolean repeatQuarter;
        private int screenWidth;
        private int screenHeight;
        private SharedPreferences preferences;
        private String prefRepeatMethod;
        private Bitmap bitmap;
        private Bitmap scaledBitmap;
        private static final Paint bitmapPaint = new Paint();
        private Canvas bitmapCanvas;
        private int rSize;
        private final Context context;

        public OrnamentView(Context context) {
            super(context);
            this.context = context;
            this.screenWidth = dimensions[0];
            this.screenHeight = dimensions[1];
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
        }

        private File getStorageFile() {
            boolean[] storageState = getExternalStorageState();
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
            this.imageID = imageID;
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
            this.image = MainGenerator.genFractal(this.image, inverse, this.level + 1,
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
            if (this.image == MainGenerator.errorHandler) {
                this.setImage(MainGenerator.constants[this.imageID], 0, this.imageID);
            } else {
                this.rSize = (int) Math.floor(this.screenWidth / this.image.get(0).size());
                if (this.rSize == 0) {
                    this.rSize = 1;
                }
                int bitmapWidth = this.rSize * this.image.get(0).size();
                int bitmapHeight = this.rSize * this.image.size();
                this.bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565);
                this.bitmapCanvas = new Canvas(this.bitmap);
            }
            this.invalidate();
        }

        public Bitmap resizeBitmapToScreen(Bitmap bitmap, int screenWidth, int screenHeight) {
            float bitmapRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
            float screenRatio = (float) screenWidth / (float) screenHeight;
            int width = screenWidth;
            int height = screenHeight;
            if (screenRatio > 1) {
                width = (int) ((float) screenHeight * bitmapRatio);
            } else {
                height = (int) ((float) screenWidth / bitmapRatio);
            }
            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (this.image != this.prevImage || this.level == 0) {
                try {
                    MainGenerator.drawImage(this.context, this.bitmapCanvas, this.image, this.rSize);
                    this.bitmap = resizeBitmapToScreen(this.bitmap, this.screenWidth, this.screenHeight);
                    canvas.drawBitmap(this.bitmap, 0, 0, bitmapPaint);
                } catch (OutOfMemoryError oom) {
                    oom.printStackTrace();
                    MainGenerator.showOutOfMemoryErrorDialog(this.context);
                    //this.setImage(MainGenerator.errorHandler, 0, this.imageID);
                }
            }
        }
    }
}