/**
 * Defines the basic structure of a QuestionBank in Syntax Runner.
 * A QuestionBank can randomly choose and return one stored question set.
 */
public interface QuestionBankInterface
{
    /**
     * Returns the ID of a randomly selected question set in this QuestionBank.
     *
     * @return the random question ID, or -1 if this QuestionBank is empty
     */
    int getRandomQuestionId();

    /**
     * Returns one randomly selected question set from this QuestionBank.
     *
     * @return the selected question set as a String[][], or null if this QuestionBank is empty
     */
    String[][] pickRandomQuestion();
}
// p.s.: It is really an excellent experience to use GenAI for enhancing readability of annotation.-- Etienne Gu
// So this is a "javadoc ed" QuestionBank interface, and basically the start of Syntax Runner.