package com.team30.syntaxrunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores one parsed sub-question from a raw Syntax Runner question set.
 * <p>
 * A sub-question contains:
 * <ul>
 *   <li>The tokens of {@code [Question-x]}.</li>
 *   <li>The options for each token position from {@code [Options-x-y]}.</li>
 *   <li>The accepted answers from {@code [Answer-x]}.</li>
 * </ul>
 *
 * <h3>Clean view vs display view</h3>
 * Both tokens and options are stored twice:
 * <ul>
 *   <li><b>Clean</b>: trimmed on both ends, internal spaces kept.
 *       Used by Collector / Judger for assembling and comparing answers.</li>
 *   <li><b>Display</b>: clean string padded with one space on each side.
 *       Used by the front-end so that buttons / labels have visual
 *       breathing room and are easier to click.</li>
 * </ul>
 *
 * <p>The 2D ArrayList for token options is an idea from GenAI.
 * I have never known 2D ArrayList exists before. -- Etienne Gu
 */
public class SubQuestion implements SubQuestionInterface
{

    /** The number of this sub-question, such as 1 for [Question-1]. */
    private final int questionNumber;

    /** Clean tokens. Trimmed on both ends, internal spaces kept. */
    private final ArrayList<String> tokens;

    /** Display tokens. Each is the clean token padded with one space on both sides. */
    private final ArrayList<String> displayTokens;

    /**
     * Clean option lists.
     * Each index corresponds to one token in the {@code tokens} list.
     */
    private final ArrayList<ArrayList<String>> tokenOptions;

    /**
     * Display option lists.
     * Each entry is the clean option padded with one space on both sides.
     */
    private final ArrayList<ArrayList<String>> displayTokenOptions;

    /** The accepted answers of this sub-question (raw, unmodified). */
    private final ArrayList<String> answers;

    /**
     * Creates an empty sub-question with the given question number.
     *
     * @param questionNumber the number of this sub-question
     */
    public SubQuestion(int questionNumber)
    {
        this.questionNumber = questionNumber;
        this.tokens = new ArrayList<>();
        this.displayTokens = new ArrayList<>();
        this.tokenOptions = new ArrayList<>();
        this.displayTokenOptions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    /**
     * Replaces the current token list with the given token array.
     * <p>
     * For each input token this method stores two versions:
     * <ul>
     *   <li>A clean version produced by {@link #toClean(String)}.</li>
     *   <li>A display version produced by {@link #toDisplay(String)}.</li>
     * </ul>
     * The token option structure is also reset so that each token position
     * has a corresponding (clean and display) option list.
     *
     * @param tokenArray the token array to store
     */
    public void setTokens(String[] tokenArray)
    {
        tokens.clear();
        displayTokens.clear();
        tokenOptions.clear();
        displayTokenOptions.clear();
        if (tokenArray == null) return;

        for (String token : tokenArray)
        {
            String clean = toClean(token);
            tokens.add(clean);
            displayTokens.add(toDisplay(clean));
        }

        // One option list per token position, initially empty.
        for (int i = 0; i < tokens.size(); i++)
        {
            tokenOptions.add(new ArrayList<>());
            displayTokenOptions.add(new ArrayList<>());
        }
    }

    /**
     * Sets the option list for one token position.
     * <p>
     * If the token index is outside the current option list size, the
     * internal lists are expanded automatically. Each input option is
     * stored both as a clean string and as a display-padded string.
     *
     * @param tokenIndex   the index of the token
     * @param optionsArray the option array to store for that token
     */
    public void setTokenOptions(int tokenIndex, String[] optionsArray)
    {
        if (tokenIndex < 0) return;

        // Expand both lists in lockstep, so they always have the same size.
        while (tokenOptions.size() <= tokenIndex)
        {
            tokenOptions.add(new ArrayList<>());
            displayTokenOptions.add(new ArrayList<>());
        }

        tokenOptions.get(tokenIndex).clear();
        displayTokenOptions.get(tokenIndex).clear();
        if (optionsArray == null) return;

        for (String option : optionsArray)
        {
            String clean = toClean(option);
            tokenOptions.get(tokenIndex).add(clean);
            displayTokenOptions.get(tokenIndex).add(toDisplay(clean));
        }
    }

    /**
     * Replaces the current answer list with the given answer array.
     * Answers are stored as-is; normalisation is the Judger's job.
     *
     * @param answerArray the answer array to store
     */
    public void setAnswers(String[] answerArray)
    {
        answers.clear();
        if (answerArray == null) return;
        for (String answer : answerArray)
        {
            answers.add(answer);
        }
    }

    /**
     * Produces the clean form of a raw token / option string.
     * Only the leading and trailing whitespace is removed; internal
     * spaces (e.g. inside string literals or composite tokens such as
     * {@code "_10_set[i-1] = i"}) are intentionally preserved.
     *
     * @param raw the original string from the question bank
     * @return the trimmed string, or empty string if {@code raw} is null
     */
    private static String toClean(String raw)
    {
        return raw == null ? "" : raw.trim();
    }

    /**
     * Produces the display form of a clean string by padding one space
     * on both sides. This gives front-end buttons a bit of breathing
     * room and makes them easier to click.
     *
     * @param clean the clean string
     * @return the display-padded string
     */
    private static String toDisplay(String clean)
    {
        return " " + clean + " ";
    }

    /** {@inheritDoc} */
    @Override
    public int getQuestionNumber()
    {
        return questionNumber;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getTokens()
    {
        return tokens;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getDisplayTokens()
    {
        return displayTokens;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getTokenOptions(int tokenIndex)
    {
        if (tokenIndex < 0 || tokenIndex >= tokenOptions.size())
        {
            return new ArrayList<>();
        }
        return tokenOptions.get(tokenIndex);
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getDisplayTokenOptions(int tokenIndex)
    {
        if (tokenIndex < 0 || tokenIndex >= displayTokenOptions.size())
        {
            return new ArrayList<>();
        }
        return displayTokenOptions.get(tokenIndex);
    }

    /** {@inheritDoc} */
    @Override
    public List<List<String>> getAllTokenOptions()
    {
        ArrayList<List<String>> result = new ArrayList<>();
        for (ArrayList<String> optionList : tokenOptions)
        {
            result.add(optionList);
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getAnswers()
    {
        return answers;
    }
}
