package lv.srolanh.ornamenti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by srolanh on 16.4.6.
 */
public class OrnamentActivity extends ActionBarActivity {
    
    public static final int KIEGELIS_ORN_INDEX = 0;
    public static final int UGUNSKRUSTS_ORN_INDEX = 1;
    public static final int M_UGUNSKRUSTS_ORN_INDEX = 2;
    public static final int L_UGUNSKRUSTS_ORN_INDEX = 3;
    public static final int KRUSTS_ORN_INDEX = 4;
    public static final int ZALKTIS_ORN_INDEX = 5;
    private int ornIndex;
    public String ornName;
    private View vGlobal;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final MainActivity.OrnamentView vOrnament = new MainActivity.OrnamentView(this);
        ArrayList[] constants = MainGenerator.init();
        vOrnament.setImage(constants[this.ornIndex], 0);
        vOrnament.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        vOrnament.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vGlobal = v;
                Log.d(OrnamentActivity.this.ornName, "Registered long press");
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setItems(R.array.save_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            vOrnament.saveImage(vGlobal.getContext());
                        } else {
                            Log.e(OrnamentActivity.this.ornName, "Unknown option in context dialog");
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        RelativeLayout buttonLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lparams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        Button genInverse = new Button(this);
        genInverse.setText(R.string.action_gen_next_inverse);
        RelativeLayout.LayoutParams genInverseParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        genInverseParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        genInverseParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        genInverse.setLayoutParams(genInverseParams);
        genInverse.setId(R.id.gen_inverse_btn);
        genInverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vOrnament.updateImage(true);
                vOrnament.invalidate();
            }
        });

        Button gen = new Button(this);
        gen.setText(R.string.action_gen_next);
        RelativeLayout.LayoutParams genParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        genParams.addRule(RelativeLayout.ABOVE, R.id.gen_inverse_btn);
        genParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gen.setLayoutParams(genParams);
        gen.setId(R.id.gen_btn);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vOrnament.updateImage(false);
                vOrnament.invalidate();
            }
        });

        buttonLayout.addView(gen);
        buttonLayout.addView(genInverse);
        layout.addView(vOrnament);
        layout.addView(buttonLayout);
        setContentView(layout);
    }
    
    public void setOrnamentParams(int ornIndex, String ornName) {
        this.ornIndex = ornIndex;
        this.ornName = ornName;
    }
    
}