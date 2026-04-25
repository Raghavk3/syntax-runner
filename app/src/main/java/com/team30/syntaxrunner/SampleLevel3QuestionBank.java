/**
 * Question bank for level 3 syntax questions.
 * The given sample question Basically demonstrates the structure of a question.
 * See Raghav's SyntaxRunner_TokenOptions.docx for more information.
 * !!! Be aware of dealing with space (" ") !!!
 */
public class SampleLevel3QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// This for loop should be with no syntax error",
                                "// :)"
                        },
                        {"[Question-1]", "for", "(", "int", "i", "=", "0", ";", "i", "<", "10", ";", "i", "++", ")", "{", "...", "}"},
                        {"[Question-2]", "for", "(", "int", "i", "=", "0", ";", "i", "<", "10", ";", "i", "++", ")", "{", "...", "}"},

                        {"[Options-1-1]", "for", "while", "if"},
                        {"[Options-1-2]", "(", "{", "["},
                        {"[Options-1-3]", "int ", "double ", "String "},
                        {"[Options-1-4]", "i", "j", "count"},
                        {"[Options-1-5]", "=", "==", ":"},
                        {"[Options-1-6]", "0", "1", "10"},
                        {"[Options-1-7]", ";", ",", "."},
                        {"[Options-1-8]", "i", "j", "count"},
                        {"[Options-1-9]", "<", "<=", "!="},
                        {"[Options-1-10]", "10", "5", "100"},
                        {"[Options-1-11]", ";", ",", ":"},
                        {"[Options-1-12]", "i", "j", "count"},
                        {"[Options-1-13]", "++", "--", "+=2"},
                        {"[Options-1-14]", ")", "]", "}"},
                        {"[Options-1-15]", "{", "(", "["},
                        {"[Options-1-16]", "...", "System.out.println(i);", "i += 1;"},
                        {"[Options-1-17]", "}", ")", "]"},

                        {"[Options-2-1]", "for", "while", "if"},
                        {"[Options-2-2]", "(", "{", "["},
                        {"[Options-2-3]", "int ", "double ", "String "},
                        {"[Options-2-4]", "i", "j", "count"},
                        {"[Options-2-5]", "=", "==", ":"},
                        {"[Options-2-6]", "0", "1", "10"},
                        {"[Options-2-7]", ";", ",", "."},
                        {"[Options-2-8]", "i", "j", "count"},
                        {"[Options-2-9]", "<", "<=", "!="},
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
}