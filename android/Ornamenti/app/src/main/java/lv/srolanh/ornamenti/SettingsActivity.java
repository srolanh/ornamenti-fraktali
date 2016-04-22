package lv.srolanh.ornamenti;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by srolanh on 16.18.4.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        setContentView(R.layout.settings_layout);
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
        bar.setTitle("Iestatījumi");
    }

}
