package com.example.android.myappimgs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FullscreenActivity  extends AppCompatActivity {
    public static final String EXTRA_TASK_ID = "ssdddsf";
    private TextView tvm;
    private ImageView imgv;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrancescreen);
        tvm = findViewById(R.id.trip);
        imgv = this.findViewById(R.id.imageView2);
        String addr= getIntent().getStringExtra(EXTRA_TASK_ID);
        Log.d("sdds",addr);
        Glide.with(this).load(addr).into(imgv);
    }
}
