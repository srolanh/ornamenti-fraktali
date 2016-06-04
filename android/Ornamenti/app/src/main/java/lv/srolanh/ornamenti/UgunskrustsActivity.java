package lv.srolanh.ornamenti;

import android.os.Bundle;

/**
 * Created by srolanh on 16.6.4.
 */
public class UgunskrustsActivity extends OrnamentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.UGUNSKRUSTS_ORN_INDEX, "Ugunskrusts");
        super.onCreate(savedInstanceState);
    }

}
