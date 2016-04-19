package lv.srolanh.ornamenti;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by srolanh on 16.6.4.
 */
public class KiegelisActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        final MainActivity.OrnamentView kiegelis = new MainActivity.OrnamentView(this);
        ArrayList[] constants = MainGenerator.init();
        kiegelis.setImage(constants[0], 0);
        kiegelis.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        kiegelis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("Kiegelis", "Registered long press");
                kiegelis.saveImage(v.getContext());
                return true;
            }
        });

        RelativeLayout buttonLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lparams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        Button genInverse = new Button(this);
        genInverse.setText("Ģenerēt inverso");
        RelativeLayout.LayoutParams genInverseParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        genInverseParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        genInverseParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        genInverse.setLayoutParams(genInverseParams);
        genInverse.setId(R.id.gen_inverse_kiegelis);
        genInverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiegelis.updateImage(true);
                kiegelis.invalidate();
            }
        });

        Button gen = new Button(this);
        gen.setText("Ģenerēt parasto");
        RelativeLayout.LayoutParams genParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        genParams.addRule(RelativeLayout.ABOVE, R.id.gen_inverse_kiegelis);
        genParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gen.setLayoutParams(genParams);
        gen.setId(R.id.gen_kiegelis);
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiegelis.updateImage(false);
                kiegelis.invalidate();
            }
        });

        buttonLayout.addView(gen);
        buttonLayout.addView(genInverse);
        layout.addView(kiegelis);
        layout.addView(buttonLayout);
        setContentView(layout);
    }

}
