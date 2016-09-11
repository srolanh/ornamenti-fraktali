package lv.srolanh.ornamenti;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by srolanh on 16.4.6.
 */
public class OrnamentActivity extends AppCompatActivity {
    
    public static final int KIEGELIS_ORN_INDEX = 0;
    public static final int UGUNSKRUSTS_ORN_INDEX = 1;
    public static final int M_UGUNSKRUSTS_ORN_INDEX = 2;
    public static final int L_UGUNSKRUSTS_ORN_INDEX = 3;
    public static final int KRUSTS_ORN_INDEX = 4;
    public static final int ZALKTIS_ORN_INDEX = 5;
    private int ornIndex;
    public String ornName;
    public ArrayList<ArrayList<Integer>> image;
    public int level;
    private View vGlobal;
    
    public void onCreate(Bundle savedInstanceState) {
        boolean isRestoredFromImage = false;
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("image") && savedInstanceState.containsKey("level")) {
                isRestoredFromImage = true;
                this.image = (ArrayList) savedInstanceState.getSerializable("image");
                this.level = savedInstanceState.getInt("level");
            }
        }
        MainGenerator generator = new MainGenerator(this, this.ornIndex);

        setContentView(R.layout.activity_ornament_base);

        final OrnamentView vOrnament = (OrnamentView) findViewById(R.id.ornament);
        if (isRestoredFromImage) {
            vOrnament.setImage(this.image, this.level, this.ornIndex);
        } else {
            vOrnament.setImage(generator.constants[this.ornIndex], 0, this.ornIndex);
            this.image = generator.constants[this.ornIndex];
            this.level = 0;
        }
        vOrnament.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vGlobal = v;
                //Log.d(OrnamentActivity.this.ornName, "Registered long press");
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setItems(R.array.save_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (ContextCompat.checkSelfPermission(vGlobal.getContext(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) vGlobal.getContext(),
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                            }
                            if (ContextCompat.checkSelfPermission(vGlobal.getContext(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) vGlobal.getContext(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            }
                            vOrnament.saveImage(vGlobal.getContext());
                        } else {
                            //Log.e(OrnamentActivity.this.ornName, "Unknown option in context dialog");
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        Button genInverse = (Button) findViewById(R.id.gen_btn);
        if (genInverse != null) {
            genInverse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vOrnament.updateImage(true);
                    OrnamentActivity.this.image = vOrnament.getImage();
                    OrnamentActivity.this.level = vOrnament.getLevel();
                    vOrnament.invalidate();
                }
            });
        }

        Button gen = (Button) findViewById(R.id.gen_inverse_btn);
        if (gen != null) {
            gen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vOrnament.updateImage(false);
                    OrnamentActivity.this.image = vOrnament.getImage();
                    OrnamentActivity.this.level = vOrnament.getLevel();
                    vOrnament.invalidate();
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("image", this.image);
        outState.putInt("level", this.level);
    }

    public void setOrnamentParams(int ornIndex, String ornName) {
        this.ornIndex = ornIndex;
        this.ornName = ornName;
    }
    
}