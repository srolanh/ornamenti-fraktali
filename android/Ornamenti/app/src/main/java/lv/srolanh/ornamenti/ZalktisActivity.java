package lv.srolanh.ornamenti;

import android.os.Bundle;

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
