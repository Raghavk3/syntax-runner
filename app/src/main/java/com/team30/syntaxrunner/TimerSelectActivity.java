package com.team30.syntaxrunner;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerSelectActivity extends AppCompatActivity {

    private int selectedDuration = 10; // default
    private TextView selectedButton = null;
    private TextView timerDisplay;

    private static final int[] DURATIONS = {5, 10, 15, 20, 25};

    // Positions arranged in a circle (top, right, bottom-right, bottom-left, left)
    // Angles: 5=top(270°), 10=right(342°), 15=bottom-right(54°), 20=bottom-left(126°), 25=left(198°)
    private static final double[] ANGLES = {-90, -18, 54, 126, 198}; // degrees from 3 o'clock

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_select);

        String characterId = getIntent().getStringExtra("characterId");
        String topic = getIntent().getStringExtra("topic");
        String difficulty = getIntent().getStringExtra("difficulty");

        timerDisplay = findViewById(R.id.timerDisplay);
        FrameLayout buttonsOverlay = findViewById(R.id.buttonsOverlay);
        Button btnStartRun = findViewById(R.id.btnStartRun);
        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        updateTimerDisplay();

        int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());
        int containerSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
        int center = containerSize / 2;

        for (int i = 0; i < DURATIONS.length; i++) {
            final int index = i;
            final int duration = DURATIONS[i];

            TextView btn = new TextView(this);
            btn.setText(String.valueOf(duration));
            btn.setTextSize(14);
            btn.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            btn.setGravity(Gravity.CENTER);
            btn.setTextColor(Color.parseColor("#8B949E"));

            if (duration == selectedDuration) {
                btn.setBackground(getDrawable(R.drawable.bg_timer_button_selected));
                btn.setTextColor(Color.WHITE);
                selectedButton = btn;
            } else {
                btn.setBackground(getDrawable(R.drawable.bg_timer_button));
            }

            // Position on circle
            double angleRad = Math.toRadians(ANGLES[i]);
            int x = (int) (center + radius * Math.cos(angleRad) - buttonSize / 2);
            int y = (int) (center + radius * Math.sin(angleRad) - buttonSize / 2);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(buttonSize, buttonSize);
            params.leftMargin = x;
            params.topMargin = y;
            btn.setLayoutParams(params);

            // Stagger entrance animation
            btn.setAlpha(0f);
            btn.setScaleX(0f);
            btn.setScaleY(0f);
            btn.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)
                    .setStartDelay(200 + index * 80L)
                    .start();

            btn.setOnClickListener(v -> {
                // Deselect previous
                if (selectedButton != null) {
                    selectedButton.setBackground(getDrawable(R.drawable.bg_timer_button));
                    selectedButton.setTextColor(Color.parseColor("#8B949E"));
                    selectedButton.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
                }

                // Select new
                selectedDuration = duration;
                selectedButton = btn;
                btn.setBackground(getDrawable(R.drawable.bg_timer_button_selected));
                btn.setTextColor(Color.WHITE);
                btn.animate().scaleX(1.15f).scaleY(1.15f).setDuration(200).start();

                // Animate timer display
                timerDisplay.animate()
                        .scaleX(0.9f).scaleY(0.9f).alpha(0f)
                        .setDuration(150)
                        .withEndAction(() -> {
                            updateTimerDisplay();
                            timerDisplay.animate()
                                    .scaleX(1f).scaleY(1f).alpha(1f)
                                    .setDuration(200).start();
                        }).start();
            });

            buttonsOverlay.addView(btn);
        }

        btnStartRun.setOnClickListener(v -> {
            Intent intent = new Intent(TimerSelectActivity.this, GameActivity.class);
            intent.putExtra("characterId", characterId);
            intent.putExtra("topic", topic);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("durationMinutes", selectedDuration);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void updateTimerDisplay() {
        timerDisplay.setText(String.format("%d:00", selectedDuration));
    }
}
