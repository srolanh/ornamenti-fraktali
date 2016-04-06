package lv.srolanh.ornamenti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gen = (Button) findViewById(R.id.action_gen);
        final RadioGroup group = (RadioGroup) findViewById(R.id.group);
        gen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                int checkedId = group.getCheckedRadioButtonId();
                switch (checkedId){
                    case -1:
                        break;
                    case R.id.kiegelis:
                        intent = new Intent(v.getContext(), KiegelisActivity.class);
                        v.getContext().startActivity(intent);
                        break;
                    case R.id.ugunskrusts:
                        intent = new Intent(v.getContext(), UgunskrustsActivity.class);
                        v.getContext().startActivity(intent);
                        break;
                    case R.id.mugunskrusts:
                        intent = new Intent(v.getContext(), MUgunskrustsActivity.class);
                        v.getContext().startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
