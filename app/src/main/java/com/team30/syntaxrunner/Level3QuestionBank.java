/**
 * Question bank for Level 3 — Control Flow.
 * Focuses on if/else, while, for, do-while bracket matching and keywords.
 * Each question has two variants. Correct answer is always the FIRST item in each [Options] row.
 * [Answer-x] contains only the single correct valid answer.
 */
public class Level3QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        // ── QUESTION 0 ──────────────────────────────────────────────────────
        // if (x > 5) { ... }
        // Q1 error: "[" instead of "("
        // Q2 error: "}" instead of ")"
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// Simple if statement checking x > 5",
                                "// Fix the bracket error"
                        },
                        {"[Question-1]", "if", "[", "x", ">", "5", ")", "{", "...", "}"},
                        {"[Question-2]", "if", "(", "x", ">", "5", "}", "{", "...", "}"},

                        {"[Options-1-1]", "if",  "for",   "while"},
                        {"[Options-1-2]", "(",   "[",     "{"},
                        {"[Options-1-3]", "x",   "i",     "count"},
                        {"[Options-1-4]", ">",   "<",     "=="},
                        {"[Options-1-5]", "5",   "10",    "0"},
                        {"[Options-1-6]", ")",   "]",     "}"},
                        {"[Options-1-7]", "{",   "(",     "["},
                        {"[Options-1-8]", "...", "System.out.println(x);", "x++;"},
                        {"[Options-1-9]", "}",   ")",     "]"},

                        {"[Options-2-1]", "if",  "for",   "while"},
                        {"[Options-2-2]", "(",   "[",     "{"},
                        {"[Options-2-3]", "x",   "i",     "count"},
                        {"[Options-2-4]", ">",   "<",     "=="},
                        {"[Options-2-5]", "5",   "10",    "0"},
                        {"[Options-2-6]", ")",   "]",     "}"},
                        {"[Options-2-7]", "{",   "(",     "["},
                        {"[Options-2-8]", "...", "System.out.println(x);", "x++;"},
                        {"[Options-2-9]", "}",   ")",     "]"},

                        {"[Answer-1]",
                                "if(x>5){...}"
                        },
                        {"[Answer-2]",
                                "if(x>5){...}"
                        }
                });

        // ── QUESTION 1 ──────────────────────────────────────────────────────
        // if (x < 10) { ... } else { ... }
        // Q1 error: "elseif" instead of "else"
        // Q2 error: "]" instead of ")"
        QUESTIONS.put(1, new String[][]
                {
                        {"[Annotation]",
                                "// If-else statement checking x < 10",
                                "// Fix the keyword or bracket"
                        },
                        {"[Question-1]", "if", "(", "x", "<", "10", ")", "{", "...", "}", "elseif", "{", "...", "}"},
                        {"[Question-2]", "if", "(", "x", "<", "10", "]", "{", "...", "}", "else",   "{", "...", "}"},

                        {"[Options-1-1]",  "if",   "for",     "while"},
                        {"[Options-1-2]",  "(",    "{",       "["},
                        {"[Options-1-3]",  "x",    "i",       "count"},
                        {"[Options-1-4]",  "<",    ">",       "=="},
                        {"[Options-1-5]",  "10",   "5",       "0"},
                        {"[Options-1-6]",  ")",    "]",       "}"},
                        {"[Options-1-7]",  "{",    "(",       "["},
                        {"[Options-1-8]",  "...",  "System.out.println(x);", "x++;"},
                        {"[Options-1-9]",  "}",    ")",       "]"},
                        {"[Options-1-10]", "else", "elseif",  "then"},
                        {"[Options-1-11]", "{",    "(",       "["},
                        {"[Options-1-12]", "...",  "System.out.println(\"No\");", "x--;"},
                        {"[Options-1-13]", "}",    ")",       "]"},

                        {"[Options-2-1]",  "if",   "for",     "while"},
                        {"[Options-2-2]",  "(",    "{",       "["},
                        {"[Options-2-3]",  "x",    "i",       "count"},
                        {"[Options-2-4]",  "<",    ">",       "=="},
                        {"[Options-2-5]",  "10",   "5",       "0"},
                        {"[Options-2-6]",  ")",    "]",       "}"},
                        {"[Options-2-7]",  "{",    "(",       "["},
                        {"[Options-2-8]",  "...",  "System.out.println(x);", "x++;"},
                        {"[Options-2-9]",  "}",    ")",       "]"},
                        {"[Options-2-10]", "else", "elseif",  "then"},
                        {"[Options-2-11]", "{",    "(",       "["},
                        {"[Options-2-12]", "...",  "System.out.println(\"No\");", "x--;"},
                        {"[Options-2-13]", "}",    ")",       "]"},

                        {"[Answer-1]",
                                "if(x<10){...}else{...}"
                        },
                        {"[Answer-2]",
                                "if(x<10){...}else{...}"
                        }
                });

        // ── QUESTION 2 ──────────────────────────────────────────────────────
        // while (i < 10) { ... }
        // Q1 error: ";" instead of ")"
        // Q2 error: "[" instead of "("
        QUESTIONS.put(2, new String[][]
                {
                        {"[Annotation]",
                                "// While loop iterating while i < 10",
                                "// Fix the bracket or terminator"
                        },
                        {"[Question-1]", "while", "(", "i", "<", "10", ";", "{", "...", "}"},
                        {"[Question-2]", "while", "[", "i", "<", "10", ")", "{", "...", "}"},

                        {"[Options-1-1]", "while", "if",  "for"},
                        {"[Options-1-2]", "(",     "{",   "["},
                        {"[Options-1-3]", "i",     "x",   "count"},
                        {"[Options-1-4]", "<",     ">",   "=="},
                        {"[Options-1-5]", "10",    "5",   "0"},
                        {"[Options-1-6]", ")",     ";",   "]"},
                        {"[Options-1-7]", "{",     "(",   "["},
                        {"[Options-1-8]", "...",   "i++;", "System.out.println(i);"},
                        {"[Options-1-9]", "}",     ")",   "]"},

                        {"[Options-2-1]", "while", "if",  "for"},
                        {"[Options-2-2]", "(",     "{",   "["},
                        {"[Options-2-3]", "i",     "x",   "count"},
                        {"[Options-2-4]", "<",     ">",   "=="},
                        {"[Options-2-5]", "10",    "5",   "0"},
                        {"[Options-2-6]", ")",     ";",   "]"},
                        {"[Options-2-7]", "{",     "(",   "["},
                        {"[Options-2-8]", "...",   "i++;", "System.out.println(i);"},
                        {"[Options-2-9]", "}",     ")",   "]"},

                        {"[Answer-1]",
                                "while(i<10){...}"
                        },
                        {"[Answer-2]",
                                "while(i<10){...}"
                        }
                });

        // ── QUESTION 3 ──────────────────────────────────────────────────────
        // for (int i = 0; i < 5; i++) { ... }
        // Q1 error: "," instead of first ";"
        // Q2 error: "--" instead of "++"
        QUESTIONS.put(3, new String[][]
                {
                        {"[Annotation]",
                                "// For loop counting from 0 to 4",
                                "// Fix the separator or increment operator"
                        },
                        {"[Question-1]", "for", "(", "int", "i", "=", "0", ",", "i", "<", "5", ";", "i", "++", ")", "{", "...", "}"},
                        {"[Question-2]", "for", "(", "int", "i", "=", "0", ";", "i", "<", "5", ";", "i", "--", ")", "{", "...", "}"},

                        {"[Options-1-1]",  "for",  "while", "if"},
                        {"[Options-1-2]",  "(",    "{",     "["},
                        {"[Options-1-3]",  "int",  "double", "String"},
                        {"[Options-1-4]",  "i",    "j",     "count"},
                        {"[Options-1-5]",  "=",    "==",    ":"},
                        {"[Options-1-6]",  "0",    "1",     "5"},
                        {"[Options-1-7]",  ";",    ",",     "."},
                        {"[Options-1-8]",  "i",    "j",     "count"},
                        {"[Options-1-9]",  "<",    "<=",    "!="},
                        {"[Options-1-10]", "5",    "10",    "100"},
                        {"[Options-1-11]", ";",    ",",     ":"},
                        {"[Options-1-12]", "i",    "j",     "count"},
                        {"[Options-1-13]", "++",   "--",    "+=2"},
                        {"[Options-1-14]", ")",    "]",     "}"},
                        {"[Options-1-15]", "{",    "(",     "["},
                        {"[Options-1-16]", "...",  "System.out.println(i);", "i+=1;"},
                        {"[Options-1-17]", "}",    ")",     "]"},

                        {"[Options-2-1]",  "for",  "while", "if"},
                        {"[Options-2-2]",  "(",    "{",     "["},
                        {"[Options-2-3]",  "int",  "double", "String"},
                        {"[Options-2-4]",  "i",    "j",     "count"},
                        {"[Options-2-5]",  "=",    "==",    ":"},
                        {"[Options-2-6]",  "0",    "1",     "5"},
                        {"[Options-2-7]",  ";",    ",",     "."},
                        {"[Options-2-8]",  "i",    "j",     "count"},
                        {"[Options-2-9]",  "<",    "<=",    "!="},
                        {"[Options-2-10]", "5",    "10",    "100"},
                        {"[Options-2-11]", ";",    ",",     ":"},
                        {"[Options-2-12]", "i",    "j",     "count"},
                        {"[Options-2-13]", "++",   "--",    "+=2"},
                        {"[Options-2-14]", ")",    "]",     "}"},
                        {"[Options-2-15]", "{",    "(",     "["},
                        {"[Options-2-16]", "...",  "System.out.println(i);", "i+=1;"},
                        {"[Options-2-17]", "}",    ")",     "]"},

                        {"[Answer-1]",
                                "for(int i=0;i<5;i++){...}"
                        },
                        {"[Answer-2]",
                                "for(int i=0;i<5;i++){...}"
                        }
                });

        // ── QUESTION 4 ──────────────────────────────────────────────────────
        // do { ... } while (i < 5);
        // Q1 error: "," instead of ";"
        // Q2 error: "[" instead of "("
        QUESTIONS.put(4, new String[][]
                {
                        {"[Annotation]",
                                "// Do-while loop running while i < 5",
                                "// Fix the terminator or bracket"
                        },
                        {"[Question-1]", "do", "{", "...", "}", "while", "(", "i", "<", "5", ")", ","},
                        {"[Question-2]", "do", "{", "...", "}", "while", "[", "i", "<", "5", ")", ";"},

                        {"[Options-1-1]",  "do",    "while", "if"},
                        {"[Options-1-2]",  "{",     "(",     "["},
                        {"[Options-1-3]",  "...",   "i++;",  "System.out.println(i);"},
                        {"[Options-1-4]",  "}",     ")",     "]"},
                        {"[Options-1-5]",  "while", "for",   "if"},
                        {"[Options-1-6]",  "(",     "{",     "["},
                        {"[Options-1-7]",  "i",     "x",     "count"},
                        {"[Options-1-8]",  "<",     ">",     "=="},
                        {"[Options-1-9]",  "5",     "10",    "0"},
                        {"[Options-1-10]", ")",     "]",     "}"},
                        {"[Options-1-11]", ";",     ",",     "."},

                        {"[Options-2-1]",  "do",    "while", "if"},
                        {"[Options-2-2]",  "{",     "(",     "["},
                        {"[Options-2-3]",  "...",   "i++;",  "System.out.println(i);"},
                        {"[Options-2-4]",  "}",     ")",     "]"},
                        {"[Options-2-5]",  "while", "for",   "if"},
                        {"[Options-2-6]",  "(",     "{",     "["},
                        {"[Options-2-7]",  "i",     "x",     "count"},
                        {"[Options-2-8]",  "<",     ">",     "=="},
                        {"[Options-2-9]",  "5",     "10",    "0"},
                        {"[Options-2-10]", ")",     "]",     "}"},
                        {"[Options-2-11]", ";",     ",",     "."},

                        {"[Answer-1]",
                                "do{...}while(i<5);"
                        },
                        {"[Answer-2]",
                                "do{...}while(i<5);"
                        }
                });
    }
}
