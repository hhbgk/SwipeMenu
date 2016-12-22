package com.hhbgk.swipemenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, SimpleActivity.class));
                break;
            case R.id.button2:
                Intent i = new Intent(this, SimpleActivity.class);
                i.putExtra("stick_mode", true);
                startActivity(i);
                break;
            case R.id.button3:
                startActivity(new Intent(this, DifferentMenuActivity.class));
                break;
            case R.id.button4:
                Intent j = new Intent(this, DifferentMenuActivity.class);
                j.putExtra("stick_mode", true);
                startActivity(j);
                break;
            case R.id.button5:
                startActivity(new Intent(this, ExpandableListViewActivity.class));
                break;
        }
    }
}
