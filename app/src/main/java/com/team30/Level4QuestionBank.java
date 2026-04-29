/**
 * Question bank for Level 4 — Methods and Classes.
 * Focuses on method signatures, return types, access modifiers, and class declarations.
 * Each question has two variants. Correct answer is always the FIRST item in each [Options] row.
 * [Answer-x] contains only the single correct valid answer.
 */
public class Level4QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        // ── QUESTION 0 ──────────────────────────────────────────────────────
        // public static void main(String[] args) { ... }
        // Q1 error: "]" instead of ")"
        // Q2 error: "int" instead of "void"
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// Main method declaration",
                                "// Fix the bracket or return type"
                        },
                        {"[Question-1]", "public", "static", "void", "main", "(", "String[]", "args", "]", "{", "...", "}"},
                        {"[Question-2]", "public", "static", "int",  "main", "(", "String[]", "args", ")", "{", "...", "}"},

                        {"[Options-1-1]",  "public",   "private",  "protected"},
                        {"[Options-1-2]",  "static",   "final",    "abstract"},
                        {"[Options-1-3]",  "void",     "int",      "String"},
                        {"[Options-1-4]",  "main",     "start",    "run"},
                        {"[Options-1-5]",  "(",        "{",        "["},
                        {"[Options-1-6]",  "String[]", "int[]",    "double[]"},
                        {"[Options-1-7]",  "args",     "param",    "input"},
                        {"[Options-1-8]",  ")",        "]",        "}"},
                        {"[Options-1-9]",  "{",        "(",        "["},
                        {"[Options-1-10]", "...",      "System.out.println(\"Hello\");", "return;"},
                        {"[Options-1-11]", "}",        ")",        "]"},

                        {"[Options-2-1]",  "public",   "private",  "protected"},
                        {"[Options-2-2]",  "static",   "final",    "abstract"},
                        {"[Options-2-3]",  "void",     "int",      "String"},
                        {"[Options-2-4]",  "main",     "start",    "run"},
                        {"[Options-2-5]",  "(",        "{",        "["},
                        {"[Options-2-6]",  "String[]", "int[]",    "double[]"},
                        {"[Options-2-7]",  "args",     "param",    "input"},
                        {"[Options-2-8]",  ")",        "]",        "}"},
                        {"[Options-2-9]",  "{",        "(",        "["},
                        {"[Options-2-10]", "...",      "System.out.println(\"Hello\");", "return;"},
                        {"[Options-2-11]", "}",        ")",        "]"},

                        {"[Answer-1]",
                                "public static void main(String[] args){...}"
                        },
                        {"[Answer-2]",
                                "public static void main(String[] args){...}"
                        }
                });

        // ── QUESTION 1 ──────────────────────────────────────────────────────
        // public int add(int a, int b) { return a + b; }
        // Q1 error: ";" instead of ","
        // Q2 error: "[" instead of "("
        QUESTIONS.put(1, new String[][]
                {
                        {"[Annotation]",
                                "// Method that adds two integers",
                                "// Fix the separator or bracket"
                        },
                        {"[Question-1]", "public", "int", "add", "(", "int", "a", ";", "int", "b", ")", "{", "return", "a", "+", "b", ";", "}"},
                        {"[Question-2]", "public", "int", "add", "[", "int", "a", ",", "int", "b", ")", "{", "return", "a", "+", "b", ";", "}"},

                        {"[Options-1-1]",  "public",  "private",  "protected"},
                        {"[Options-1-2]",  "int",     "double",   "String"},
                        {"[Options-1-3]",  "add",     "sum",      "calc"},
                        {"[Options-1-4]",  "(",       "{",        "["},
                        {"[Options-1-5]",  "int",     "double",   "String"},
                        {"[Options-1-6]",  "a",       "x",        "num"},
                        {"[Options-1-7]",  ",",       ";",        ":"},
                        {"[Options-1-8]",  "int",     "double",   "String"},
                        {"[Options-1-9]",  "b",       "y",        "val"},
                        {"[Options-1-10]", ")",       "]",        "}"},
                        {"[Options-1-11]", "{",       "(",        "["},
                        {"[Options-1-12]", "return",  "print",    "System.out.println"},
                        {"[Options-1-13]", "a",       "x",        "num"},
                        {"[Options-1-14]", "+",       "-",        "*"},
                        {"[Options-1-15]", "b",       "y",        "val"},
                        {"[Options-1-16]", ";",       ",",        ":"},
                        {"[Options-1-17]", "}",       ")",        "]"},

                        {"[Options-2-1]",  "public",  "private",  "protected"},
                        {"[Options-2-2]",  "int",     "double",   "String"},
                        {"[Options-2-3]",  "add",     "sum",      "calc"},
                        {"[Options-2-4]",  "(",       "{",        "["},
                        {"[Options-2-5]",  "int",     "double",   "String"},
                        {"[Options-2-6]",  "a",       "x",        "num"},
                        {"[Options-2-7]",  ",",       ";",        ":"},
                        {"[Options-2-8]",  "int",     "double",   "String"},
                        {"[Options-2-9]",  "b",       "y",        "val"},
                        {"[Options-2-10]", ")",       "]",        "}"},
                        {"[Options-2-11]", "{",       "(",        "["},
                        {"[Options-2-12]", "return",  "print",    "System.out.println"},
                        {"[Options-2-13]", "a",       "x",        "num"},
                        {"[Options-2-14]", "+",       "-",        "*"},
                        {"[Options-2-15]", "b",       "y",        "val"},
                        {"[Options-2-16]", ";",       ",",        ":"},
                        {"[Options-2-17]", "}",       ")",        "]"},

                        {"[Answer-1]",
                                "public int add(int a,int b){return a+b;}"
                        },
                        {"[Answer-2]",
                                "public int add(int a,int b){return a+b;}"
                        }
                });

        // ── QUESTION 2 ──────────────────────────────────────────────────────
        // private int x;
        // Q1 error: "," instead of ";"
        // Q2 error: ":" instead of ";"
        QUESTIONS.put(2, new String[][]
                {
                        {"[Annotation]",
                                "// Private field declaration",
                                "// Fix the terminator"
                        },
                        {"[Question-1]", "private", "int", "x", ","},
                        {"[Question-2]", "private", "int", "x", ":"},

                        {"[Options-1-1]", "private", "public",  "protected"},
                        {"[Options-1-2]", "int",     "double",  "String"},
                        {"[Options-1-3]", "x",       "a",       "num"},
                        {"[Options-1-4]", ";",       ",",       "."},

                        {"[Options-2-1]", "private", "public",  "protected"},
                        {"[Options-2-2]", "int",     "double",  "String"},
                        {"[Options-2-3]", "x",       "a",       "num"},
                        {"[Options-2-4]", ";",       ":",       "."},

                        {"[Answer-1]",
                                "private int x;"
                        },
                        {"[Answer-2]",
                                "private int x;"
                        }
                });

        // ── QUESTION 3 ──────────────────────────────────────────────────────
        // public void print() { ... }
        // Q1 error: "]" instead of ")"
        // Q2 error: "int" instead of "void"
        QUESTIONS.put(3, new String[][]
                {
                        {"[Annotation]",
                                "// Void method with no parameters",
                                "// Fix the bracket or return type"
                        },
                        {"[Question-1]", "public", "void", "print", "(", "]", "{", "...", "}"},
                        {"[Question-2]", "public", "int",  "print", "(", ")", "{", "...", "}"},

                        {"[Options-1-1]", "public",  "private",  "protected"},
                        {"[Options-1-2]", "void",    "int",      "String"},
                        {"[Options-1-3]", "print",   "show",     "display"},
                        {"[Options-1-4]", "(",       "{",        "["},
                        {"[Options-1-5]", ")",       "]",        "}"},
                        {"[Options-1-6]", "{",       "(",        "["},
                        {"[Options-1-7]", "...",     "System.out.println(\"Hi\");", "return;"},
                        {"[Options-1-8]", "}",       ")",        "]"},

                        {"[Options-2-1]", "public",  "private",  "protected"},
                        {"[Options-2-2]", "void",    "int",      "String"},
                        {"[Options-2-3]", "print",   "show",     "display"},
                        {"[Options-2-4]", "(",       "{",        "["},
                        {"[Options-2-5]", ")",       "]",        "}"},
                        {"[Options-2-6]", "{",       "(",        "["},
                        {"[Options-2-7]", "...",     "System.out.println(\"Hi\");", "return;"},
                        {"[Options-2-8]", "}",       ")",        "]"},

                        {"[Answer-1]",
                                "public void print(){...}"
                        },
                        {"[Answer-2]",
                                "public void print(){...}"
                        }
                });

        // ── QUESTION 4 ──────────────────────────────────────────────────────
        // public class Test { ... }
        // Q1 error: "Class" instead of "class"
        // Q2 error: "[" instead of "{"
        QUESTIONS.put(4, new String[][]
                {
                        {"[Annotation]",
                                "// Class declaration",
                                "// Fix the keyword or bracket"
                        },
                        {"[Question-1]", "public", "Class", "Test", "{", "...", "}"},
                        {"[Question-2]", "public", "class", "Test", "[", "...", "}"},

                        {"[Options-1-1]", "public",  "private",   "protected"},
                        {"[Options-1-2]", "class",   "Class",     "interface"},
                        {"[Options-1-3]", "Test",    "Demo",      "Main"},
                        {"[Options-1-4]", "{",       "(",         "["},
                        {"[Options-1-5]", "...",     "int x;",    "void run(){}"},
                        {"[Options-1-6]", "}",       ")",         "]"},

                        {"[Options-2-1]", "public",  "private",   "protected"},
                        {"[Options-2-2]", "class",   "Class",     "interface"},
                        {"[Options-2-3]", "Test",    "Demo",      "Main"},
                        {"[Options-2-4]", "{",       "(",         "["},
                        {"[Options-2-5]", "...",     "int x;",    "void run(){}"},
                        {"[Options-2-6]", "}",       ")",         "]"},

                        {"[Answer-1]",
                                "public class Test{...}"
                        },
                        {"[Answer-2]",
                                "public class Test{...}"
                        }
                });
    }
}
