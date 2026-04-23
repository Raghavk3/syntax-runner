import java.util.List;

/**
 * A test class for checking whether QuestionBank, Question,
 * and SubQuestion work correctly together.
 */
public class QuestionTest
{
    /**
     * Runs the full test flow for the current Syntax Runner question model.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("========== SYNTAX RUNNER QUESTION TEST ==========\n");

        // Step 1: Create a sample question bank.
        QuestionBankInterface bank = new SampleLevel3QuestionBank();
        System.out.println("[PASS] SampleLevel3QuestionBank created.\n");

        // Step 2: Test random question ID.
        int randomId = bank.getRandomQuestionId();
        System.out.println("----- Random ID Test -----");
        System.out.println("Random question ID = " + randomId);

        if (randomId == -1)
        {
            System.out.println("[FAIL] QuestionBank is empty.\n");
            return;
        }
        else
        {
            System.out.println("[PASS] A valid random question ID was returned.\n");
        }

        // Step 3: Pick a random raw question.
        String[][] rawQuestion = null;

        while (rawQuestion == null)
        {
            rawQuestion = bank.pickRandomQuestion();
        }

        System.out.println("----- Raw Question Test -----");
        System.out.println("[PASS] Successfully got a non-null raw question.\n");

        // Step 4: Parse the raw question into a Question object.
        Question question = new Question(rawQuestion);
        System.out.println("----- Question Object Test -----");
        System.out.println("[PASS] Successfully created Question object.\n");

        // Step 5: Test annotation lines.
        List<String> annotationLines = question.getAnnotationLines();
        System.out.println("----- Annotation Test -----");

        if (annotationLines == null)
        {
            System.out.println("[FAIL] Annotation list is null.\n");
        }
        else if (annotationLines.isEmpty())
        {
            System.out.println("[WARN] Annotation list is empty.\n");
        }
        else
        {
            System.out.println("[PASS] Annotation lines found:");
            for (String line : annotationLines)
            {
                System.out.println("  " + line);
            }
            System.out.println();
        }

        // Step 6: Test question count.
        int questionCount = question.getQuestionCount();
        System.out.println("----- Question Count Test -----");
        System.out.println("Question count = " + questionCount);

        if (questionCount <= 0)
        {
            System.out.println("[FAIL] No sub-questions were parsed.\n");
            return;
        }
        else
        {
            System.out.println("[PASS] Sub-question count parsed successfully.\n");
        }

        // Step 7: Test each SubQuestion.
        System.out.println("----- SubQuestion Test -----");

        for (int i = 1; i <= questionCount; i++)
        {
            SubQuestion subQuestion = question.getSubQuestion(i);

            if (subQuestion == null)
            {
                System.out.println("[FAIL] SubQuestion " + i + " is null.\n");
                continue;
            }

            System.out.println("SubQuestion #" + subQuestion.getQuestionNumber());

            // 7.1 Test tokens.
            List<String> tokens = subQuestion.getTokens();
            if (tokens == null || tokens.isEmpty())
            {
                System.out.println("  [FAIL] Tokens are missing.\n");
                continue;
            }
            else
            {
                System.out.println("  [PASS] Tokens:");
                for (int t = 0; t < tokens.size(); t++)
                {
                    System.out.println("    Token " + (t + 1) + ": " + tokens.get(t));
                }
            }

            // 7.2 Test options for each token.
            System.out.println("  [PASS] Token options:");
            for (int t = 0; t < tokens.size(); t++)
            {
                List<String> options = subQuestion.getTokenOptions(t);

                System.out.print("    Options for Token " + (t + 1) + " (" + tokens.get(t) + "): ");

                if (options == null || options.isEmpty())
                {
                    System.out.println("[none]");
                }
                else
                {
                    for (int o = 0; o < options.size(); o++)
                    {
                        System.out.print(options.get(o));
                        if (o < options.size() - 1)
                        {
                            System.out.print(" | ");
                        }
                    }
                    System.out.println();
                }
            }

            // 7.3 Test all token option lists.
            List<List<String>> allTokenOptions = subQuestion.getAllTokenOptions();
            if (allTokenOptions == null)
            {
                System.out.println("  [FAIL] getAllTokenOptions() returned null.");
            }
            else
            {
                System.out.println("  [PASS] getAllTokenOptions() returned " + allTokenOptions.size() + " option lists.");
            }

            // 7.4 Test answers.
            List<String> answers = subQuestion.getAnswers();
            if (answers == null || answers.isEmpty())
            {
                System.out.println("  [FAIL] Answers are missing.");
            }
            else
            {
                System.out.println("  [PASS] Answers:");
                for (String answer : answers)
                {
                    System.out.println("    " + answer);
                }
            }

            System.out.println();
        }

        // Step 8: Test getAllSubQuestions().
        System.out.println("----- All SubQuestions Test -----");
        List<SubQuestion> allSubQuestions = question.getAllSubQuestions();

        if (allSubQuestions == null)
        {
            System.out.println("[FAIL] getAllSubQuestions() returned null.\n");
        }
        else
        {
            System.out.println("[PASS] getAllSubQuestions() returned " + allSubQuestions.size() + " sub-questions.\n");
        }

        // Step 9: Final summary.
        System.out.println("========== TEST FINISHED ==========");
    }
}
