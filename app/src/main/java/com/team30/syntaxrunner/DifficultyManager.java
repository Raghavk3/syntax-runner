package com.team30.syntaxrunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Picks and instantiates the right QuestionBanks based on the player's
 * chosen Difficulty, then exposes a single entry point for fetching a
 * random raw question.
 *
 * <p>Mapping:
 * <ul>
 *   <li>{@link Difficulty#EASY}   → Level1QuestionBank</li>
 *   <li>{@link Difficulty#MEDIUM} → Level2/3/4 QuestionBanks</li>
 *   <li>{@link Difficulty#HARD}   → Level5/6 QuestionBanks</li>
 * </ul>
 */
public class DifficultyManager {

    private final Difficulty difficulty;
    private final QuestionBankInterface aggregatedBank;

    /**
     * Builds a DifficultyManager for the given difficulty.
     * All required level banks are instantiated immediately and wrapped
     * inside an {@link AggregatedQuestionBank}.
     *
     * @param difficulty the chosen difficulty
     */
    public DifficultyManager(Difficulty difficulty)
    {
        this.difficulty = difficulty;
        List<QuestionBankInterface> banks = new ArrayList<>();
        for (int level : difficulty.getLevels())
        {
            banks.add(createBankForLevel(level));
        }
        this.aggregatedBank = new AggregatedQuestionBank(banks);
    }

    /**
     * Factory that maps a level number to its concrete QuestionBank.
     * Centralising this here means new levels only need to be added
     * in one place.
     */
    private QuestionBankInterface createBankForLevel(int level)
    {
        switch (level)
        {
            case 1: return new Level1QuestionBank();
            case 2: return new Level2QuestionBank();
            case 3: return new Level3QuestionBank();
            case 4: return new Level4QuestionBank();
            case 5: return new Level5QuestionBank();
            case 6: return new Level6QuestionBank();
            default:
                throw new IllegalArgumentException("Unknown level: " + level);
        }
    }

    /** Returns the difficulty this manager was created with. */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * Picks a random raw question from the pool defined by the current
     * difficulty. Caller wraps the result with {@code new Question(...)}.
     *
     * @return a raw question {@code String[][]}
     */
    public String[][] pickRandomQuestion()
    {
        return aggregatedBank.pickRandomQuestion();
    }
}
