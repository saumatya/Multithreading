package com.example.polar_watch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.polar_watch.databinding.ActivityStartTrackingBinding;

public class StartTrackingActivity extends Activity {

    private TextView mTextView;
    private ActivityStartTrackingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mTextView = binding.text;

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTrackingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}