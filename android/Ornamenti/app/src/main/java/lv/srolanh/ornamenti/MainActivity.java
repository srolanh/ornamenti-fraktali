package lv.srolanh.ornamenti;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    public static int[] dimensions = new int[2];

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
                switch (checkedId) {
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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        this.dimensions[0] = size.x;
        this.dimensions[1] = size.y;
    }

    public static class FractalView extends View {

        private GestureDetectorCompat detector;

        /*class Gesture extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent event) {
                Log.d("Ornamenti", "double tap: event registered");
                updateImage(false);
                invalidate();
                return true;
            }

        }*/

        public ArrayList<ArrayList<Integer>> image;
        public int level;

        public FractalView(Context context) {
            super(context);
            //this.detector = new GestureDetectorCompat(this.getContext(), new Gesture());
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //this.detector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        public void setImage(ArrayList<ArrayList<Integer>> image, int level) {
            this.image = image;
            this.level = level;
        }

        public void updateImage(boolean inverse) {
            this.image = MainGenerator.genFractal(this.image, inverse, this.level + 1, null);
            this.level += 1;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            MainGenerator.drawImage(canvas, this.image);
        }

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
