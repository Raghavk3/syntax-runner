import java.util.ArrayList;
import java.util.List;

/**
 * Stores one parsed sub-question from a raw Syntax Runner question set.
 * A sub-question contains:
 * tokens of [Question-x],
 * options for each token position,
 * and accepted answers of [Answer-x].
 */
public class SubQuestion implements SubQuestionInterface
{
    /** The number of this sub-question, such as 1 for [Question-1]. */
    private final int questionNumber;

    /** The token list of this sub-question. */
    private final ArrayList<String> tokens;

    /**
     * The option lists for each token position.
     * Each index corresponds to one token in the tokens list.
     * P.S.: This 2D ArrayList is an idea from GenAI. I have never known 2D ArrayList exists before.-- Etienne Gu
     */
    private final ArrayList<ArrayList<String>> tokenOptions;

    /** The accepted answers of this sub-question. */
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
        this.tokenOptions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    /**
     * Replaces the current token list with the given token array.
     * This method also resets the token option structure so that each token
     * position has a corresponding option list.
     *
     * @param tokenArray the token array to store
     */
    public void setTokens(String[] tokenArray)
    {
        tokens.clear();

        if (tokenArray == null)
        {
            return;
        }

        for (String token : tokenArray)
        {
            tokens.add(token);
        }

        tokenOptions.clear();
        for (int i = 0; i < tokens.size(); i++)
        {
            tokenOptions.add(new ArrayList<>());
        }
    }

    /**
     * Sets the option list for one token position.
     * If the token index is outside the current option list size,
     * the internal list is expanded automatically.
     *
     * @param tokenIndex   the index of the token
     * @param optionsArray the option array to store for that token
     */
    public void setTokenOptions(int tokenIndex, String[] optionsArray)
    {
        if (tokenIndex < 0)
        {
            return;
        }

        while (tokenOptions.size() <= tokenIndex)
        {
            tokenOptions.add(new ArrayList<>());
        }

        tokenOptions.get(tokenIndex).clear();

        if (optionsArray == null)
        {
            return;
        }

        for (String option : optionsArray)
        {
            tokenOptions.get(tokenIndex).add(option);
        }
    }

    /**
     * Replaces the current answer list with the given answer array.
     *
     * @param answerArray the answer array to store
     */
    public void setAnswers(String[] answerArray)
    {
        answers.clear();

        if (answerArray == null)
        {
            return;
        }

        for (String answer : answerArray)
        {
            answers.add(answer);
        }
    }

    /**
     * Returns the number of this sub-question.
     *
     * @return the sub-question number
     */
    @Override
    public int getQuestionNumber()
    {
        return questionNumber;
    }

    /**
     * Returns all tokens of this sub-question.
     *
     * @return the token list
     */
    @Override
    public List<String> getTokens()
    {
        return tokens;
    }

    /**
     * Returns the option list for the token at the given index.
     * If the index is invalid, an empty list is returned.
     *
     * @param tokenIndex the index of the token
     * @return the option list of that token
     */
    @Override
    public List<String> getTokenOptions(int tokenIndex)
    {
        if (tokenIndex < 0 || tokenIndex >= tokenOptions.size())
        {
            return new ArrayList<>();
        }

        return tokenOptions.get(tokenIndex);
    }

    /**
     * Returns all token option lists of this sub-question.
     *
     * @return a list containing all token option lists
     */
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

    /**
     * Returns all accepted answers of this sub-question.
     *
     * @return the answer list
     */
    @Override
    public List<String> getAnswers()
    {
        return answers;
    }
}