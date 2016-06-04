package lv.srolanh.ornamenti;

import android.os.Bundle;

/**
 * Created by srolanh on 16.11.4.
 */
public class LUgunskrustsActivity extends OrnamentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.L_UGUNSKRUSTS_ORN_INDEX, "LUgunskrusts");
        super.onCreate(savedInstanceState);
    }
}
