package lv.srolanh.ornamenti;

import android.os.Bundle;

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
