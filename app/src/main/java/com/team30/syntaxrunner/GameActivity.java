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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
    private FlexboxLayout codeTokenRow;
    private TextView optionsHintLabel;
    private GridLayout optionsContainer;
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
    private List<String> currentMergedTokens = null;
    private boolean isTwoErrorQuestion = false;
    private int errorToken1Index = -1;
    private int errorToken2Index = -1;
    private String correctOption1 = "";
    private String correctOption2 = "";
    private List<String> shuffledOptions1 = new ArrayList<>();
    private List<String> shuffledOptions2 = new ArrayList<>();
    private int bugFixStep = 0;          // 0=selecting tokens  1=fixing bug1  2=fixing bug2
    private int selectedBugToken1Index = -1;
    private int selectedBugToken2Index = -1;
    private TextView selectedBugChip1 = null;
    private TextView selectedBugChip2 = null;
    private String selectedFix = "";
    private TextView selectedFixChip = null;
    private boolean wrongCountedThisQuestion = false;
    private String currentContext = "";
    private List<QuestionSlot> allQuestions = new ArrayList<>();
    private int questionIndex = 0;

    // ── System ────────────────────────────────────────────────────────────────
    private final Handler gameHandler = new Handler(Looper.getMainLooper());
    private ValueAnimator charBobAnim;
    private boolean gameLoopRunning = false;

    // ── Dimensions ────────────────────────────────────────────────────────────
    private int screenWidth;
    private float density;
    private float groundY;
    private int charSizePx;
    private int obstacleSizePx;
    private float obstacleSpeedPx;

    // ── Intents ───────────────────────────────────────────────────────────────
    private int durationMinutes;
    private int runnerDrawable;
    private String difficulty, characterId;
    private String avatarColor;

    // ── Constants ─────────────────────────────────────────────────────────────
    private static final int FIRST_OBSTACLE_ENCOUNTER_MS = 2500;

    // ── Inner: question slot ──────────────────────────────────────────────────
    private static class QuestionSlot {
        final Question question;
        final boolean isTwoError;
        final SubQuestion sq1, sq2;
        final int err1, err2;
        final SubQuestion singleVariant;

        static QuestionSlot twoError(Question q, SubQuestion s1, SubQuestion s2, int e1, int e2) {
            return new QuestionSlot(q, true, s1, s2, e1, e2, null);
        }

        static QuestionSlot oneError(Question q, SubQuestion sv) {
            return new QuestionSlot(q, false, null, null, -1, -1, sv);
        }

        private QuestionSlot(Question q, boolean two, SubQuestion s1, SubQuestion s2,
                             int e1, int e2, SubQuestion sv) {
            question = q; isTwoError = two; sq1 = s1; sq2 = s2;
            err1 = e1; err2 = e2; singleVariant = sv;
        }
    }

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
        String topic = getIntent().getStringExtra("topic");
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
        DifficultyManager difficultyManager = new DifficultyManager(diffEnum);
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
        Random rng = new Random();
        for (QuestionBank bank : banks) {
            for (String[][] raw : bank.getAllQuestions()) {
                if (raw == null) continue;
                Question q = new Question(raw);
                SubQuestion sq1 = q.getSubQuestion(1);
                SubQuestion sq2 = q.getSubQuestion(2);
                if (sq1 == null && sq2 == null) continue;

                if (sq1 == null) { allQuestions.add(QuestionSlot.oneError(q, sq2)); continue; }
                if (sq2 == null) { allQuestions.add(QuestionSlot.oneError(q, sq1)); continue; }

                int e1 = findErrorTokenIndex(sq1);
                int e2 = findErrorTokenIndex(sq2);
                boolean realErr1 = isRealError(sq1, e1);
                boolean realErr2 = isRealError(sq2, e2);
                boolean twoError = realErr1 && realErr2 && (e1 != e2);

                if (twoError) {
                    allQuestions.add(QuestionSlot.twoError(q, sq1, sq2, e1, e2));
                } else {
                    SubQuestion sv = (!realErr1 && realErr2) ? sq2
                                   : (realErr1 && !realErr2) ? sq1
                                   : (rng.nextBoolean() ? sq1 : sq2);
                    allQuestions.add(QuestionSlot.oneError(q, sv));
                }
            }
        }
        Collections.shuffle(allQuestions);
        questionIndex = 0;
    }

    private boolean isRealError(SubQuestion sq, int idx) {
        if (idx < 0 || idx >= sq.getTokens().size()) return false;
        List<String> opts = sq.getTokenOptions(idx);
        return opts != null && !opts.isEmpty()
            && !sq.getTokens().get(idx).trim().equals(opts.get(0).trim());
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
        int screenHeight = gridView.getHeight();
        groundY = gridView.getGroundY();
        obstacleSpeedPx = screenWidth * 0.05f;

        int travelTicks = Math.round((screenWidth * 0.95f) / obstacleSpeedPx);
        int firstSpawnTick = Math.max(0, FIRST_OBSTACLE_ENCOUNTER_MS / 100 - travelTicks);
        lastObstacleDist = firstSpawnTick - 21;

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
        timeLeft = Math.max(0, timeLeft - 1);
        updateTimerDisplay();
        if (timeLeft <= 0) {
            endGame();
            return;
        }

        if (!showQuestion) {
            distanceCount++;
            gridView.setScrollOffset(distanceCount * 3);

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

        QuestionSlot slot = allQuestions.get(questionIndex++);
        currentQuestion = slot.question;
        isTwoErrorQuestion = slot.isTwoError;

        if (slot.isTwoError) {
            currentSubQuestion = slot.sq1;
            errorToken1Index = slot.err1;
            errorToken2Index = slot.err2;

            List<String> merged = new ArrayList<>(slot.sq1.getTokens());
            merged.set(slot.err2, slot.sq2.getTokens().get(slot.err2));
            currentMergedTokens = merged;

            correctOption1 = slot.sq1.getTokenOptions(slot.err1).get(0);
            correctOption2 = slot.sq2.getTokenOptions(slot.err2).get(0);
            shuffledOptions1 = build4Options(slot.sq1, slot.err1);
            shuffledOptions2 = build4Options(slot.sq2, slot.err2);
        } else {
            currentSubQuestion = slot.singleVariant;
            errorToken1Index = findErrorTokenIndex(currentSubQuestion);
            if (errorToken1Index < 0) errorToken1Index = 0;
            errorToken2Index = -1;

            List<String> opts = currentSubQuestion.getTokenOptions(errorToken1Index);
            if (opts == null || opts.isEmpty()) {
                resumeRunner();
                return;
            }
            correctOption1 = opts.get(0);
            shuffledOptions1 = build4Options(currentSubQuestion, errorToken1Index);
            currentMergedTokens = null;
        }

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

    private List<String> build4Options(SubQuestion sq, int errIdx) {
        List<String> opts = sq.getTokenOptions(errIdx);
        List<String> result = new ArrayList<>();
        result.add(opts.get(0));
        for (int i = 1; i < opts.size(); i++) {
            if (!opts.get(i).isEmpty()) result.add(opts.get(i));
        }
        if (result.size() < 4) {
            List<String> tokens = sq.getTokens();
            for (int ti = 0; ti < tokens.size() && result.size() < 4; ti++) {
                if (ti == errIdx) continue;
                List<String> adj = sq.getTokenOptions(ti);
                if (adj == null) continue;
                for (int j = 1; j < adj.size() && result.size() < 4; j++) {
                    String wo = adj.get(j).trim();
                    if (!wo.isEmpty() && !result.contains(wo)) result.add(wo);
                }
            }
        }
        Collections.shuffle(result);
        return result;
    }

    // ── Find-the-Bug UI ────────────────────────────────────────────────────────

    private void renderQuestionOverlay() {
        List<String> anns = currentQuestion.getAnnotationLines();
        currentContext = "";
        for (String a : anns) {
            if (a.startsWith("//")) { currentContext = a; break; }
        }
        String errorHint = isTwoErrorQuestion ? "→ find 2 errors" : "→ find 1 error";
        annotationText.setText(currentContext.isEmpty() ? errorHint : currentContext + "\n" + errorHint);

        questionTypeLabel.setText("● BUG_SCAN");
        questionTypeLabel.setTextColor(Color.parseColor("#FF8B949E"));

        List<String> displayTokens = (isTwoErrorQuestion && currentMergedTokens != null)
            ? currentMergedTokens : currentSubQuestion.getTokens();

        codeTokenRow.removeAllViews();
        for (int i = 0; i < displayTokens.size(); i++) {
            final int idx = i;
            TextView tv = new TextView(this);
            tv.setText(displayTokens.get(i));
            tv.setTypeface(Typeface.MONOSPACE);
            tv.setTextSize(11f);
            tv.setTextColor(Color.parseColor("#FFC9D1D9"));
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(dp(7), dp(5), dp(7), dp(5));
            tv.setBackground(makeTokenDefault());
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(dp(2), dp(2), dp(2), dp(2));
            tv.setLayoutParams(lp);
            tv.setOnClickListener(v -> handleBugTokenTap(idx, tv));
            codeTokenRow.addView(tv);
        }

        optionsHintLabel.setVisibility(View.GONE);
        optionsContainer.setVisibility(View.GONE);
        optionsContainer.removeAllViews();
        submitBtn.setVisibility(View.GONE);
        submitBtn.setEnabled(true);
        submitBtn.setOnClickListener(v -> evaluateAndSubmit());

        bugFixStep = 0;
        selectedBugToken1Index = -1; selectedBugChip1 = null;
        selectedBugToken2Index = -1; selectedBugChip2 = null;
        selectedFix = ""; selectedFixChip = null;

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
        if (bugFixStep > 0) return;

        if (!isTwoErrorQuestion) {
            if (selectedBugChip1 != null && selectedBugChip1 != chip) {
                selectedBugChip1.setBackground(makeTokenDefault());
                selectedBugChip1.setTextColor(Color.parseColor("#FFC9D1D9"));
            }
            selectedBugToken1Index = tokenIdx;
            selectedBugChip1 = chip;
            chip.setBackground(makeRoundRect(dp(4), Color.parseColor("#33FF4444"), Color.parseColor("#FFFF4444"), dp(1)));
            chip.setTextColor(Color.WHITE);
            selectedFix = "";
            if (selectedFixChip != null) {
                selectedFixChip.setBackground(makeChipDefault());
                selectedFixChip.setTextColor(Color.parseColor("#FFC9D1D9"));
                selectedFixChip = null;
            }
            submitBtn.setVisibility(View.GONE);
            showFixOptions(shuffledOptions1);
        } else {
            if (selectedBugChip1 == null) {
                selectedBugToken1Index = tokenIdx;
                selectedBugChip1 = chip;
                chip.setBackground(makeRoundRect(dp(4), Color.parseColor("#33FF4444"), Color.parseColor("#FFFF4444"), dp(1)));
                chip.setTextColor(Color.WHITE);
                String hint = currentContext.isEmpty() ? "→ tap 1 more error" : currentContext + "\n→ tap 1 more error";
                annotationText.setText(hint);
            } else if (chip == selectedBugChip1) {
                chip.setBackground(makeTokenDefault());
                chip.setTextColor(Color.parseColor("#FFC9D1D9"));
                selectedBugToken1Index = -1;
                selectedBugChip1 = null;
                String hint = currentContext.isEmpty() ? "→ find 2 errors" : currentContext + "\n→ find 2 errors";
                annotationText.setText(hint);
            } else if (selectedBugChip2 == null) {
                selectedBugToken2Index = tokenIdx;
                selectedBugChip2 = chip;
                chip.setBackground(makeRoundRect(dp(4), Color.parseColor("#33FF4444"), Color.parseColor("#FFFF4444"), dp(1)));
                chip.setTextColor(Color.WHITE);
                String hint = currentContext.isEmpty() ? "fix error 1 of 2" : currentContext + "\nfix error 1 of 2";
                annotationText.setText(hint);
                showFixOptions(shuffledOptions1);
            }
        }
    }

    private void showFixOptions(List<String> options) {
        questionTypeLabel.setText("● BUG_DETECTED");
        questionTypeLabel.setTextColor(Color.parseColor("#FFFF4444"));
        optionsHintLabel.setVisibility(View.VISIBLE);
        optionsContainer.removeAllViews();

        for (int k = 0; k < options.size(); k++) {
            final String opt = options.get(k);
            TextView chip = new TextView(this);
            chip.setText(opt);
            chip.setTypeface(Typeface.MONOSPACE);
            chip.setTextSize(12f);
            chip.setTextColor(Color.parseColor("#FFC9D1D9"));
            chip.setGravity(Gravity.CENTER);
            chip.setPadding(dp(10), dp(8), dp(10), dp(8));
            chip.setBackground(makeChipDefault());

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(
                    GridLayout.spec(k / 2, 1f),
                    GridLayout.spec(k % 2, 1f));
            lp.width = 0;
            lp.height = GridLayout.LayoutParams.WRAP_CONTENT;
            lp.setMargins(dp(4), dp(4), dp(4), dp(4));
            chip.setLayoutParams(lp);
            chip.setOnClickListener(v -> handleFixTap(opt, chip));
            optionsContainer.addView(chip);
        }

        optionsContainer.setVisibility(View.VISIBLE);
        bugFixStep = 1;
    }

    private void handleFixTap(String fix, TextView chip) {
        if (!chip.isEnabled()) return;

        if (selectedFixChip != null && selectedFixChip != chip) {
            selectedFixChip.setBackground(makeChipDefault());
            selectedFixChip.setTextColor(Color.parseColor("#FFC9D1D9"));
        }

        selectedFix = fix;
        selectedFixChip = chip;

        chip.setBackground(makeRoundRect(dp(8), Color.parseColor("#336C63FF"), Color.parseColor("#FF6C63FF"), dp(2)));
        chip.setTextColor(Color.WHITE);

        submitBtn.setVisibility(View.VISIBLE);
    }

    private void evaluateAndSubmit() {
        if (selectedFix.isEmpty()) return;
        submitBtn.setEnabled(false);

        if (bugFixStep == 1) {
            boolean bugCorrect = (selectedBugToken1Index == errorToken1Index
                    || selectedBugToken2Index == errorToken1Index);
            boolean fixCorrect = correctOption1.trim().equals(selectedFix.trim());

            if (bugCorrect && fixCorrect) {
                TextView bug1Chip = (selectedBugToken1Index == errorToken1Index)
                        ? selectedBugChip1 : selectedBugChip2;
                if (bug1Chip != null) {
                    bug1Chip.setBackground(makeRoundRect(dp(4), Color.parseColor("#1A44FF88"), Color.parseColor("#FF44FF88"), dp(1)));
                    bug1Chip.setTextColor(Color.parseColor("#FF44FF88"));
                }
                if (selectedFixChip != null) {
                    selectedFixChip.setBackground(makeRoundRect(dp(8), Color.parseColor("#1A3FB950"), Color.parseColor("#FF3FB950"), dp(2)));
                    selectedFixChip.setTextColor(Color.parseColor("#FF3FB950"));
                }

                if (!isTwoErrorQuestion) {
                    correctAnswers++;
                    correctCount.setText(String.valueOf(correctAnswers));
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
                    gameHandler.postDelayed(() -> {
                        selectedFix = "";
                        selectedFixChip = null;
                        submitBtn.setVisibility(View.GONE);
                        submitBtn.setEnabled(true);
                        optionsContainer.removeAllViews();
                        optionsContainer.setVisibility(View.GONE);
                        optionsHintLabel.setVisibility(View.GONE);
                        showFixOptions(shuffledOptions2);
                        bugFixStep = 2;
                        String hint = currentContext.isEmpty() ? "fix error 2 of 2" : currentContext + "\nfix error 2 of 2";
                        annotationText.setText(hint);
                    }, 400);
                }
            } else {
                if (!wrongCountedThisQuestion) {
                    wrongCountedThisQuestion = true;
                    incorrectAnswers++;
                    incorrectCount.setText(String.valueOf(incorrectAnswers));
                }
                if (selectedFixChip != null) {
                    selectedFixChip.setBackground(makeRoundRect(dp(8), Color.parseColor("#1AFF7B72"), Color.parseColor("#FFFF7B72"), dp(2)));
                    selectedFixChip.setTextColor(Color.parseColor("#FFFF7B72"));
                }
                if (isTwoErrorQuestion) {
                    gameHandler.postDelayed(this::resetBothTokens, 700);
                } else {
                    gameHandler.postDelayed(this::resetForRetry, 700);
                }
            }

        } else if (bugFixStep == 2) {
            int bug2TappedIdx = (selectedBugToken1Index == errorToken1Index)
                    ? selectedBugToken2Index : selectedBugToken1Index;
            TextView bug2Chip = (selectedBugToken1Index == errorToken1Index)
                    ? selectedBugChip2 : selectedBugChip1;
            boolean bugCorrect = (bug2TappedIdx == errorToken2Index);
            boolean fixCorrect = correctOption2.trim().equals(selectedFix.trim());

            if (bugCorrect && fixCorrect) {
                correctAnswers++;
                correctCount.setText(String.valueOf(correctAnswers));
                if (bug2Chip != null) {
                    bug2Chip.setBackground(makeRoundRect(dp(4), Color.parseColor("#1A44FF88"), Color.parseColor("#FF44FF88"), dp(1)));
                    bug2Chip.setTextColor(Color.parseColor("#FF44FF88"));
                }
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
                if (selectedFixChip != null) {
                    selectedFixChip.setBackground(makeRoundRect(dp(8), Color.parseColor("#1AFF7B72"), Color.parseColor("#FFFF7B72"), dp(2)));
                    selectedFixChip.setTextColor(Color.parseColor("#FFFF7B72"));
                }
                gameHandler.postDelayed(this::resetBothTokens, 700);
            }
        }
    }

    private void resetBothTokens() {
        if (selectedBugChip1 != null) {
            selectedBugChip1.setBackground(makeTokenDefault());
            selectedBugChip1.setTextColor(Color.parseColor("#FFC9D1D9"));
        }
        if (selectedBugChip2 != null) {
            selectedBugChip2.setBackground(makeTokenDefault());
            selectedBugChip2.setTextColor(Color.parseColor("#FFC9D1D9"));
        }
        selectedBugToken1Index = -1; selectedBugChip1 = null;
        selectedBugToken2Index = -1; selectedBugChip2 = null;
        selectedFix = ""; selectedFixChip = null;
        bugFixStep = 0;
        submitBtn.setVisibility(View.GONE);
        submitBtn.setEnabled(true);
        optionsHintLabel.setVisibility(View.GONE);
        optionsContainer.setVisibility(View.GONE);
        optionsContainer.removeAllViews();
        questionTypeLabel.setText("● BUG_SCAN");
        questionTypeLabel.setTextColor(Color.parseColor("#FF8B949E"));
        String hint = isTwoErrorQuestion ? "→ find 2 errors" : "→ find 1 error";
        annotationText.setText(currentContext.isEmpty() ? hint : currentContext + "\n" + hint);
        for (int i = 0; i < codeTokenRow.getChildCount(); i++) {
            View v = codeTokenRow.getChildAt(i);
            v.setEnabled(true);
            v.setClickable(true);
        }
    }

    private void resetForRetry() {
        selectedFix = "";
        selectedFixChip = null;
        submitBtn.setVisibility(View.GONE);
        submitBtn.setEnabled(true);

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

        for (int i = 0; i < codeTokenRow.getChildCount(); i++) {
            View v = codeTokenRow.getChildAt(i);
            v.setEnabled(false);
            v.setClickable(false);
            if (v instanceof TextView) {
                if (i == errorToken1Index || (isTwoErrorQuestion && i == errorToken2Index)) {
                    ((TextView) v).setBackground(makeRoundRect(dp(4), Color.parseColor("#1A3FB950"), Color.parseColor("#803FB950"), dp(1)));
                    ((TextView) v).setTextColor(Color.parseColor("#803FB950"));
                }
            }
        }

        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            View v = optionsContainer.getChildAt(i);
            if (v instanceof TextView) {
                TextView chip = (TextView) v;
                String correctToReveal = (bugFixStep == 2) ? correctOption2 : correctOption1;
                if (chip.getText().toString().trim().equals(correctToReveal.trim())) {
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
        isTwoErrorQuestion = false;
        errorToken1Index = -1; errorToken2Index = -1;
        bugFixStep = 0;
        selectedBugToken1Index = -1; selectedBugChip1 = null;
        selectedBugToken2Index = -1; selectedBugChip2 = null;
        selectedFix = ""; selectedFixChip = null;
        currentMergedTokens = null;
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
        timerDisplay.setText(String.format(Locale.getDefault(), "%02d:%02d", mins, secs));
    }

    private void updateQuestionTimer() {
        int secs = qTimer / 10;
        questionTimerPill.setText(String.format(Locale.getDefault(), "00:%02d", secs));

        if (qTimer <= 50) {
            questionTimerPill.setBackground(AppCompatResources.getDrawable(this, R.drawable.bg_qtimer_pill_red));
            questionTimerPill.setTextColor(Color.parseColor("#FFFF7B72"));
        } else {
            questionTimerPill.setBackground(AppCompatResources.getDrawable(this, R.drawable.bg_qtimer_pill));
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
