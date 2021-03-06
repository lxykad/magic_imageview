package com.lxy.zoom.image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToBigImg(View view) {
        startActivity(new Intent(view.getContext(), BigImgActivity.class));
    }

    public void goType(View view) {
        startActivity(new Intent(view.getContext(), ScaleTypeActivity.class));
    }

    public void goShape(View view) {
        startActivity(new Intent(view.getContext(), ShapeActivity.class));
    }

    public void goGroup(View view) {
        startActivity(new Intent(view.getContext(), AnimationActivity.class));
    }
}
