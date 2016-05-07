package lv.srolanh.ornamenti;

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
 * Created by srolanh on 16.2.5.
 */
public class ZalktisActivity extends ActionBarActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final MainActivity.OrnamentView zalktis = new MainActivity.OrnamentView(this);
        ArrayList[] constants = MainGenerator.init();
        zalktis.setImage(constants[5], 0);
        zalktis.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        zalktis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("zalktis", "Registered long press");
                zalktis.saveImage(v.getContext());
                return true;
            }
        });

        RelativeLayout buttonLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lparams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        Button genInverse = new Button(this);
        genInverse.setText("Ģenerēt inverso");
        RelativeLayout.LayoutParams genInverseParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        genInverseParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        genInverseParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        genInverse.setLayoutParams(genInverseParams);
        genInverse.setId(R.id.gen_inverse_zalktis);
        genInverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zalktis.updateImage(true);
                zalktis.invalidate();
            }
        });

        Button gen = new Button(this);
        gen.setText("Ģenerēt parasto");
        RelativeLayout.LayoutParams genParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        genParams.addRule(RelativeLayout.ABOVE, R.id.gen_inverse_zalktis);
        genParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gen.setLayoutParams(genParams);
        gen.setId(R.id.gen_zalktis);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zalktis.updateImage(false);
                zalktis.invalidate();
            }
        });

        buttonLayout.addView(gen);
        buttonLayout.addView(genInverse);
        layout.addView(zalktis);
        layout.addView(buttonLayout);
        setContentView(layout);
    }
    
}
