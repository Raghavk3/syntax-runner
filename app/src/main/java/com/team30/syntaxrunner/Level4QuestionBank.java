public class Level4QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        QUESTIONS.put(0, new String[][]
        {
            {"[Annotation]",
                "// Methods and classes without syntax errors",
                "// :)"
            },

            {"[Question-1]", "public", "static", "void", "main", "(", "String[]", "args", ")", "{", "...", "}"},
            {"[Question-2]", "public", "int", "add", "(", "int", "a", ",", "int", "b", ")", "{", "return", "a", "+", "b", ";", "}"},
            {"[Question-3]", "private", "int", "x", ";"},
            {"[Question-4]", "public", "void", "print", "(", ")", "{", "...", "}"},
            {"[Question-5]", "public", "class", "Test", "{", "...", "}"},

            // ===== Q1 =====
            {"[Options-1-1]", "public", "private", "protected"},
            {"[Options-1-2]", "static", "", "final"},
            {"[Options-1-3]", "void", "int", "String"},
            {"[Options-1-4]", "main", "start", "run"},
            {"[Options-1-5]", "(", "{", "["},
            {"[Options-1-6]", "String[]", "int[]", "double[]"},
            {"[Options-1-7]", "args", "param", "input"},
            {"[Options-1-8]", ")", "]", "}"},
            {"[Options-1-9]", "{", "(", "["},
            {"[Options-1-10]", "...", "System.out.println(\"Hello\");", "return;"},
            {"[Options-1-11]", "}", ")", "]"},

            // ===== Q2 =====
            {"[Options-2-1]", "public", "private", "protected"},
            {"[Options-2-2]", "int", "double", "String"},
            {"[Options-2-3]", "add", "sum", "calc"},
            {"[Options-2-4]", "(", "{", "["},
            {"[Options-2-5]", "int", "double", "String"},
            {"[Options-2-6]", "a", "x", "num"},
            {"[Options-2-7]", ",", ";", ":"},
            {"[Options-2-8]", "int", "double", "String"},
            {"[Options-2-9]", "b", "y", "val"},
            {"[Options-2-10]", ")", "]", "}"},
            {"[Options-2-11]", "{", "(", "["},
            {"[Options-2-12]", "return", "print", "System.out.println"},
            {"[Options-2-13]", "a", "x", "num"},
            {"[Options-2-14]", "+", "-", "*"},
            {"[Options-2-15]", "b", "y", "val"},
            {"[Options-2-16]", ";", ",", ":"},
            {"[Options-2-17]", "}", ")", "]"},

            // ===== Q3 =====
            {"[Options-3-1]", "private", "public", "protected"},
            {"[Options-3-2]", "int", "double", "String"},
            {"[Options-3-3]", "x", "a", "num"},
            {"[Options-3-4]", ";", ",", "."},

            // ===== Q4 =====
            {"[Options-4-1]", "public", "private", "protected"},
            {"[Options-4-2]", "void", "int", "String"},
            {"[Options-4-3]", "print", "show", "display"},
            {"[Options-4-4]", "(", "{", "["},
            {"[Options-4-5]", ")", "]", "}"},
            {"[Options-4-6]", "{", "(", "["},
            {"[Options-4-7]", "...", "System.out.println(\"Hi\");", "return;"},
            {"[Options-4-8]", "}", ")", "]"},

            // ===== Q5 =====
            {"[Options-5-1]", "public", "private", "protected"},
            {"[Options-5-2]", "class", "interface", "enum"},
            {"[Options-5-3]", "Test", "Demo", "Main"},
            {"[Options-5-4]", "{", "(", "["},
            {"[Options-5-5]", "...", "int x;", "void run(){}"},
            {"[Options-5-6]", "}", ")", "]"},

            {"[Answer-1]", "public static void main(String[] args){...}"},
            {"[Answer-2]", "public int add(int a,int b){return a+b;}"},
            {"[Answer-3]", "private int x;"},
            {"[Answer-4]", "public void print(){...}"},
            {"[Answer-5]", "public class Test{...}"}
        });
    }
}