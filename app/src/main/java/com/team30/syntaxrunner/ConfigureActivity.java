package com.team30.syntaxrunner;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigureActivity extends AppCompatActivity {

    private String selectedTopic = null;
    private String selectedDifficulty = null;
    private View selectedTopicView = null;
    private View selectedDifficultyView = null;

    // Fix 5: Only OOP module — no DSA, Fundamentals, or Algorithms
    private static final String[] TOPICS = {"OOP"};
    private static final String[] DIFFICULTIES = {"EASY", "MEDIUM", "HARD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        String characterId = getIntent().getStringExtra("characterId");
        int runnerDrawable = getIntent().getIntExtra("runner_drawable", R.drawable.ic_runner_astronaut);

        GridLayout topicGrid = findViewById(R.id.topicGrid);
        LinearLayout difficultyRow = findViewById(R.id.difficultyRow);
        Button btnNextStep = findViewById(R.id.btnNextStep);
        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int cardWidth = (screenWidth - (margin * 2) - spacing) / 2;
        int cardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 52, getResources().getDisplayMetrics());

        // Build topic grid — single OOP card, pre-selected
        for (int i = 0; i < TOPICS.length; i++) {
            final String topic = TOPICS[i];
            TextView card = createSelectionCard(topic, cardWidth, cardHeight);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cardWidth;
            params.height = cardHeight;
            params.setMargins(spacing / 2, spacing / 2, spacing / 2, spacing / 2);
            params.columnSpec = GridLayout.spec(0);
            params.rowSpec = GridLayout.spec(0);
            card.setLayoutParams(params);

            card.setAlpha(0f);
            card.setScaleX(0.9f);
            card.setScaleY(0.9f);
            card.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(350).start();

            topicGrid.addView(card);

            // Fix 5: Auto-select OOP — it is the only and pre-selected module
            selectedTopic = topic;
            selectedTopicView = card;
            card.setBackground(getDrawable(R.drawable.bg_card_selected));
            card.setTextColor(Color.WHITE);
            card.animate().scaleX(1.03f).scaleY(1.03f).setDuration(150).start();
        }

        // Build difficulty row
        int diffCardWidth = (screenWidth - (margin * 2) - (spacing * 2)) / 3;
        for (int i = 0; i < DIFFICULTIES.length; i++) {
            final int index = i;
            final String difficulty = DIFFICULTIES[i];

            TextView card = createSelectionCard(difficulty, diffCardWidth, cardHeight);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(diffCardWidth, cardHeight);
            params.setMargins(spacing / 2, 0, spacing / 2, 0);
            card.setLayoutParams(params);

            card.setAlpha(0f);
            card.setTranslationX(-15f);
            card.animate().alpha(1f).translationX(0f)
                    .setDuration(350).setStartDelay(300 + index * 70L).start();

            card.setOnClickListener(v -> {
                if (selectedDifficultyView != null) {
                    selectedDifficultyView.setBackground(getDrawable(R.drawable.bg_card_unselected));
                    ((TextView) selectedDifficultyView).setTextColor(Color.parseColor("#8B949E"));
                    selectedDifficultyView.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
                }
                selectedDifficulty = difficulty;
                selectedDifficultyView = card;
                card.setBackground(getDrawable(R.drawable.bg_card_selected));
                card.setTextColor(Color.WHITE);
                card.animate().scaleX(1.03f).scaleY(1.03f).setDuration(150).start();
                updateNextButton(btnNextStep);
            });

            difficultyRow.addView(card);
        }

        btnNextStep.setOnClickListener(v -> {
            if (selectedTopic != null && selectedDifficulty != null) {
                Intent intent = new Intent(ConfigureActivity.this, TimerSelectActivity.class);
                intent.putExtra("characterId", characterId);
                intent.putExtra("runner_drawable", runnerDrawable);
                intent.putExtra("topic", selectedTopic);
                intent.putExtra("difficulty", selectedDifficulty);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private TextView createSelectionCard(String text, int width, int height) {
        TextView card = new TextView(this);
        card.setText(text);
        card.setTextColor(Color.parseColor("#8B949E"));
        card.setTextSize(14);
        card.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        card.setGravity(Gravity.CENTER);
        card.setBackground(getDrawable(R.drawable.bg_card_unselected));
        return card;
    }

    private void updateNextButton(Button btn) {
        // selectedTopic is always "OOP" (pre-selected), so only difficulty must be chosen
        if (selectedDifficulty != null) {
            btn.setEnabled(true);
            btn.animate().alpha(1f).setDuration(300).start();
        }
    }
}
