package com.team30.syntaxrunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compares assembled player answers against the accepted answers stored
 * in each {@link SubQuestion}.
 *
 * <p>Both sides are normalised by {@link #normalize(String)} which strips
 * <b>all</b> whitespace characters. This way the answer
 * {@code "System.out.println(\"Hi\");"} and
 * {@code "System . out . println ( \"Hi\" ) ;"} are treated as equal,
 * which matches our "ignore spaces completely" policy.
 *
 * <p>The Judger is stateless; a single instance can be safely reused.
 */
public class Judger
{

    /**
     * Judges every sub-question of the given {@link Question}.
     *
     * @param question     the parsed question
     * @param userAnswers  map of {@code subQuestionNumber -> assembled answer}
     * @return {@code true} only if every sub-question is judged correct
     */
    public boolean judgeAll(Question question, Map<Integer, String> userAnswers)
    {
        if (question == null || userAnswers == null) return false;

        for (SubQuestion sq : question.getAllSubQuestions())
        {
            String userAnswer = userAnswers.get(sq.getQuestionNumber());
            if (!judgeOne(sq, userAnswer))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Judges every sub-question and returns a per-sub-question result map.
     * Useful for "X / Y correct" displays or detailed feedback.
     *
     * @param question     the parsed question
     * @param userAnswers  map of {@code subQuestionNumber -> assembled answer}
     * @return map of {@code subQuestionNumber -> boolean correctness}
     */
    public Map<Integer, Boolean> judgeEach(Question question, Map<Integer, String> userAnswers)
    {
        Map<Integer, Boolean> result = new HashMap<>();
        if (question == null) return result;

        for (SubQuestion sq : question.getAllSubQuestions())
        {
            String userAnswer = userAnswers == null
                    ? null
                    : userAnswers.get(sq.getQuestionNumber());
            result.put(sq.getQuestionNumber(), judgeOne(sq, userAnswer));
        }
        return result;
    }

    /**
     * Judges a single sub-question.
     *
     * @param subQuestion the sub-question to judge
     * @param userAnswer  the player's assembled answer string
     * @return {@code true} if the normalised user answer matches any
     *         normalised accepted answer
     */
    public boolean judgeOne(SubQuestion subQuestion, String userAnswer)
    {
        if (subQuestion == null) return false;

        String normalisedUser = normalize(userAnswer);
        List<String> accepted = subQuestion.getAnswers();
        if (accepted == null) return false;

        for (String acc : accepted)
        {
            if (normalize(acc).equals(normalisedUser))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Strips every whitespace character from the input.
     * Used on both user answers and accepted answers so that any space
     * difference is irrelevant to correctness.
     *
     * @param s the raw string (can be null)
     * @return the whitespace-free string, or empty string if {@code s} is null
     */
    private static String normalize(String s)
    {
        return s == null ? "" : s.replaceAll("\\s+", "");
    }
}
