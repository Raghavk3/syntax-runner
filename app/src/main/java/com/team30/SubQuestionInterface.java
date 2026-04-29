import java.util.List;

/**
 * Represents one parsed sub-question inside a question set.
 * <p>
 * A sub-question contains its own tokens, token options, and answer list.
 * <p>
 * Two views are exposed for tokens and options:
 * <ul>
 *   <li><b>Clean view</b> ({@code getTokens}, {@code getTokenOptions}):
 *       trimmed strings used by Collector / Judger for answer assembly
 *       and comparison. Internal spaces are preserved (e.g. string
 *       literals like {@code "Hello World"} keep their inner space).</li>
 *   <li><b>Display view</b> ({@code getDisplayTokens},
 *       {@code getDisplayTokenOptions}): each token has a single space
 *       padded on both sides, so the front-end renders click targets
 *       with comfortable padding.</li>
 * </ul>
 */
public interface SubQuestionInterface {

    /**
     * Returns the number of this sub-question.
     * For example, question number 2 refers to {@code [Question-2]}.
     *
     * @return the sub-question number
     */
    int getQuestionNumber();

    /**
     * Returns all tokens of this sub-question in the clean view.
     * Each token has been trimmed on both ends; internal spaces are kept.
     *
     * @return a list of clean tokens
     */
    List<String> getTokens();

    /**
     * Returns all tokens of this sub-question in the display view.
     * Each token is the clean token padded with one space on both sides.
     *
     * @return a list of display-ready tokens
     */
    List<String> getDisplayTokens();

    /**
     * Returns the option list (clean view) for the token at the given index.
     * If the index is invalid, an empty list is returned.
     *
     * @param tokenIndex the index of the token
     * @return a list of clean options for that token
     */
    List<String> getTokenOptions(int tokenIndex);

    /**
     * Returns the option list (display view) for the token at the given index.
     * Each option is the clean option padded with one space on both sides.
     * If the index is invalid, an empty list is returned.
     *
     * @param tokenIndex the index of the token
     * @return a list of display-ready options for that token
     */
    List<String> getDisplayTokenOptions(int tokenIndex);

    /**
     * Returns all token option lists of this sub-question (clean view).
     * Each inner list corresponds to one token position.
     *
     * @return a list of token option lists
     */
    List<List<String>> getAllTokenOptions();

    /**
     * Returns all accepted answers for this sub-question.
     * Answers are stored as-is; the Judger is responsible for normalising
     * (e.g. stripping all whitespace) before comparison.
     *
     * @return a list of accepted answers
     */
    List<String> getAnswers();
}