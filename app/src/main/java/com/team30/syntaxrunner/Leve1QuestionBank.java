/**
 * Question bank for Level 1 — Basic Output.
 * Focuses on System.out.println syntax, semicolons, and print chain keywords.
 * Each question has two variants. Correct answer is always the FIRST item in each [Options] row.
 * [Answer-x] contains only the single correct valid answer.
 */
public class Level1QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        // ── QUESTION 0 ──────────────────────────────────────────────────────
        // System.out.println("Hello World");
        // Q1 error: "in" instead of "out"
        // Q2 error: "print" instead of "println"
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// Print 'Hello World' to the console",
                                "// Fix the incorrect token in the print statement"
                        },
                        {"[Question-1]", "System", ".", "in",  ".", "println", "(", "\"Hello World\"", ")", ";"},
                        {"[Question-2]", "System", ".", "out", ".", "print",   "(", "\"Hello World\"", ")", ";"},

                        {"[Options-1-1]", "System",         "Sys",         "system"},
                        {"[Options-1-2]", ".",               "::",          ","},
                        {"[Options-1-3]", "out",             "in",          "err"},
                        {"[Options-1-4]", ".",               "::",          ","},
                        {"[Options-1-5]", "println",         "print",       "printf"},
                        {"[Options-1-6]", "(",               "{",           "["},
                        {"[Options-1-7]", "\"Hello World\"", "Hello World", "'Hello World'"},
                        {"[Options-1-8]", ")",               "]",           "}"},
                        {"[Options-1-9]", ";",               ",",           "."},

                        {"[Options-2-1]", "System",         "Sys",         "system"},
                        {"[Options-2-2]", ".",               "::",          ","},
                        {"[Options-2-3]", "out",             "in",          "err"},
                        {"[Options-2-4]", ".",               "::",          ","},
                        {"[Options-2-5]", "println",         "print",       "printf"},
                        {"[Options-2-6]", "(",               "{",           "["},
                        {"[Options-2-7]", "\"Hello World\"", "Hello World", "'Hello World'"},
                        {"[Options-2-8]", ")",               "]",           "}"},
                        {"[Options-2-9]", ";",               ",",           "."},

                        {"[Answer-1]",
                                "System.out.println(\"Hello World\");"
                        }
                        {"[Answer-2]",
                                "System.out.println(\"Hello World\");"
                        }
                });

        // ── QUESTION 1 ──────────────────────────────────────────────────────
        // System.out.println("Java is fun");
        // Q1 error: "," instead of ";" at end
        // Q2 error: "err" instead of "out"
        QUESTIONS.put(1, new String[][]
                {
                        {"[Annotation]",
                                "// Print 'Java is fun' to the console",
                                "// Fix the incorrect token"
                        },
                        {"[Question-1]", "System", ".", "out", ".", "println", "(", "\"Java is fun\"", ")", ","},
                        {"[Question-2]", "System", ".", "err", ".", "println", "(", "\"Java is fun\"", ")", ";"},

                        {"[Options-1-1]", "System",         "Sys",         "system"},
                        {"[Options-1-2]", ".",               "::",          ","},
                        {"[Options-1-3]", "out",             "in",          "err"},
                        {"[Options-1-4]", ".",               "::",          ","},
                        {"[Options-1-5]", "println",         "print",       "printf"},
                        {"[Options-1-6]", "(",               "{",           "["},
                        {"[Options-1-7]", "\"Java is fun\"", "Java is fun", "'Java is fun'"},
                        {"[Options-1-8]", ")",               "]",           "}"},
                        {"[Options-1-9]", ";",               ",",           "."},

                        {"[Options-2-1]", "System",         "Sys",         "system"},
                        {"[Options-2-2]", ".",               "::",          ","},
                        {"[Options-2-3]", "out",             "in",          "err"},
                        {"[Options-2-4]", ".",               "::",          ","},
                        {"[Options-2-5]", "println",         "print",       "printf"},
                        {"[Options-2-6]", "(",               "{",           "["},
                        {"[Options-2-7]", "\"Java is fun\"", "Java is fun", "'Java is fun'"},
                        {"[Options-2-8]", ")",               "]",           "}"},
                        {"[Options-2-9]", ";",               ",",           "."},

                        {"[Answer-1]",
                                "System.out.println(\"Java is fun\");"
                        }
                        {"[Answer-2]",
                                "System.out.println(\"Java is fun\");"
                        }
                });

        // ── QUESTION 2 ──────────────────────────────────────────────────────
        // System.out.println(42);
        // Q1 error: "{" instead of "("
        // Q2 error: "]" instead of ")"
        QUESTIONS.put(2, new String[][]
                {
                        {"[Annotation]",
                                "// Print the number 42 to the console",
                                "// Fix the bracket error"
                        },
                        {"[Question-1]", "System", ".", "out", ".", "println", "{", "42", ")", ";"},
                        {"[Question-2]", "System", ".", "out", ".", "println", "(", "42", "]", ";"},

                        {"[Options-1-1]", "System",  "Sys",   "system"},
                        {"[Options-1-2]", ".",        "::",    ","},
                        {"[Options-1-3]", "out",      "in",    "err"},
                        {"[Options-1-4]", ".",        "::",    ","},
                        {"[Options-1-5]", "println",  "print", "printf"},
                        {"[Options-1-6]", "(",        "{",     "["},
                        {"[Options-1-7]", "42",       "41",    "43"},
                        {"[Options-1-8]", ")",        "]",     "}"},
                        {"[Options-1-9]", ";",        ",",     "."},

                        {"[Options-2-1]", "System",  "Sys",   "system"},
                        {"[Options-2-2]", ".",        "::",    ","},
                        {"[Options-2-3]", "out",      "in",    "err"},
                        {"[Options-2-4]", ".",        "::",    ","},
                        {"[Options-2-5]", "println",  "print", "printf"},
                        {"[Options-2-6]", "(",        "{",     "["},
                        {"[Options-2-7]", "42",       "41",    "43"},
                        {"[Options-2-8]", ")",        "]",     "}"},
                        {"[Options-2-9]", ";",        ",",     "."},

                        {"[Answer-1]",
                                "System.out.println(42);"
                        }
                        {"[Answer-2]",
                                "System.out.println(42);"
                        }
                });

        // ── QUESTION 3 ──────────────────────────────────────────────────────
        // System.out.println("Score: " + score);
        // Q1 error: "::" instead of "."
        // Q2 error: "printf" instead of "println"
        QUESTIONS.put(3, new String[][]
                {
                        {"[Annotation]",
                                "// Print a score variable with a label",
                                "// Fix the separator or method name"
                        },
                        {"[Question-1]", "System", "::", "out", ".", "println", "(", "\"Score: \"", "+", "score", ")", ";"},
                        {"[Question-2]", "System", ".",  "out", ".", "printf",  "(", "\"Score: \"", "+", "score", ")", ";"},

                        {"[Options-1-1]",  "System",     "Sys",     "system"},
                        {"[Options-1-2]",  ".",           "::",      ","},
                        {"[Options-1-3]",  "out",         "in",      "err"},
                        {"[Options-1-4]",  ".",           "::",      ","},
                        {"[Options-1-5]",  "println",     "print",   "printf"},
                        {"[Options-1-6]",  "(",           "{",       "["},
                        {"[Options-1-7]",  "\"Score: \"", "Score: ", "'Score: '"},
                        {"[Options-1-8]",  "+",           "-",       "*"},
                        {"[Options-1-9]",  "score",       "Score",   "SCORE"},
                        {"[Options-1-10]", ")",           "]",       "}"},
                        {"[Options-1-11]", ";",           ",",       "."},

                        {"[Options-2-1]",  "System",     "Sys",     "system"},
                        {"[Options-2-2]",  ".",           "::",      ","},
                        {"[Options-2-3]",  "out",         "in",      "err"},
                        {"[Options-2-4]",  ".",           "::",      ","},
                        {"[Options-2-5]",  "println",     "print",   "printf"},
                        {"[Options-2-6]",  "(",           "{",       "["},
                        {"[Options-2-7]",  "\"Score: \"", "Score: ", "'Score: '"},
                        {"[Options-2-8]",  "+",           "-",       "*"},
                        {"[Options-2-9]",  "score",       "Score",   "SCORE"},
                        {"[Options-2-10]", ")",           "]",       "}"},
                        {"[Options-2-11]", ";",           ",",       "."},

                        {"[Answer-1]",
                                "System.out.println(\"Score: \" + score);"
                        }
                        {"[Answer-2]",
                                "System.out.println(\"Score: \" + score);"
                        }
                });

        // ── QUESTION 4 ──────────────────────────────────────────────────────
        // System.out.println("Hello" + " " + "World");
        // Q1 error: "-" instead of second "+"
        // Q2 error: "." instead of first "+"
        QUESTIONS.put(4, new String[][]
                {
                        {"[Annotation]",
                                "// Concatenate and print three strings",
                                "// Fix the operator error"
                        },
                        {"[Question-1]", "System", ".", "out", ".", "println", "(", "\"Hello\"", "+", "\" \"", "-",  "\"World\"", ")", ";"},
                        {"[Question-2]", "System", ".", "out", ".", "println", "(", "\"Hello\"", ".", "\" \"", "+",  "\"World\"", ")", ";"},

                        {"[Options-1-1]",  "System",   "Sys",   "system"},
                        {"[Options-1-2]",  ".",         "::",    ","},
                        {"[Options-1-3]",  "out",       "in",    "err"},
                        {"[Options-1-4]",  ".",         "::",    ","},
                        {"[Options-1-5]",  "println",   "print", "printf"},
                        {"[Options-1-6]",  "(",         "{",     "["},
                        {"[Options-1-7]",  "\"Hello\"", "Hello", "'Hello'"},
                        {"[Options-1-8]",  "+",         "-",     "*"},
                        {"[Options-1-9]",  "\" \"",     " ",     "' '"},
                        {"[Options-1-10]", "+",         "-",     "*"},
                        {"[Options-1-11]", "\"World\"", "World", "'World'"},
                        {"[Options-1-12]", ")",         "]",     "}"},
                        {"[Options-1-13]", ";",         ",",     "."},

                        {"[Options-2-1]",  "System",   "Sys",   "system"},
                        {"[Options-2-2]",  ".",         "::",    ","},
                        {"[Options-2-3]",  "out",       "in",    "err"},
                        {"[Options-2-4]",  ".",         "::",    ","},
                        {"[Options-2-5]",  "println",   "print", "printf"},
                        {"[Options-2-6]",  "(",         "{",     "["},
                        {"[Options-2-7]",  "\"Hello\"", "Hello", "'Hello'"},
                        {"[Options-2-8]",  "+",         ".",     "-"},
                        {"[Options-2-9]",  "\" \"",     " ",     "' '"},
                        {"[Options-2-10]", "+",         "-",     "*"},
                        {"[Options-2-11]", "\"World\"", "World", "'World'"},
                        {"[Options-2-12]", ")",         "]",     "}"},
                        {"[Options-2-13]", ";",         ",",     "."},

                        {"[Answer-1]",
                                "System.out.println(\"Hello\" + \" \" + \"World\");"
                        }
                        {"[Answer-2]",
                                "System.out.println(\"Hello\" + \" \" + \"World\");"
                        }
                });
    }
}