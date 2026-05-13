package com.team30.syntaxrunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Assembles a player's per-token picks into a single answer string per
 * sub-question, ready to be fed into the {@link Judger}.
 *
 * <p>The Collector is intentionally stateless and front-end agnostic.
 * Whether the picks come from a real UI or from a {@link TestCollector}
 * (the "fake player"), the assembly logic is the same: concatenate every
 * picked token in order using a {@link StringBuilder}.
 *
 * <p>Picks are expected to be in <b>clean form</b> (the strings returned
 * by {@link SubQuestion#getTokenOptions(int)}). Display-padded strings
 * also work because the Judger strips all whitespace, but using clean
 * strings keeps debug logs readable.
 */
public class Collector
{

    /**
     * Assembles every sub-question's picks into one answer string.
     *
     * @param picks map of {@code subQuestionNumber -> ordered list of picked tokens}
     * @return map of {@code subQuestionNumber -> assembled answer string}
     */
    public Map<Integer, String> assemble(Map<Integer, List<String>> picks)
    {
        Map<Integer, String> assembled = new HashMap<>();
        if (picks == null) return assembled;

        for (Map.Entry<Integer, List<String>> entry : picks.entrySet()) {
            assembled.put(entry.getKey(), assembleOne(entry.getValue()));
        }
        return assembled;
    }

    /**
     * Assembles one sub-question's picks into a single string.
     *
     * @param tokens the ordered list of picked tokens for this sub-question
     * @return the concatenated answer string (empty if {@code tokens} is null)
     */
    public String assembleOne(List<String> tokens)
    {
        StringBuilder sb = new StringBuilder();
        if (tokens == null) return sb.toString();
        for (String token : tokens)
        {
            if (token != null) sb.append(token);
        }
        return sb.toString();
    }
}
