import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A QuestionBank composed of several underlying QuestionBanks.
 * <p>
 * {@link #pickRandomQuestion()} first picks one of the underlying banks
 * uniformly at random, then asks that bank for a random question.
 * This keeps the per-level question density independent of how many
 * questions each level happens to contain.
 */
public class AggregatedQuestionBank implements QuestionBankInterface
{

    private final List<QuestionBankInterface> banks;
    private final Random random = new Random();

    /**
     * Creates an aggregated bank from a non-empty list of sub-banks.
     *
     * @param banks the underlying QuestionBanks; must not be empty
     */
    public AggregatedQuestionBank(List<QuestionBankInterface> banks)
    {
        if (banks == null || banks.isEmpty())
        {
            throw new IllegalArgumentException("AggregatedQuestionBank requires at least one bank");
        }
        this.banks = new ArrayList<>(banks);
    }

    /** {@inheritDoc} */
    @Override
    public int getRandomQuestionId()
    {
        // Not meaningful across multiple banks; delegate to a random one.
        return banks.get(random.nextInt(banks.size())).getRandomQuestionId();
    }

    /** {@inheritDoc} */
    @Override
    public String[][] pickRandomQuestion()
    {
        // Step 1: pick one underlying bank uniformly.
        QuestionBankInterface chosen = banks.get(random.nextInt(banks.size()));
        // Step 2: delegate the actual pick to that bank.
        return chosen.pickRandomQuestion();
    }
}