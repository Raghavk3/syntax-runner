package com.team30.syntaxrunner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CharacterSelectActivity extends AppCompatActivity {

    private String selectedCharacter = null;
    private View selectedView = null;

    private static final String[] CHARACTERS = {
            "astronaut", "robot", "hacker",
            "alien", "ninja", "wizard",
            "cyborg", "samurai", "mecha",
            "zombie"
    };

    private static final String[] CHARACTER_EMOJIS = {
            "👨‍🚀", "🤖", "💻",
            "👽", "🥷", "🧙",
            "🦾", "⚔️", "🎮",
            "🧟"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        GridLayout grid = findViewById(R.id.characterGrid);
        Button btnConfirm = findViewById(R.id.btnConfirm);
        TextView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        int cardSize = (screenWidth - padding - (spacing * 2)) / 3;

        for (int i = 0; i < CHARACTERS.length; i++) {
            final int index = i;
            final String characterId = CHARACTERS[i];

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setGravity(Gravity.CENTER);
            card.setBackground(getDrawable(R.drawable.bg_card_unselected));
            card.setPadding(8, 16, 8, 16);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cardSize;
            params.height = cardSize;
            params.setMargins(spacing / 2, spacing / 2, spacing / 2, spacing / 2);
            params.columnSpec = GridLayout.spec(i % 3);
            params.rowSpec = GridLayout.spec(i / 3);
            card.setLayoutParams(params);

            // Avatar emoji placeholder
            TextView emoji = new TextView(this);
            emoji.setText(CHARACTER_EMOJIS[i]);
            emoji.setTextSize(32);
            emoji.setGravity(Gravity.CENTER);
            card.addView(emoji);

            // Character name
            TextView name = new TextView(this);
            name.setText(characterId.toUpperCase());
            name.setTextColor(Color.parseColor("#8B949E"));
            name.setTextSize(10);
            name.setGravity(Gravity.CENTER);
            name.setTypeface(android.graphics.Typeface.MONOSPACE);
            name.setPadding(0, 8, 0, 0);
            card.addView(name);

            // Stagger animation on enter
            card.setAlpha(0f);
            card.setScaleX(0.9f);
            card.setScaleY(0.9f);
            card.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)
                    .setStartDelay(index * 60L)
                    .start();

            card.setOnClickListener(v -> {
                // Deselect previous
                if (selectedView != null) {
                    selectedView.setBackground(getDrawable(R.drawable.bg_card_unselected));
                    selectedView.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
                }
                // Select new
                selectedCharacter = characterId;
                selectedView = card;
                card.setBackground(getDrawable(R.drawable.bg_card_selected));
                card.animate().scaleX(1.05f).scaleY(1.05f).setDuration(200).start();

                // Enable confirm button
                btnConfirm.setEnabled(true);
                btnConfirm.animate().alpha(1f).setDuration(300).start();
            });

            grid.addView(card);
        }

        btnConfirm.setOnClickListener(v -> {
            if (selectedCharacter != null) {
                Intent intent = new Intent(CharacterSelectActivity.this, ConfigureActivity.class);
                intent.putExtra("characterId", selectedCharacter);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
