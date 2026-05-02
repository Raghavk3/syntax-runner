package com.team30.syntaxrunner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logo = findViewById(R.id.logoText);
        TextView title = findViewById(R.id.titleText);
        TextView tagline = findViewById(R.id.taglineText);
        Button btnStart = findViewById(R.id.btnStart);

        // Fade + slide up animation for logo
        logo.setAlpha(0f);
        logo.setTranslationY(30f);
        logo.animate().alpha(1f).translationY(0f).setDuration(800).setStartDelay(200).start();

        // Title animation
        title.setAlpha(0f);
        title.setTranslationY(20f);
        title.animate().alpha(1f).translationY(0f).setDuration(700).setStartDelay(500).start();

        // Tagline animation
        tagline.setAlpha(0f);
        tagline.animate().alpha(1f).setDuration(800).setStartDelay(1000).start();

        // Button animation
        btnStart.setAlpha(0f);
        btnStart.setTranslationY(20f);
        btnStart.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(1200).start();

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CharacterSelectActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}