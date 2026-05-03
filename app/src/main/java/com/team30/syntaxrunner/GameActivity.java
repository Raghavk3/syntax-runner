package com.team30.syntaxrunner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    // ── UI ────────────────────────────────────────────────────────────────────
    private TextView timerText;
    private TextView annotationText;
    private LinearLayout tokenContainer;
    private LinearLayout optionsContainer;
    private Button btnSubmit;
    private Button btnAbort;

    // ── Timer ─────────────────────────────────────────────────────────────────
    private CountDownTimer pomodoroTimer;
    private int durationMinutes;

    // ── Game setup ────────────────────────────────────────────────────────────
    private String difficulty;   // "EASY", "MEDIUM", or "HARD"
    private String topic;
    private String characterId;

    // ── Etienne's classes ─────────────────────────────────────────────────────
    private DifficultyManager difficultyManager;
    private Collector collector;
    private Judger judger;

    // ── Current question state ────────────────────────────────────────────────
    private Question currentQuestion;
    private SubQuestion currentSubQuestion;

    // picks: subQuestionNumber -> list of picked tokens in order
    // We always use subQuestion number 1 or 2, picked randomly
    // For simplicity we store picks per token index for the current subQuestion
    private List<String> currentTokenPicks = new ArrayList<>();

    // ── Session score ─────────────────────────────────────────────────────────
    private int score    = 0;
    private int correct  = 0;
    private int missed   = 0;
    private int attempted = 0;

    // ── Lifecycle ─────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Receive data from TimerSelectActivity
        durationMinutes = getIntent().getIntExtra("durationMinutes", 10);
        difficulty      = getIntent().getStringExtra("difficulty");
        topic           = getIntent().getStringExtra("topic");
        characterId     = getIntent().getStringExtra("characterId");

        // Bind UI views
        timerText        = findViewById(R.id.timerText);
        annotationText   = findViewById(R.id.annotationText);
        tokenContainer   = findViewById(R.id.tokenContainer);
        optionsContainer = findViewById(R.id.optionsContainer);
        btnSubmit        = findViewById(R.id.btnSubmit);
        btnAbort         = findViewById(R.id.btnAbort);

        // Set up Etienne's classes
        collector = new Collector();
        judger    = new Judger();

        // Convert the difficulty string from ConfigureActivity into the enum
        Difficulty difficultyEnum;
        switch (difficulty) {
            case "HARD":   difficultyEnum = Difficulty.HARD;   break;
            case "MEDIUM": difficultyEnum = Difficulty.MEDIUM; break;
            default:       difficultyEnum = Difficulty.EASY;   break;
        }
        difficultyManager = new DifficultyManager(difficultyEnum);

        // Buttons
        btnAbort.setOnClickListener(v -> endSession());
        btnSubmit.setOnClickListener(v -> onSubmit());

        // Start countdown
        startPomodoroTimer();

        // Load first question
        loadNextQuestion();
    }

    // ── POMODORO TIMER ────────────────────────────────────────────────────────

    private void startPomodoroTimer() {
        long totalMillis = durationMinutes * 60 * 1000L;

        pomodoroTimer = new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisRemaining) {
                long mins = millisRemaining / 60000;
                long secs = (millisRemaining % 60000) / 1000;
                timerText.setText(String.format("%02d:%02d", mins, secs));
            }

            @Override
            public void onFinish() {
                endSession();
            }
        }.start();
    }

    // ── LOAD NEXT QUESTION ────────────────────────────────────────────────────

    private void loadNextQuestion() {
        // Clear previous state
        currentTokenPicks.clear();
        tokenContainer.removeAllViews();
        optionsContainer.removeAllViews();

        // Pick a random raw question from the right difficulty pool
        String[][] rawQuestion = difficultyManager.pickRandomQuestion();
        if (rawQuestion == null) {
            endSession();
            return;
        }

        // Parse it into a Question object
        currentQuestion = new Question(rawQuestion);

        // Randomly pick subquestion 1 or 2
        List<SubQuestion> allSubs = currentQuestion.getAllSubQuestions();
        int randomIndex = (int)(Math.random() * allSubs.size());
        currentSubQuestion = allSubs.get(randomIndex);

        // Initialise picks list with the correct number of slots (one per token)
        List<String> tokens = currentSubQuestion.getTokens();
        currentTokenPicks.clear();
        for (int i = 0; i < tokens.size(); i++) {
            // Pre-fill with the original token — player only changes the one they tap
            currentTokenPicks.add(tokens.get(i));
        }

        // Show annotation (the comment hint lines)
        List<String> annotations = currentQuestion.getAnnotationLines();
        if (annotations != null && !annotations.isEmpty()) {
            annotationText.setText(android.text.TextUtils.join("\n", annotations));
        }

        // Render token buttons
        renderTokens();
    }

    // ── RENDER TOKENS ─────────────────────────────────────────────────────────

    private void renderTokens() {
        tokenContainer.removeAllViews();
        List<String> displayTokens = currentSubQuestion.getDisplayTokens();

        for (int i = 0; i < displayTokens.size(); i++) {
            final int tokenIndex = i;

            Button tokenBtn = new Button(this);
            tokenBtn.setText(displayTokens.get(i).trim());
            tokenBtn.setTextColor(Color.WHITE);
            tokenBtn.setBackgroundColor(Color.parseColor("#1E1E2E"));
            tokenBtn.setTextSize(13f);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            tokenBtn.setLayoutParams(params);

            tokenBtn.setOnClickListener(v -> showOptionsForToken(tokenIndex));
            tokenContainer.addView(tokenBtn);
        }
    }

    // ── SHOW OPTIONS FOR TAPPED TOKEN ─────────────────────────────────────────

    private void showOptionsForToken(int tokenIndex) {
        optionsContainer.removeAllViews();

        // Use display options for showing, clean options for recording
        List<String> displayOptions = currentSubQuestion.getDisplayTokenOptions(tokenIndex);
        List<String> cleanOptions   = currentSubQuestion.getTokenOptions(tokenIndex);

        if (displayOptions == null || displayOptions.isEmpty()) return;

        for (int i = 0; i < displayOptions.size(); i++) {
            final int optionIndex = i;
            final String cleanOption = (cleanOptions != null && i < cleanOptions.size())
                    ? cleanOptions.get(i)
                    : displayOptions.get(i).trim();

            Button optBtn = new Button(this);
            optBtn.setText(displayOptions.get(i).trim());
            optBtn.setTextColor(Color.WHITE);
            optBtn.setBackgroundColor(Color.parseColor("#2A2A3E"));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 0);
            optBtn.setLayoutParams(params);

            optBtn.setOnClickListener(v -> {
                // Record the clean option at this token index
                recordPick(tokenIndex, cleanOption);

                // Highlight selected button, reset others
                for (int j = 0; j < optionsContainer.getChildCount(); j++) {
                    optionsContainer.getChildAt(j)
                            .setBackgroundColor(Color.parseColor("#2A2A3E"));
                }
                optBtn.setBackgroundColor(Color.parseColor("#4444AA"));
            });

            optionsContainer.addView(optBtn);
        }
    }

    // ── RECORD PICK ───────────────────────────────────────────────────────────

    private void recordPick(int tokenIndex, String cleanOption) {
        // Replace the token at this index with the player's chosen option
        if (tokenIndex < currentTokenPicks.size()) {
            currentTokenPicks.set(tokenIndex, cleanOption);
        }
    }

    // ── SUBMIT ────────────────────────────────────────────────────────────────

    private void onSubmit() {
        if (currentQuestion == null || currentSubQuestion == null) return;

        attempted++;

        // Build the picks map in the format Collector.assemble() expects:
        // subQuestionNumber -> ordered list of picked tokens
        Map<Integer, List<String>> picksMap = new HashMap<>();
        picksMap.put(currentSubQuestion.getQuestionNumber(),
                new ArrayList<>(currentTokenPicks));

        // Assemble: subQuestionNumber -> concatenated answer string
        Map<Integer, String> assembled = collector.assemble(picksMap);

        // Judge: returns true only if ALL sub-questions are correct
        // Since we only show one sub-question at a time, this effectively
        // judges just the one the player answered
        boolean isCorrect = judger.judgeAll(currentQuestion, assembled);

        if (isCorrect) {
            correct++;
            score += 10;
            Toast.makeText(this, "Correct! +10", Toast.LENGTH_SHORT).show();
        } else {
            missed++;
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        // Wait 1 second then load next question
        tokenContainer.postDelayed(this::loadNextQuestion, 1000);
    }

    // ── END SESSION ───────────────────────────────────────────────────────────

    private void endSession() {
        if (pomodoroTimer != null) pomodoroTimer.cancel();

        int accuracy = attempted > 0 ? (correct * 100) / attempted : 0;

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("score",    score);
        intent.putExtra("accuracy", accuracy);
        intent.putExtra("correct",  correct);
        intent.putExtra("missed",   missed);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pomodoroTimer != null) pomodoroTimer.cancel();
    }
}