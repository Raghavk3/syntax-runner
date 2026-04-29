/**
 * Question bank for Level 2 — Variable Declarations.
 * Focuses on data types, assignment operators, literals, and terminators.
 * Each question has two variants. Correct answer is always the FIRST item in each [Options] row.
 * [Answer-x] contains only the single correct valid answer.
 */
public class Level2QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        // ── QUESTION 0 ──────────────────────────────────────────────────────
        // int a = 10;
        // Q1 error: ":" instead of "="
        // Q2 error: "," instead of ";"
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// Declare an integer variable with value 10",
                                "// Fix the assignment operator or terminator"
                        },
                        {"[Question-1]", "int", "a", ":", "10", ";"},
                        {"[Question-2]", "int", "a", "=", "10", ","},

                        {"[Options-1-1]", "int",  "double", "String"},
                        {"[Options-1-2]", "a",    "b",      "num"},
                        {"[Options-1-3]", "=",    ":",      "=="},
                        {"[Options-1-4]", "10",   "5",      "0"},
                        {"[Options-1-5]", ";",    ",",      "."},

                        {"[Options-2-1]", "int",  "double", "String"},
                        {"[Options-2-2]", "a",    "b",      "num"},
                        {"[Options-2-3]", "=",    ":",      "=="},
                        {"[Options-2-4]", "10",   "5",      "0"},
                        {"[Options-2-5]", ";",    ",",      "."},

                        {"[Answer-1]",
                                "int a=10;"
                        },
                        {"[Answer-2]",
                                "int a=10;"
                        }
                });

        // ── QUESTION 1 ──────────────────────────────────────────────────────
        // double d = 5.5;
        // Q1 error: "int" instead of "double"
        // Q2 error: "==" instead of "="
        QUESTIONS.put(1, new String[][]
                {
                        {"[Annotation]",
                                "// Declare a double variable with value 5.5",
                                "// Fix the type or assignment operator"
                        },
                        {"[Question-1]", "int",    "d", "=",  "5.5", ";"},
                        {"[Question-2]", "double", "d", "==", "5.5", ";"},

                        {"[Options-1-1]", "double", "int",    "String"},
                        {"[Options-1-2]", "d",      "x",      "num"},
                        {"[Options-1-3]", "=",      "==",     ":"},
                        {"[Options-1-4]", "5.5",    "2.2",    "1.0"},
                        {"[Options-1-5]", ";",      ",",      "."},

                        {"[Options-2-1]", "double", "int",    "String"},
                        {"[Options-2-2]", "d",      "x",      "num"},
                        {"[Options-2-3]", "=",      "==",     ":"},
                        {"[Options-2-4]", "5.5",    "2.2",    "1.0"},
                        {"[Options-2-5]", ";",      ",",      "."},

                        {"[Answer-1]",
                                "double d=5.5;"
                        },
                        {"[Answer-2]",
                                "double d=5.5;"
                        }
                });

        // ── QUESTION 2 ──────────────────────────────────────────────────────
        // String s = "Hi";
        // Q1 error: "'Hi'" instead of "\"Hi\"" (single quotes)
        // Q2 error: ":" instead of "="
        QUESTIONS.put(2, new String[][]
                {
                        {"[Annotation]",
                                "// Declare a String variable with value \"Hi\"",
                                "// Fix the quotes or assignment operator"
                        },
                        {"[Question-1]", "String", "s", "=", "'Hi'",   ";"},
                        {"[Question-2]", "String", "s", ":", "\"Hi\"", ";"},

                        {"[Options-1-1]", "String",  "int",   "double"},
                        {"[Options-1-2]", "s",       "str",   "text"},
                        {"[Options-1-3]", "=",       "==",    ":"},
                        {"[Options-1-4]", "\"Hi\"",  "'Hi'",  "Hi"},
                        {"[Options-1-5]", ";",       ",",     "."},

                        {"[Options-2-1]", "String",  "int",   "double"},
                        {"[Options-2-2]", "s",       "str",   "text"},
                        {"[Options-2-3]", "=",       "==",    ":"},
                        {"[Options-2-4]", "\"Hi\"",  "'Hi'",  "Hi"},
                        {"[Options-2-5]", ";",       ",",     "."},

                        {"[Answer-1]",
                                "String s=\"Hi\";"
                        },
                        {"[Answer-2]",
                                "String s=\"Hi\";"
                        }
                });

        // ── QUESTION 3 ──────────────────────────────────────────────────────
        // boolean flag = true;
        // Q1 error: "True" instead of "true"
        // Q2 error: "==" instead of "="
        QUESTIONS.put(3, new String[][]
                {
                        {"[Annotation]",
                                "// Declare a boolean variable set to true",
                                "// Fix the value casing or operator"
                        },
                        {"[Question-1]", "boolean", "flag", "=",  "True", ";"},
                        {"[Question-2]", "boolean", "flag", "==", "true", ";"},

                        {"[Options-1-1]", "boolean", "Boolean", "bool"},
                        {"[Options-1-2]", "flag",    "f",       "check"},
                        {"[Options-1-3]", "=",       "==",      ":"},
                        {"[Options-1-4]", "true",    "True",    "TRUE"},
                        {"[Options-1-5]", ";",       ",",       "."},

                        {"[Options-2-1]", "boolean", "Boolean", "bool"},
                        {"[Options-2-2]", "flag",    "f",       "check"},
                        {"[Options-2-3]", "=",       "==",      ":"},
                        {"[Options-2-4]", "true",    "True",    "TRUE"},
                        {"[Options-2-5]", ";",       ",",       "."},

                        {"[Answer-1]",
                                "boolean flag=true;"
                        },
                        {"[Answer-2]",
                                "boolean flag=true;"
                        }
                });

        // ── QUESTION 4 ──────────────────────────────────────────────────────
        // char c = 'A';
        // Q1 error: "\"A\"" instead of "'A'" (double quotes for char)
        // Q2 error: "." instead of ";"
        QUESTIONS.put(4, new String[][]
                {
                        {"[Annotation]",
                                "// Declare a char variable with value 'A'",
                                "// Fix the quotes or terminator"
                        },
                        {"[Question-1]", "char", "c", "=", "\"A\"", ";"},
                        {"[Question-2]", "char", "c", "=", "'A'",   "."},

                        {"[Options-1-1]", "char",  "Char",   "String"},
                        {"[Options-1-2]", "c",     "ch",     "letter"},
                        {"[Options-1-3]", "=",     "==",     ":"},
                        {"[Options-1-4]", "'A'",   "\"A\"",  "A"},
                        {"[Options-1-5]", ";",     ",",      "."},

                        {"[Options-2-1]", "char",  "Char",   "String"},
                        {"[Options-2-2]", "c",     "ch",     "letter"},
                        {"[Options-2-3]", "=",     "==",     ":"},
                        {"[Options-2-4]", "'A'",   "\"A\"",  "A"},
                        {"[Options-2-5]", ";",     ",",      "."},

                        {"[Answer-1]",
                                "char c='A';"
                        },
                        {"[Answer-2]",
                                "char c='A';"
                        }
                });
    }
}