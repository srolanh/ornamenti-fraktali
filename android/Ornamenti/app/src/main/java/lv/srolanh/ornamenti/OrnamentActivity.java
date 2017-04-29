package lv.srolanh.ornamenti;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
    public static final int ZALKTIS2_ORN_INDEX = 6;
    public static final int JUMIS_KOKS_ORN_INDEX = 7;
    private int ornIndex;
    public String ornName;
    public ArrayList<ArrayList<Boolean>> image;
    public int level;
    public OrnamentView vOrnament;
    
    public void onCreate(Bundle savedInstanceState) {
        boolean isRestoredFromImage = false;
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("image") && savedInstanceState.containsKey("level")) {
                isRestoredFromImage = true;
                image = (ArrayList) savedInstanceState.getSerializable("image");
                level = savedInstanceState.getInt("level");
            }
        }
        MainGenerator generator = new MainGenerator(this, ornIndex);

        setContentView(R.layout.activity_ornament_base);

        vOrnament = (OrnamentView) findViewById(R.id.ornament);
        if (isRestoredFromImage) {
            vOrnament.setImage(image, level, ornIndex);
        } else {
            vOrnament.setImage(generator.constants[ornIndex], 0, ornIndex);
            image = generator.constants[ornIndex];
            level = 0;
        }

        Button genInverse = (Button) findViewById(R.id.gen_inverse_btn);
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

        Button gen = (Button) findViewById(R.id.gen_btn);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_image:
                if (ContextCompat.checkSelfPermission(vOrnament.getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) vOrnament.getContext(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                if (ContextCompat.checkSelfPermission(vOrnament.getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) vOrnament.getContext(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                }
                vOrnament.saveImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("image", image);
        outState.putInt("level", level);
    }

    public void setOrnamentParams(int ornIndex, String ornName) {
        this.ornIndex = ornIndex;
        this.ornName = ornName;
    }
    
}