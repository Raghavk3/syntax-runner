import java.util.List;

/**
 * Represents one parsed sub-question inside a question set.
 * A sub-question contains its own tokens, token options, and answer list.
 */
public interface SubQuestionInterface
{
    /**
     * Returns the number of this sub-question.
     * For example, question number 2 refers to [Question-2].
     *
     * @return the sub-question number
     */
    int getQuestionNumber();

    /**
     * Returns all tokens of this sub-question.
     *
     * @return a list of tokens
     */
    List<String> getTokens();

    /**
     * Returns the option list for the token at the given index.
     * If the index is invalid, an empty list is returned.
     *
     * @param tokenIndex the index of the token
     * @return a list of options for that token
     */
    List<String> getTokenOptions(int tokenIndex);

    /**
     * Returns all token option lists of this sub-question.
     * Each inner list corresponds to one token position.
     *
     * @return a list of token option lists
     */
    List<List<String>> getAllTokenOptions();

    /**
     * Returns all accepted answers for this sub-question.
     *
     * @return a list of accepted answers
     */
    List<String> getAnswers();
}