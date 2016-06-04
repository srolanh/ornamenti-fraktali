package lv.srolanh.ornamenti;

import android.os.Bundle;

/**
 * Created by srolanh on 16.6.4.
 */
public class MUgunskrustsActivity extends OrnamentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.M_UGUNSKRUSTS_ORN_INDEX, "MUgunskrusts");
        super.onCreate(savedInstanceState);
    }

}
