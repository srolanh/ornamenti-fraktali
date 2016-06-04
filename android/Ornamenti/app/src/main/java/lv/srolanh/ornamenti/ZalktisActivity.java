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
public class ZalktisActivity extends OrnamentActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.ZALKTIS_ORN_INDEX, "Zalktis");
        super.onCreate(savedInstanceState);
    }
    
}
