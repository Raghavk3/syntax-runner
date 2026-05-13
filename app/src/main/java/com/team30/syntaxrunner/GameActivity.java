package com.team30.syntaxrunner;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    // ── Views ─────────────────────────────────────────────────────────────────
    private GridBackgroundView gridView;
    private View groundLine;
    private FrameLayout obstacleLayer;
    private FrameLayout charContainer;
    private ImageView charEmoji;
    private View charGlow;
    private TextView timerDisplay;
    private LinearLayout abortBtn;
    private TextView correctCount;
    private TextView incorrectCount;
    private FrameLayout questionOverlayOuter;
    private TextView questionTypeLabel;
    private TextView questionTimerPill;
    private TextView annotationText;
    private LinearLayout codeTokenRow;
    private TextView optionsHintLabel;
    private View optionsScrollView;
    private LinearLayout optionsContainer;
    private Button submitBtn;

    // ── Game state ────────────────────────────────────────────────────────────
    private int timeLeft;
    private boolean showQuestion = false;
    private int qTimer = 180;
    private int distanceCount = 0;
    private int lastObstacleDist = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private boolean isGameOver = false;
    private int nextObstacleId = 0;
    private final List<ObstacleInfo> obstacles = new ArrayList<>();

    // ── Question state ────────────────────────────────────────────────────────
    private Question currentQuestion;
    private SubQuestion currentSubQuestion;
    private int errorTokenIndex = -1;
    private String correctOption = "";
    private List<String> shuffledOptions = new ArrayList<>();
    private int selectedBugTokenIndex = -1;
    private TextView selectedBugChip = null;
    private String selectedFix = "";
    private TextView selectedFixChip = null;
    private boolean wrongCountedThisQuestion = false;
    private List<String[][]> allQuestions = new ArrayList<>();
    private int questionIndex = 0;

    // ── System ────────────────────────────────────────────────────────────────
    private final Handler gameHandler = new Handler(Looper.getMainLooper());
    private DifficultyManager difficultyManager;
    private ValueAnimator charBobAnim;
    private boolean gameLoopRunning = false;

    // ── Dimensions ────────────────────────────────────────────────────────────
    private int screenWidth, screenHeight;
    private float density;
    private float groundY;
    private int charSizePx;
    private int obstacleSizePx;
    private float obstacleSpeedPx;

    // ── Intents ───────────────────────────────────────────────────────────────
    private int durationMinutes;
    private int runnerDrawable;
    private String difficulty, topic, characterId;
    private String avatarColor;

    // ── Inner: obstacle data ──────────────────────────────────────────────────
    private static class ObstacleInfo {
        int id;
        String type;
        float x;
        View view;

        ObstacleInfo(int id, String type, float x) {
            this.id = id;
            this.type = type;
            this.x = x;
        }
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        durationMinutes = getIntent().getIntExtra("durationMinutes", 5);
        runnerDrawable = getIntent().getIntExtra("runner_drawable", R.drawable.ic_runner_astronaut);
        difficulty = getIntent().getStringExtra("difficulty");
        topic = getIntent().getStringExtra("topic");
        characterId = getIntent().getStringExtra("characterId");
        if (difficulty == null) difficulty = "EASY";
        if (characterId == null) characterId = "astronaut";

        avatarColor = getAvatarColor(characterId);
        timeLeft = durationMinutes * 600;

        density = getResources().getDisplayMetrics().density;
        charSizePx = dp(72);
        obstacleSizePx = dp(36);

        bindViews();
        setupDifficulty();
        setupCharacter();
        setupAbortButton();

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (gridView.getHeight() > 0 && !gameLoopRunning) {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        onLayoutReady();
                    }
                }
            }
        );
    }

    private void bindViews() {
        gridView = findViewById(R.id.gridView);
        groundLine = findViewById(R.id.groundLine);
        obstacleLayer = findViewById(R.id.obstacleLayer);
        charContainer = findViewById(R.id.charContainer);
        charEmoji = findViewById(R.id.charEmoji);
        charGlow = findViewById(R.id.charGlow);
        timerDisplay = findViewById(R.id.timerDisplay);
        abortBtn = findViewById(R.id.abortBtn);
        correctCount = findViewById(R.id.correctCount);
        incorrectCount = findViewById(R.id.incorrectCount);
        questionOverlayOuter = findViewById(R.id.questionOverlayOuter);
        questionTypeLabel = findViewById(R.id.questionTypeLabel);
        questionTimerPill = findViewById(R.id.questionTimerPill);
        annotationText = findViewById(R.id.annotationText);
        codeTokenRow = findViewById(R.id.codeTokenRow);
        optionsHintLabel = findViewById(R.id.optionsHintLabel);
        optionsScrollView = findViewById(R.id.optionsScrollView);
        optionsContainer = findViewById(R.id.optionsContainer);
        submitBtn = findViewById(R.id.submitBtn);
    }

    private void setupDifficulty() {
        Difficulty diffEnum;
        switch (difficulty) {
            case "HARD":   diffEnum = Difficulty.HARD;   break;
            case "MEDIUM": diffEnum = Difficulty.MEDIUM; break;
            default:       diffEnum = Difficulty.EASY;   break;
        }
        difficultyManager = new DifficultyManager(diffEnum);
    }

    private void buildShuffledQuestionList() {
        allQuestions = new ArrayList<>();
        QuestionBank[] banks = {
            new Level1QuestionBank(),
            new Level2QuestionBank(),
            new Level3QuestionBank(),
            new Level4QuestionBank(),
            new Level5QuestionBank(),
            new Level6QuestionBank()
        };
        for (QuestionBank bank : banks) {
            allQuestions.addAll(bank.getAllQuestions());
        }
        Collections.shuffle(allQuestions);
        questionIndex = 0;
    }

    private void setupCharacter() {
        charEmoji.setImageResource(runnerDrawable);

        int c = Color.parseColor(avatarColor);
        GradientDrawable glowDrawable = new GradientDrawable();
        glowDrawable.setShape(GradientDrawable.OVAL);
        glowDrawable.setColor(Color.argb(100, Color.red(c), Color.green(c), Color.blue(c)));
        charGlow.setBackground(glowDrawable);

        gridView.setHorizonColor(avatarColor);
    }

    private void setupAbortButton() {
        abortBtn.setOnClickListener(v -> endGame());
    }

    private void onLayoutReady() {
        screenWidth = gridView.getWidth();
        screenHeight = gridView.getHeight();
        groundY = gridView.getGroundY();
        obstacleSpeedPx = screenWidth * 0.05f;  // 2.5x the original 0.02

        FrameLayout.LayoutParams glp = (FrameLayout.LayoutParams) groundLine.getLayoutParams();
        glp.topMargin = (int) groundY;
        groundLine.setLayoutParams(glp);

        charContainer.setX(screenWidth * 0.20f - charSizePx / 2f);
        charContainer.setY(groundY - charSizePx);

        buildShuffledQuestionList();
        startCharBob();
        startGameLoop();
        updateTimerDisplay();
    }

    // ── Game loop ─────────────────────────────────────────────────────────────

    private final Runnable gameLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isGameOver && gameLoopRunning) {
                gameTick();
                gameHandler.postDelayed(this, 100);
            }
        }
    };

    private void startGameLoop() {
        gameLoopRunning = true;
        gameHandler.postDelayed(gameLoopRunnable, 100);
    }

    private void stopGameLoop() {
        gameLoopRunning = false;
        gameHandler.removeCallbacks(gameLoopRunnable);
    }

    private void gameTick() {
        // Main timer ALWAYS ticks — never pauses when question is showing
        timeLeft = Math.max(0, timeLeft - 1);
        updateTimerDisplay();
        if (timeLeft <= 0) {
            endGame();
            return;
        }

        if (!showQuestion) {
            distanceCount++;
            gridView.setScrollOffset(distanceCount * 3);

            // Tighter gap + higher probability for more active feel
            if (distanceCount - lastObstacleDist > 20 && Math.random() < 0.6) {
                spawnObstacle();
                lastObstacleDist = distanceCount;
            }

            List<ObstacleInfo> toRemove = new ArrayList<>();
            for (ObstacleInfo obs : obstacles) {
                obs.x -= obstacleSpeedPx;
                if (obs.x < -screenWidth * 0.20f) {
                    toRemove.add(obs);
                } else {
                    obs.view.setX(obs.x - obstacleSizePx / 2f);
                }
            }
            for (ObstacleInfo obs : toRemove) {
                obstacleLayer.removeView(obs.view);
                obstacles.remove(obs);
            }

            float trigL = screenWidth * 0.23f;
            float trigR = screenWidth * 0.27f;
            for (ObstacleInfo obs : obstacles) {
                if (obs.x > trigL && obs.x < trigR) {
                    triggerQuestion();
                    break;
                }
            }
        } else {
            // Per-question countdown — always ticks, no guard
            qTimer = Math.max(0, qTimer - 1);
            updateQuestionTimer();
            if (qTimer <= 0) {
                handleTimeout();
            }
        }
    }

    // ── Obstacles ─────────────────────────────────────────────────────────────

    private void spawnObstacle() {
        String type = Math.random() < 0.5 ? "block" : "spike";
        float spawnX = screenWidth * 1.20f;
        ObstacleInfo obs = new ObstacleInfo(nextObstacleId++, type, spawnX);

        obs.view = createObstacleView(type);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(obstacleSizePx, obstacleSizePx);
        obs.view.setX(spawnX - obstacleSizePx / 2f);
        obs.view.setY(groundY - obstacleSizePx);
        obstacleLayer.addView(obs.view, lp);
        obstacles.add(obs);
    }

    private View createObstacleView(String type) {
        return "block".equals(type) ? new BlockObstacleView(this) : new SpikeObstacleView(this);
    }

    private void jumpObstacles() {
        float removeX = -screenWidth * 0.20f - obstacleSizePx;
        List<ObstacleInfo> toRemove = new ArrayList<>();
        for (ObstacleInfo obs : obstacles) {
            if (obs.x < screenWidth * 0.30f) {
                obs.x = removeX;
                toRemove.add(obs);
            }
        }
        for (ObstacleInfo obs : toRemove) {
            obstacleLayer.removeView(obs.view);
            obstacles.remove(obs);
        }
    }

    // ── Questions ─────────────────────────────────────────────────────────────

    private void triggerQuestion() {
        if (showQuestion) return;
        showQuestion = true;
        qTimer = 180;
        selectedBugTokenIndex = -1;
        selectedBugChip = null;
        selectedFix = "";
        selectedFixChip = null;
        wrongCountedThisQuestion = false;

        if (charBobAnim != null) charBobAnim.cancel();
        charContainer.animate().scaleX(0.95f).scaleY(0.95f).setDuration(200).start();

        loadAndShowQuestion();
    }

    private void loadAndShowQuestion() {
        if (questionIndex >= allQuestions.size()) {
            endGame();
            return;
        }
        String[][] raw = allQuestions.get(questionIndex++);
        if (raw == null) {
            resumeRunner();
            return;
        }

        currentQuestion = new Question(raw);
        List<SubQuestion> subs = currentQuestion.getAllSubQuestions();
        if (subs.isEmpty()) {
            resumeRunner();
            return;
        }

        currentSubQuestion = subs.get((int)(Math.random() * subs.size()));
        errorTokenIndex = findErrorTokenIndex(currentSubQuestion);
        if (errorTokenIndex < 0) errorTokenIndex = 0;

        List<String> opts = currentSubQuestion.getTokenOptions(errorTokenIndex);
        if (opts == null || opts.isEmpty()) {
            resumeRunner();
            return;
        }

        correctOption = opts.get(0);

        // Build up to 4 options: 1 correct + up to 3 wrong
        shuffledOptions = new ArrayList<>();
        shuffledOptions.add(correctOption);
        for (int i = 1; i < opts.size(); i++) {
            if (!opts.get(i).isEmpty()) shuffledOptions.add(opts.get(i));
        }
        // Borrow extra wrong options from other token positions if needed
        if (shuffledOptions.size() < 4) {
            List<String> tokens = currentSubQuestion.getTokens();
            for (int ti = 0; ti < tokens.size() && shuffledOptions.size() < 4; ti++) {
                if (ti == errorTokenIndex) continue;
                List<String> adj = currentSubQuestion.getTokenOptions(ti);
                for (int j = 1; j < adj.size() && shuffledOptions.size() < 4; j++) {
                    String wo = adj.get(j).trim();
                    if (!wo.isEmpty() && !shuffledOptions.contains(wo)) shuffledOptions.add(wo);
                }
            }
        }
        Collections.shuffle(shuffledOptions);

        renderQuestionOverlay();
    }

    private int findErrorTokenIndex(SubQuestion sq) {
        List<String> tokens = sq.getTokens();
        for (int i = 0; i < tokens.size(); i++) {
            List<String> opts = sq.getTokenOptions(i);
            if (opts != null && opts.size() > 1 && !tokens.get(i).trim().equals(opts.get(0).trim())) {
                return i;
            }
        }
        for (int i = 0; i < tokens.size(); i++) {
            List<String> opts = sq.getTokenOptions(i);
            if (opts != null && opts.size() > 1) return i;
        }
        return 0;
    }

    // ── Find-the-Bug UI ────────────────────────────────────────────────────────

    private void renderQuestionOverlay() {
        // Annotation: code context line + find-the-bug instruction
        List<String> anns = currentQuestion.getAnnotationLines();
        String context = "";
        for (String a : anns) {
            if (a.startsWith("//")) { context = a; break; }
        }
        annotationText.setText(context.isEmpty() ? "→ find 1 error" : context + "\n→ find 1 error");

        // Header type label — neutral state until bug token is tapped
        questionTypeLabel.setText("● BUG_SCAN");
        questionTypeLabel.setTextColor(Color.parseColor("#FF8B949E"));

        // Code block: ALL tokens rendered as tappable chips
        codeTokenRow.removeAllViews();
        List<String> tokens = currentSubQuestion.getTokens();
        for (int i = 0; i < tokens.size(); i++) {
            final int idx = i;
            TextView tv = new TextView(this);
            tv.setText(tokens.get(i));
            tv.setTypeface(Typeface.MONOSPACE);
            tv.setTextSize(13f);
            tv.setTextColor(Color.parseColor("#FFC9D1D9"));
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(dp(8), dp(6), dp(8), dp(6));
            tv.setBackground(makeTokenDefault());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(dp(2), dp(2), dp(2), dp(2));
            tv.setLayoutParams(lp);
            tv.setOnClickListener(v -> handleBugTokenTap(idx, tv));
            codeTokenRow.addView(tv);
        }

        // Fix options area hidden until a bug token is selected
        optionsHintLabel.setVisibility(View.GONE);
        optionsScrollView.setVisibility(View.GONE);
        optionsContainer.removeAllViews();

        submitBtn.setVisibility(View.GONE);
        submitBtn.setEnabled(true);
        submitBtn.setOnClickListener(v -> evaluateAndSubmit());

        questionOverlayOuter.setAlpha(0f);
        questionOverlayOuter.setVisibility(View.VISIBLE);
        questionOverlayOuter.animate()
                .alpha(1f)
                .setDuration(250)
                .setInterpolator(new OvershootInterpolator(1.2f))
                .start();

        updateQuestionTimer();
    }

    private void handleBugTokenTap(int tokenIdx, TextView chip) {
        // Deselect previously highlighted bug token
        if (selectedBugChip != null && selectedBugChip != chip) {
            selectedBugChip.setBackground(makeTokenDefault());
            selectedBugChip.setTextColor(Color.parseColor("#FFC9D1D9"));
        }

        selectedBugTokenIndex = tokenIdx;
        selectedBugChip = chip;

        // Highlight tapped token in red
        chip.setBackground(makeRoundRect(dp(4), Color.parseColor("#33FF4444"), Color.parseColor("#FFFF4444"), dp(1)));
        chip.setTextColor(Color.WHITE);

        // Reset any fix selection made previously
        selectedFix = "";
        if (selectedFixChip != null) {
            selectedFixChip.setBackground(makeChipDefault());
            selectedFixChip.setTextColor(Color.parseColor("#FFC9D1D9"));
            selectedFixChip = null;
        }
        submitBtn.setVisibility(View.GONE);

        showFixOptions();
    }

    private void showFixOptions() {
        // Header flips to BUG_DETECTED in red
        questionTypeLabel.setText("● BUG_DETECTED");
        questionTypeLabel.setTextColor(Color.parseColor("#FFFF4444"));

        optionsHintLabel.setVisibility(View.VISIBLE);

        // Rebuild fix option chips
        optionsContainer.removeAllViews();
        for (String opt : shuffledOptions) {
            TextView chip = new TextView(this);
            chip.setText(opt);
            chip.setTypeface(Typeface.MONOSPACE);
            chip.setTextSize(13f);
            chip.setTextColor(Color.parseColor("#FFC9D1D9"));
            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(14), dp(9), dp(14), dp(9));
            chip.setBackground(makeChipDefault());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, dp(8), 0);
            chip.setLayoutParams(lp);
            chip.setOnClickListener(v -> handleFixTap(opt, chip));
            optionsContainer.addView(chip);
        }

        optionsScrollView.setVisibility(View.VISIBLE);
    }

    private void handleFixTap(String fix, TextView chip) {
        if (!chip.isEnabled()) return;

        // Deselect previous fix chip
        if (selectedFixChip != null && selectedFixChip != chip) {
            selectedFixChip.setBackground(makeChipDefault());
            selectedFixChip.setTextColor(Color.parseColor("#FFC9D1D9"));
        }

        selectedFix = fix;
        selectedFixChip = chip;

        // Highlight selected fix chip purple
        chip.setBackground(makeRoundRect(dp(8), Color.parseColor("#336C63FF"), Color.parseColor("#FF6C63FF"), dp(2)));
        chip.setTextColor(Color.WHITE);

        submitBtn.setVisibility(View.VISIBLE);
    }

    private void evaluateAndSubmit() {
        if (selectedFix.isEmpty() || selectedBugTokenIndex < 0) return;

        boolean bugCorrect = (selectedBugTokenIndex == errorTokenIndex);
        boolean fixCorrect = correctOption.trim().equals(selectedFix.trim());
        boolean isCorrect = bugCorrect && fixCorrect;

        submitBtn.setEnabled(false);

        if (isCorrect) {
            correctAnswers++;
            correctCount.setText(String.valueOf(correctAnswers));

            // Turn bug token green
            if (selectedBugChip != null) {
                selectedBugChip.setBackground(makeRoundRect(dp(4), Color.parseColor("#1A44FF88"), Color.parseColor("#FF44FF88"), dp(1)));
                selectedBugChip.setTextColor(Color.parseColor("#FF44FF88"));
            }
            // Turn fix chip green
            if (selectedFixChip != null) {
                selectedFixChip.setBackground(makeRoundRect(dp(8), Color.parseColor("#1A3FB950"), Color.parseColor("#FF3FB950"), dp(2)));
                selectedFixChip.setTextColor(Color.parseColor("#FF3FB950"));
            }
            disableAllChips();
            disableCodeTokens();

            gameHandler.postDelayed(() -> {
                if (!isGameOver && !isFinishing()) {
                    dismissQuestion();
                    jumpObstacles();
                    resumeRunner();
                }
            }, 700);

        } else {
            if (!wrongCountedThisQuestion) {
                wrongCountedThisQuestion = true;
                incorrectAnswers++;
                incorrectCount.setText(String.valueOf(incorrectAnswers));
            }

            // Flash fix chip red to signal wrong answer
            if (selectedFixChip != null) {
                selectedFixChip.setBackground(makeRoundRect(dp(8), Color.parseColor("#1AFF7B72"), Color.parseColor("#FFFF7B72"), dp(2)));
                selectedFixChip.setTextColor(Color.parseColor("#FFFF7B72"));
            }

            gameHandler.postDelayed(this::resetForRetry, 700);
        }
    }

    private void resetForRetry() {
        selectedFix = "";
        selectedFixChip = null;
        submitBtn.setVisibility(View.GONE);
        submitBtn.setEnabled(true);

        // Reset all fix option chips to default (bug token stays highlighted)
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View v = optionsContainer.getChildAt(i);
            if (v instanceof TextView) {
                v.setEnabled(true);
                v.setClickable(true);
                ((TextView) v).setBackground(makeChipDefault());
                ((TextView) v).setTextColor(Color.parseColor("#FFC9D1D9"));
            }
        }
    }

    private void handleTimeout() {
        if (!wrongCountedThisQuestion) {
            wrongCountedThisQuestion = true;
            incorrectAnswers++;
            incorrectCount.setText(String.valueOf(incorrectAnswers));
        }

        // Reveal correct bug token in code row
        for (int i = 0; i < codeTokenRow.getChildCount(); i++) {
            View v = codeTokenRow.getChildAt(i);
            v.setEnabled(false);
            v.setClickable(false);
            if (v instanceof TextView && i == errorTokenIndex) {
                ((TextView) v).setBackground(makeRoundRect(dp(4), Color.parseColor("#1A3FB950"), Color.parseColor("#803FB950"), dp(1)));
                ((TextView) v).setTextColor(Color.parseColor("#803FB950"));
            }
        }

        // Reveal correct fix option if the options panel is already visible
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View v = optionsContainer.getChildAt(i);
            if (v instanceof TextView) {
                TextView chip = (TextView) v;
                if (chip.getText().toString().trim().equals(correctOption.trim())) {
                    chip.setBackground(makeRoundRect(dp(8), Color.parseColor("#1A3FB950"), Color.parseColor("#803FB950"), dp(2)));
                    chip.setTextColor(Color.parseColor("#803FB950"));
                }
                chip.setEnabled(false);
                chip.setClickable(false);
            }
        }
        submitBtn.setVisibility(View.GONE);

        gameHandler.postDelayed(() -> {
            if (!isGameOver && !isFinishing()) {
                dismissQuestion();
                jumpObstacles();
                resumeRunner();
            }
        }, 900);
    }

    private void disableAllChips() {
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View v = optionsContainer.getChildAt(i);
            v.setEnabled(false);
            v.setClickable(false);
        }
    }

    private void disableCodeTokens() {
        for (int i = 0; i < codeTokenRow.getChildCount(); i++) {
            View v = codeTokenRow.getChildAt(i);
            v.setEnabled(false);
            v.setClickable(false);
        }
    }

    private void dismissQuestion() {
        questionOverlayOuter.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> {
                    questionOverlayOuter.setVisibility(View.GONE);
                    questionOverlayOuter.setAlpha(1f);
                })
                .start();
    }

    private void resumeRunner() {
        showQuestion = false;
        selectedBugTokenIndex = -1;
        selectedBugChip = null;
        selectedFix = "";
        selectedFixChip = null;
        qTimer = 180;
        groundY = gridView.getGroundY();
        charContainer.setY(groundY - charSizePx);
        charContainer.animate().scaleX(1f).scaleY(1f).setDuration(200)
                .withEndAction(this::startCharBob)
                .start();
    }

    // ── Timer display ─────────────────────────────────────────────────────────

    private void updateTimerDisplay() {
        int totalSecs = (int) Math.ceil(timeLeft / 10.0);
        int mins = totalSecs / 60;
        int secs = totalSecs % 60;
        timerDisplay.setText(String.format("%02d:%02d", mins, secs));
    }

    private void updateQuestionTimer() {
        int secs = qTimer / 10;
        questionTimerPill.setText(String.format("00:%02d", secs));

        if (qTimer <= 50) {
            questionTimerPill.setBackground(getDrawable(R.drawable.bg_qtimer_pill_red));
            questionTimerPill.setTextColor(Color.parseColor("#FFFF7B72"));
        } else {
            questionTimerPill.setBackground(getDrawable(R.drawable.bg_qtimer_pill));
            questionTimerPill.setTextColor(Color.parseColor("#FF8B949E"));
        }
    }

    // ── Character animation ───────────────────────────────────────────────────

    private void startCharBob() {
        if (isGameOver || showQuestion) return;
        if (charBobAnim != null) charBobAnim.cancel();

        float baseY = groundY - charSizePx;
        float bobPx = dp(14);
        charBobAnim = ValueAnimator.ofFloat(0f, -bobPx, 0f);
        charBobAnim.setDuration(380);
        charBobAnim.setRepeatCount(ValueAnimator.INFINITE);
        charBobAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        charBobAnim.addUpdateListener(anim -> {
            if (!isGameOver) {
                charContainer.setY(baseY + (Float) anim.getAnimatedValue());
            }
        });
        charBobAnim.start();
    }

    // ── End game ──────────────────────────────────────────────────────────────

    private void endGame() {
        if (isGameOver) return;
        isGameOver = true;
        stopGameLoop();
        if (charBobAnim != null) charBobAnim.cancel();

        int total = correctAnswers + incorrectAnswers;
        int accuracy = total > 0 ? (correctAnswers * 100) / total : 0;
        int score = correctAnswers * 10;

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("accuracy", accuracy);
        intent.putExtra("correct", correctAnswers);
        intent.putExtra("missed", incorrectAnswers);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    protected void onPause() {
        super.onPause();
        stopGameLoop();
        if (charBobAnim != null) charBobAnim.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isGameOver && screenWidth > 0) {
            startGameLoop();
            if (charBobAnim != null && !showQuestion) charBobAnim.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopGameLoop();
        if (charBobAnim != null) charBobAnim.cancel();
    }

    // ── Drawable helpers ──────────────────────────────────────────────────────

    private GradientDrawable makeRoundRect(float cornerRadius, int fillColor, int strokeColor, int strokeWidth) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.RECTANGLE);
        d.setCornerRadius(cornerRadius);
        d.setColor(fillColor);
        d.setStroke(strokeWidth, strokeColor);
        return d;
    }

    private GradientDrawable makeChipDefault() {
        return makeRoundRect(dp(8), Color.parseColor("#FF161B22"), Color.parseColor("#FF2D333B"), dp(1));
    }

    // Code token default: dark pill matching the code block background
    private GradientDrawable makeTokenDefault() {
        return makeRoundRect(dp(4), Color.parseColor("#FF2A2A3E"), Color.parseColor("#FF3A3A5A"), dp(1));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private int dp(int dp) {
        return Math.round(dp * density);
    }

    private String getAvatarColor(String id) {
        if (id == null) return "#6C63FF";
        switch (id) {
            case "astronaut": return "#4DA3FF";
            case "robot":     return "#6C63FF";
            case "hacker":    return "#3FB950";
            case "alien":     return "#57F287";
            case "ninja":     return "#8B949E";
            case "wizard":    return "#FF7B72";
            case "cyborg":    return "#4DA3FF";
            case "samurai":   return "#FF4040";
            case "mecha":     return "#6C63FF";
            case "zombie":    return "#3FB950";
            default:          return "#6C63FF";
        }
    }
}
