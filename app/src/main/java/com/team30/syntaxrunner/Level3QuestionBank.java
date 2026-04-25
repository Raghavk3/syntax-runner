public class Level3QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        QUESTIONS.put(0, new String[][]
        {
            {"[Annotation]",
                "// Control flow without syntax errors",
                "// :)"
            },

            {"[Question-1]", "if", "(", "x", ">", "5", ")", "{", "...", "}"},
            {"[Question-2]", "if", "(", "x", "<", "10", ")", "{", "...", "}", "else", "{", "...", "}"},
            {"[Question-3]", "while", "(", "i", "<", "10", ")", "{", "...", "}"},
            {"[Question-4]", "for", "(", "int", "i", "=", "0", ";", "i", "<", "5", ";", "i", "++", ")", "{", "...", "}"},
            {"[Question-5]", "do", "{", "...", "}", "while", "(", "i", "<", "5", ")", ";"},

            // ===== Q1 =====
            {"[Options-1-1]", "if", "for", "while"},
            {"[Options-1-2]", "(", "{", "["},
            {"[Options-1-3]", "x", "i", "count"},
            {"[Options-1-4]", ">", "<", "=="},
            {"[Options-1-5]", "5", "10", "0"},
            {"[Options-1-6]", ")", "]", "}"},
            {"[Options-1-7]", "{", "(", "["},
            {"[Options-1-8]", "...", "System.out.println(x);", "x++;"},
            {"[Options-1-9]", "}", ")", "]"},

            // ===== Q2 =====
            {"[Options-2-1]", "if", "for", "while"},
            {"[Options-2-2]", "(", "{", "["},
            {"[Options-2-3]", "x", "i", "count"},
            {"[Options-2-4]", "<", ">", "=="},
            {"[Options-2-5]", "10", "5", "0"},
            {"[Options-2-6]", ")", "]", "}"},
            {"[Options-2-7]", "{", "(", "["},
            {"[Options-2-8]", "...", "System.out.println(x);", "x++;"},
            {"[Options-2-9]", "}", ")", "]"},
            {"[Options-2-10]", "else", "elseif", "then"},
            {"[Options-2-11]", "{", "(", "["},
            {"[Options-2-12]", "...", "System.out.println(\"No\");", "x--;"},
            {"[Options-2-13]", "}", ")", "]"},

            // ===== Q3 =====
            {"[Options-3-1]", "while", "if", "for"},
            {"[Options-3-2]", "(", "{", "["},
            {"[Options-3-3]", "i", "x", "count"},
            {"[Options-3-4]", "<", ">", "=="},
            {"[Options-3-5]", "10", "5", "0"},
            {"[Options-3-6]", ")", "]", "}"},
            {"[Options-3-7]", "{", "(", "["},
            {"[Options-3-8]", "...", "i++;", "System.out.println(i);"},
            {"[Options-3-9]", "}", ")", "]"},

            // ===== Q4 =====
            {"[Options-4-1]", "for", "while", "if"},
            {"[Options-4-2]", "(", "{", "["},
            {"[Options-4-3]", "int", "double", "String"},
            {"[Options-4-4]", "i", "j", "count"},
            {"[Options-4-5]", "=", "==", ":"},
            {"[Options-4-6]", "0", "1", "5"},
            {"[Options-4-7]", ";", ",", "."},
            {"[Options-4-8]", "i", "j", "count"},
            {"[Options-4-9]", "<", "<=", "!="},
            {"[Options-4-10]", "5", "10", "100"},
            {"[Options-4-11]", ";", ",", ":"},
            {"[Options-4-12]", "i", "j", "count"},
            {"[Options-4-13]", "++", "--", "+=2"},
            {"[Options-4-14]", ")", "]", "}"},
            {"[Options-4-15]", "{", "(", "["},
            {"[Options-4-16]", "...", "System.out.println(i);", "i+=1;"},
            {"[Options-4-17]", "}", ")", "]"},

            // ===== Q5 =====
            {"[Options-5-1]", "do", "while", "if"},
            {"[Options-5-2]", "{", "(", "["},
            {"[Options-5-3]", "...", "i++;", "System.out.println(i);"},
            {"[Options-5-4]", "}", ")", "]"},
            {"[Options-5-5]", "while", "for", "if"},
            {"[Options-5-6]", "(", "{", "["},
            {"[Options-5-7]", "i", "x", "count"},
            {"[Options-5-8]", "<", ">", "=="},
            {"[Options-5-9]", "5", "10", "0"},
            {"[Options-5-10]", ")", "]", "}"},
            {"[Options-5-11]", ";", ",", "."},

            {"[Answer-1]", "if(x>5){...}"},
            {"[Answer-2]", "if(x<10){...}else{...}"},
            {"[Answer-3]", "while(i<10){...}"},
            {"[Answer-4]", "for(int i=0;i<5;i++){...}"},
            {"[Answer-5]", "do{...}while(i<5);"}
        });
    }
}