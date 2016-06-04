package lv.srolanh.ornamenti;

import android.os.Bundle;

/**
 * Created by srolanh on 16.22.4
 *
 */
public class KrustsActivity extends OrnamentActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.KRUSTS_ORN_INDEX, "Krusts");
        super.onCreate(savedInstanceState);
    }
    
}
