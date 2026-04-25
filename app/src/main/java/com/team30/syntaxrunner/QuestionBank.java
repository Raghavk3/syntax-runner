import java.util.HashMap;
import java.util.Random;

/**
 * An abstract base class for all QuestionBank levels in Syntax Runner.
 * This class stores question sets, loads question data through subclasses,
 * and provides common behaviours for randomly selecting questions.
 */
public abstract class QuestionBank implements QuestionBankInterface
{
    /**
     * Stores all question sets in this QuestionBank.
     * Each key is a question set ID, and each value is a raw String[][] question set.
     */
    protected final HashMap<Integer, String[][]> QUESTIONS = new HashMap<>();

    /**
     * The random number generator used to select question IDs.
     */
    protected final Random RANDOM = new Random();

    /**
     * Creates a QuestionBank and loads its question data.
     * Each subclass is responsible for providing its own question content
     * through {@link #loadQuestions()}.
     */
    public QuestionBank()
    {
        loadQuestions();
    }

    /**
     * Loads all question data into this QuestionBank.
     * Each subclass, such as a specific level QuestionBank, must implement this method
     * and insert its own question sets into the QUESTIONS map.
     */
    protected abstract void loadQuestions();

    /**
     * Returns the ID of a randomly selected question set in this QuestionBank.
     *
     * @return the random question ID, or -1 if this QuestionBank is empty
     */
    @Override
    public int getRandomQuestionId()
    {
        if (QUESTIONS.isEmpty())
        {
            return -1;
        }

        Object[] keys = QUESTIONS.keySet().toArray();
        int randomIndex = RANDOM.nextInt(keys.length);

        return (Integer) keys[randomIndex];
    }

    /**
     * Returns one randomly selected question set from this QuestionBank.
     *
     * @return the selected question set as a String[][], or null if this QuestionBank is empty
     */
    @Override
    public String[][] pickRandomQuestion()
    {
        // Return the chosen question set from this QuestionBank.
        int id = getRandomQuestionId();

        // Return null if this QuestionBank is empty.
        if (id == -1)
        {
            return null;
        }

        return QUESTIONS.get(id);
    }
}