package lv.srolanh.ornamenti;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.view.Window;

/**
 * Created by srolanh on 16.18.4.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
