public class Level2QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        QUESTIONS.put(0, new String[][]
        {
            {"[Annotation]",
                "// Variable declaration without syntax errors",
                "// :)"
            },

            {"[Question-1]", "int", "a", "=", "10", ";"},
            {"[Question-2]", "double", "d", "=", "5.5", ";"},
            {"[Question-3]", "String", "s", "=", "\"Hi\"", ";"},
            {"[Question-4]", "int", "x", "=", "5", ";"},
            {"[Question-5]", "String", "msg", "=", "\"Hello\"", ";"},

            // ===== Question 1 Options =====
            {"[Options-1-1]", "int", "double", "String"},
            {"[Options-1-2]", "a", "b", "num"},
            {"[Options-1-3]", "=", "==", ":"},
            {"[Options-1-4]", "10", "5", "0"},
            {"[Options-1-5]", ";", ",", "."},

            // ===== Question 2 Options =====
            {"[Options-2-1]", "double", "int", "String"},
            {"[Options-2-2]", "d", "x", "num"},
            {"[Options-2-3]", "=", "==", ":"},
            {"[Options-2-4]", "5.5", "2.2", "1.0"},
            {"[Options-2-5]", ";", ",", "."},

            // ===== Question 3 Options =====
            {"[Options-3-1]", "String", "int", "double"},
            {"[Options-3-2]", "s", "str", "text"},
            {"[Options-3-3]", "=", "==", ":"},
            {"[Options-3-4]", "\"Hi\"", "\"Hello\"", "\"Java\""},
            {"[Options-3-5]", ";", ",", "."},

            // ===== Question 4 Options =====
            {"[Options-4-1]", "int", "double", "String"},
            {"[Options-4-2]", "x", "a", "num"},
            {"[Options-4-3]", "=", "==", ":"},
            {"[Options-4-4]", "5", "10", "0"},
            {"[Options-4-5]", ";", ",", "."},

            // ===== Question 5 Options =====
            {"[Options-5-1]", "String", "int", "double"},
            {"[Options-5-2]", "msg", "text", "str"},
            {"[Options-5-3]", "=", "==", ":"},
            {"[Options-5-4]", "\"Hello\"", "\"Hi\"", "\"Java\""},
            {"[Options-5-5]", ";", ",", "."},

            {"[Answer-1]", "int a=10;"},
            {"[Answer-2]", "double d=5.5;"},
            {"[Answer-3]", "String s=\"Hi\";"},
            {"[Answer-4]", "int x=5;"},
            {"[Answer-5]", "String msg=\"Hello\";"}
        });
    }
}