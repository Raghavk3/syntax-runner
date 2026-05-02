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

    private static final String[] TOPICS = {"DSA", "OOP", "Fundamentals", "Algorithms"};
    private static final String[] DIFFICULTIES = {"EASY", "MEDIUM", "HARD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        String characterId = getIntent().getStringExtra("characterId");

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

        // Build topic grid (2x2)
        for (int i = 0; i < TOPICS.length; i++) {
            final int index = i;
            final String topic = TOPICS[i];

            TextView card = createSelectionCard(topic, cardWidth, cardHeight);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cardWidth;
            params.height = cardHeight;
            params.setMargins(spacing / 2, spacing / 2, spacing / 2, spacing / 2);
            params.columnSpec = GridLayout.spec(i % 2);
            params.rowSpec = GridLayout.spec(i / 2);
            card.setLayoutParams(params);

            // Stagger animation
            card.setAlpha(0f);
            card.setScaleX(0.9f);
            card.setScaleY(0.9f);
            card.animate().alpha(1f).scaleX(1f).scaleY(1f)
                    .setDuration(350).setStartDelay(index * 70L).start();

            card.setOnClickListener(v -> {
                if (selectedTopicView != null) {
                    selectedTopicView.setBackground(getDrawable(R.drawable.bg_card_unselected));
                    ((TextView) selectedTopicView).setTextColor(Color.parseColor("#8B949E"));
                    selectedTopicView.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
                }
                selectedTopic = topic;
                selectedTopicView = card;
                card.setBackground(getDrawable(R.drawable.bg_card_selected));
                card.setTextColor(Color.WHITE);
                card.animate().scaleX(1.03f).scaleY(1.03f).setDuration(150).start();
                updateNextButton(btnNextStep);
            });

            topicGrid.addView(card);
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

            // Stagger animation with horizontal slide
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
        if (selectedTopic != null && selectedDifficulty != null) {
            btn.setEnabled(true);
            btn.animate().alpha(1f).setDuration(300).start();
        }
    }
}
