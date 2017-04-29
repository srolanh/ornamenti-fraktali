package lv.srolanh.ornamenti;

import android.os.Bundle;

/**
 * Created by srolanh on 16.16.9.
 */
public class JumisKoksActivity extends OrnamentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setOrnamentParams(OrnamentActivity.JUMIS_KOKS_ORN_INDEX, "Jumis / Koks");
        super.onCreate(savedInstanceState);
    }

}
