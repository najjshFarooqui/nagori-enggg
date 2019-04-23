package com.example.applligent.nagoriengineering.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.service.OnSwipeTouchListener;

public class SwipeGesture extends AppCompatActivity {
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_gesture);
        image = findViewById(R.id.image_swipe);
        image.setOnTouchListener(new OnSwipeTouchListener(SwipeGesture.this) {
            public void onSwipeTop() {
                Toast.makeText(SwipeGesture.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(SwipeGesture.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(SwipeGesture.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(SwipeGesture.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
