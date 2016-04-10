package lv.srolanh.ornamenti;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by srolanh on 16.6.4.
 */
public class MUgunskrustsActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final MainActivity.FractalView mukrusts = new MainActivity.FractalView(this);
        ArrayList[] constants = MainGenerator.init();
        mukrusts.setImage(constants[2], 0);
        mukrusts.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mukrusts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mukrusts.updateImage(false);
                mukrusts.invalidate();
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
        genInverse.setId(R.id.gen_inverse_m_ugunskrusts);
        genInverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mukrusts.updateImage(true);
                mukrusts.invalidate();
            }
        });

        Button gen = new Button(this);
        gen.setText("Ģenerēt parasto");
        RelativeLayout.LayoutParams genParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        genParams.addRule(RelativeLayout.ABOVE, R.id.gen_inverse_m_ugunskrusts);
        genParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gen.setLayoutParams(genParams);
        gen.setId(R.id.gen_m_ugunskrusts);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mukrusts.updateImage(false);
                mukrusts.invalidate();
            }
        });

        buttonLayout.addView(gen);
        buttonLayout.addView(genInverse);
        layout.addView(mukrusts);
        layout.addView(buttonLayout);
        setContentView(layout);
    }

}
