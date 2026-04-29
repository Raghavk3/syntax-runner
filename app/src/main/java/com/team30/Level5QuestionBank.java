/**
 * Question bank for Level 5 — Object-Oriented Features.
 * Focuses on inheritance (extends), interfaces (implements),
 * @Override, super, and abstract/interface declarations.
 * Each question has two variants. Correct answer is always FIRST in options row.
 */
public class Level5QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        // ── QUESTION 0 ──────────────────────────────────────────────────────
        // public class Dog extends Animal { }
        // Error in Q1: "implements" instead of "extends"
        // Error in Q2: "Animal" missing — "Object" instead
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// Dog class inherits from Animal",
                                "// Fix the inheritance keyword"
                        },
                        {"[Question-1]", "public", "class", "Dog", "implements", "Animal", "{", "}"},
                        {"[Question-2]", "public", "class", "Dog", "extends",    "Object",  "{", "}"},

                        {"[Options-1-1]", "public",     "private",   "protected"},
                        {"[Options-1-2]", "class",      "interface", "abstract"},
                        {"[Options-1-3]", "Dog",        "dog",       "DOG"},
                        {"[Options-1-4]", "extends",    "implements","inherits"},
                        {"[Options-1-5]", "Animal",     "Object",    "Runnable"},
                        {"[Options-1-6]", "{",          "(",         "["},
                        {"[Options-1-7]", "}",          ")",         "]"},

                        {"[Options-2-1]", "public",     "private",   "protected"},
                        {"[Options-2-2]", "class",      "interface", "abstract"},
                        {"[Options-2-3]", "Dog",        "dog",       "DOG"},
                        {"[Options-2-4]", "extends",    "implements","inherits"},
                        {"[Options-2-5]", "Animal",     "Object",    "Runnable"},
                        {"[Options-2-6]", "{",          "(",         "["},
                        {"[Options-2-7]", "}",          ")",         "]"},

                        {"[Answer-1]",
                                "public class Dog extends Animal { }"
                        },
                        {"[Answer-2]",
                                "public class Dog extends Animal { }"
                        }
                });

        // ── QUESTION 1 ──────────────────────────────────────────────────────
        // public class Circle implements Shape { }
        // Error in Q1: "extends" instead of "implements"
        // Error in Q2: "private" instead of "public"
        QUESTIONS.put(1, new String[][]
                {
                        {"[Annotation]",
                                "// Circle class implements the Shape interface",
                                "// Fix the keyword error"
                        },
                        {"[Question-1]", "public", "class", "Circle", "extends",    "Shape", "{", "}"},
                        {"[Question-2]", "private","class", "Circle", "implements", "Shape", "{", "}"},

                        {"[Options-1-1]", "public",     "private",    "protected"},
                        {"[Options-1-2]", "class",      "interface",  "abstract"},
                        {"[Options-1-3]", "Circle",     "circle",     "CIRCLE"},
                        {"[Options-1-4]", "implements", "extends",    "inherits"},
                        {"[Options-1-5]", "Shape",      "Runnable",   "Object"},
                        {"[Options-1-6]", "{",          "(",          "["},
                        {"[Options-1-7]", "}",          ")",          "]"},

                        {"[Options-2-1]", "public",     "private",    "protected"},
                        {"[Options-2-2]", "class",      "interface",  "abstract"},
                        {"[Options-2-3]", "Circle",     "circle",     "CIRCLE"},
                        {"[Options-2-4]", "implements", "extends",    "inherits"},
                        {"[Options-2-5]", "Shape",      "Runnable",   "Object"},
                        {"[Options-2-6]", "{",          "(",          "["},
                        {"[Options-2-7]", "}",          ")",          "]"},

                        {"[Answer-1]",
                                "public class Circle implements Shape { }"
                        },
                        {"[Answer-2]",
                                "public class Circle implements Shape { }"
                        }
                });

        // ── QUESTION 2 ──────────────────────────────────────────────────────
        // @Override public void run() { }
        // Error in Q1: "@Overide" (typo) instead of "@Override"
        // Error in Q2: "private" instead of "public"
        QUESTIONS.put(2, new String[][]
                {
                        {"[Annotation]",
                                "// Override the run method from parent class",
                                "// Fix the annotation or modifier"
                        },
                        {"[Question-1]", "@Overide",  "public", "void", "run", "(", ")", "{", "}"},
                        {"[Question-2]", "@Override", "private","void", "run", "(", ")", "{", "}"},

                        {"[Options-1-1]", "@Override", "@Overide",  "@override"},
                        {"[Options-1-2]", "public",    "private",   "protected"},
                        {"[Options-1-3]", "void",      "int",       "String"},
                        {"[Options-1-4]", "run",       "Run",       "RUN"},
                        {"[Options-1-5]", "(",         "{",         "["},
                        {"[Options-1-6]", ")",         "}",         "]"},
                        {"[Options-1-7]", "{",         "(",         "["},
                        {"[Options-1-8]", "}",         ")",         "]"},

                        {"[Options-2-1]", "@Override", "@Overide",  "@override"},
                        {"[Options-2-2]", "public",    "private",   "protected"},
                        {"[Options-2-3]", "void",      "int",       "String"},
                        {"[Options-2-4]", "run",       "Run",       "RUN"},
                        {"[Options-2-5]", "(",         "{",         "["},
                        {"[Options-2-6]", ")",         "}",         "]"},
                        {"[Options-2-7]", "{",         "(",         "["},
                        {"[Options-2-8]", "}",         ")",         "]"},

                        {"[Answer-1]",
                                "@Override public void run() { }"
                        },
                        {"[Answer-2]",
                                "@Override public void run() { }"
                        }
                });

        // ── QUESTION 3 ──────────────────────────────────────────────────────
        // public abstract class Vehicle { }
        // Error in Q1: "interface" instead of "abstract"
        // Error in Q2: "vehicle" (lowercase) instead of "Vehicle"
        QUESTIONS.put(3, new String[][]
                {
                        {"[Annotation]",
                                "// Declare an abstract Vehicle class",
                                "// Fix the incorrect keyword or identifier"
                        },
                        {"[Question-1]", "public", "interface", "class",    "Vehicle", "{", "}"},
                        {"[Question-2]", "public", "abstract",  "class",    "vehicle", "{", "}"},

                        {"[Options-1-1]", "public",   "private",   "protected"},
                        {"[Options-1-2]", "abstract", "interface", "static"},
                        {"[Options-1-3]", "class",    "interface", "enum"},
                        {"[Options-1-4]", "Vehicle",  "vehicle",   "VEHICLE"},
                        {"[Options-1-5]", "{",        "(",         "["},
                        {"[Options-1-6]", "}",        ")",         "]"},

                        {"[Options-2-1]", "public",   "private",   "protected"},
                        {"[Options-2-2]", "abstract", "interface", "static"},
                        {"[Options-2-3]", "class",    "interface", "enum"},
                        {"[Options-2-4]", "Vehicle",  "vehicle",   "VEHICLE"},
                        {"[Options-2-5]", "{",        "(",         "["},
                        {"[Options-2-6]", "}",        ")",         "]"},

                        {"[Answer-1]",
                                "public abstract class Vehicle { }"
                        },
                        {"[Answer-2]",
                                "public abstract class Vehicle { }"
                        }
                });

        // ── QUESTION 4 ──────────────────────────────────────────────────────
        // super.toString()
        // Error in Q1: "this" instead of "super"
        // Error in Q2: "tostring" (lowercase) instead of "toString"
        QUESTIONS.put(4, new String[][]
                {
                        {"[Annotation]",
                                "// Call the parent class toString method",
                                "// Fix the keyword or method name"
                        },
                        {"[Question-1]", "this",  ".", "toString", "(", ")"},
                        {"[Question-2]", "super",  ".", "tostring", "(", ")"},

                        {"[Options-1-1]", "super",    "this",     "self"},
                        {"[Options-1-2]", ".",         "::",       ","},
                        {"[Options-1-3]", "toString",  "tostring", "ToString"},
                        {"[Options-1-4]", "(",         "{",        "["},
                        {"[Options-1-5]", ")",         "}",        "]"},

                        {"[Options-2-1]", "super",    "this",     "self"},
                        {"[Options-2-2]", ".",         "::",       ","},
                        {"[Options-2-3]", "toString",  "tostring", "ToString"},
                        {"[Options-2-4]", "(",         "{",        "["},
                        {"[Options-2-5]", ")",         "}",        "]"},

                        {"[Answer-1]",
                                "super.toString()"
                        },
                        {"[Answer-2]",
                                "super.toString()"
                        }
                });
    }
}