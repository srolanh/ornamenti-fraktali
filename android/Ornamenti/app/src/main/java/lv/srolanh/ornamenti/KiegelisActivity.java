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
public class KiegelisActivity extends OrnamentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.KIEGELIS_ORN_INDEX, "Kiegelis");
        super.onCreate(savedInstanceState);
    }

}
