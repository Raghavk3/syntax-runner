import java.util.HashMap;
import java.util.Random;
public class QuestionBank
{
    // Each question is stored in a hashmap with pre-defined integer ID.
    // Starting from ID 1, incrementing continuously. (1, 2, 3, 4...)
    private static final HashMap<Integer, String[][]> QUESTIONS = new HashMap<>();
    private static final Random RANDOM = new Random();

    // The IDs(keys) start from 0.
    // Each level of QuestionBank owns its local IDs, not a global shared ID set.
    static
    {
        QUESTIONS.put(0, new String[][]
                {
                        {"[Question-1]", "for", "(", "int", "i", "=", "0", ";", "i", "<", "10", ";", "i", "++", ")", "{", "...", "}"},
                        {"[Question-2]", "for", "(", "int", "i", "=", "0", ";", "i", "<", "10", ";", "i", "++", ")", "{", "...", "}"},

                        {"[Options-1-1]",  "for", "while", "if"},
                        {"[Options-1-2]",  "(", "{", "["},
                        {"[Options-1-3]",  "int ", "double ", "String "},
                        {"[Options-1-4]",  "i", "j", "count"},
                        {"[Options-1-5]",  "=", "==", ":"},
                        {"[Options-1-6]",  "0", "1", "10"},
                        {"[Options-1-7]",  ";", ",", "."},
                        {"[Options-1-8]",  "i", "j", "count"},
                        {"[Options-1-9]",  "<", "<=", "!="},
                        {"[Options-1-10]", "10", "5", "100"},
                        {"[Options-1-11]", ";", ",", ":"},
                        {"[Options-1-12]", "i", "j", "count"},
                        {"[Options-1-13]", "++", "--", "+=2"},
                        {"[Options-1-14]", ")", "]", "}"},
                        {"[Options-1-15]", "{", "(", "["},
                        {"[Options-1-16]", "...", "System.out.println(i);", "i += 1;"},
                        {"[Options-1-17]", "}", ")", "]"},

                        {"[Options-2-1]",  "for", "while", "if"},
                        {"[Options-2-2]",  "(", "{", "["},
                        {"[Options-2-3]",  "int ", "double ", "String "},
                        {"[Options-2-4]",  "i", "j", "count"},
                        {"[Options-2-5]",  "=", "==", ":"},
                        {"[Options-2-6]",  "0", "1", "10"},
                        {"[Options-2-7]",  ";", ",", "."},
                        {"[Options-2-8]",  "i", "j", "count"},
                        {"[Options-2-9]",  "<", "<=", "!="},
                        {"[Options-2-10]", "10", "5", "100"},
                        {"[Options-2-11]", ";", ",", ":"},
                        {"[Options-2-12]", "i", "j", "count"},
                        {"[Options-2-13]", "++", "--", "+=2"},
                        {"[Options-2-14]", ")", "]", "}"},
                        {"[Options-2-15]", "{", "(", "["},
                        {"[Options-2-16]", "...", "System.out.println(i);", "i += 1;"},
                        {"[Options-2-17]", "}", ")", "]"},

                        {"[Answer-1]",
                                "for(int i=0;i<10;i++){...}",
                                "for(int i=0;i<10;i++){System.out.println(i);}",
                                "for(int j=0;j<10;j++){...}",
                                "for(int count=0;count<10;count++){...}"
                        },
                        {"[Answer-2]",
                                "for(int i=0;i<10;i++){...}",
                                "for(int i=0;i<10;i++){System.out.println(i);}",
                                "for(int j=0;j<10;j++){...}",
                                "for(int count=0;count<10;count++){...}"
                        }
                });
    }

    public static int getRandomQuestionId ()
    {
        // Return -1 when this QuestionBank is empty (stored 0 question).
        if (QUESTIONS.isEmpty()) {return -1;}

        // Put all question IDs (keys) from the HashMap and store them in an array and pick one by index.
        Object[] keys = QUESTIONS.keySet().toArray();
        int randomIndex = RANDOM.nextInt(keys.length);

        return (Integer) keys[randomIndex];
    }

    public static String[][] pickRandomQuestion()
    {
        // Randomly pick and return one question from this QuestionBank.
        int id = getRandomQuestionId();

        // Return null if this QuestionBank is empty.
        if (id == -1) {return null;}

        return QUESTIONS.get(id);
    }
}
