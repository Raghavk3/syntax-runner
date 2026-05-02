package com.team30.syntaxrunner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        int score = getIntent().getIntExtra("score", 0);
        int accuracy = getIntent().getIntExtra("accuracy", 0);
        int correct = getIntent().getIntExtra("correct", 0);
        int missed = getIntent().getIntExtra("missed", 0);

        TextView scoreValue = findViewById(R.id.scoreValue);
        TextView accuracyValue = findViewById(R.id.accuracyValue);
        TextView correctValue = findViewById(R.id.correctValue);
        TextView missedValue = findViewById(R.id.missedValue);
        Button btnPlayAgain = findViewById(R.id.btnPlayAgain);

        scoreValue.setText(String.valueOf(score));
        accuracyValue.setText(accuracy + "%");
        correctValue.setText(String.valueOf(correct));
        missedValue.setText(String.valueOf(missed));

        // Animate stats rows in with stagger
        LinearLayout statsContainer = findViewById(R.id.statsContainer);
        for (int i = 0; i < statsContainer.getChildCount(); i++) {
            View row = statsContainer.getChildAt(i);
            row.setAlpha(0f);
            row.setTranslationY(20f);
            row.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400)
                    .setStartDelay(300 + i * 100L)
                    .start();
        }

        // Animate title
        TextView titleText = findViewById(R.id.titleText);
        titleText.setAlpha(0f);
        titleText.setScaleX(0.9f);
        titleText.setScaleY(0.9f);
        titleText.animate().alpha(1f).scaleX(1f).scaleY(1f)
                .setDuration(500).setStartDelay(100).start();

        // Animate button
        btnPlayAgain.setAlpha(0f);
        btnPlayAgain.setTranslationY(20f);
        btnPlayAgain.animate().alpha(1f).translationY(0f)
                .setDuration(400).setStartDelay(800).start();

        btnPlayAgain.setOnClickListener(v -> {
            Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }
}
