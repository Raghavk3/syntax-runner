/**
 * Question bank for Level 6 — Advanced Constructs.
 * Focuses on generics (<T>), lambda expressions (->),
 * exception handling (try/catch/finally/throw), and collections.
 * Each question has two variants. Correct answer is always FIRST in options row.
 */
public class Level6QuestionBank extends QuestionBank
{
    @Override
    protected void loadQuestions()
    {
        // ── QUESTION 0 ──────────────────────────────────────────────────────
        // ArrayList<String> list = new ArrayList<>();
        // Error in Q1: "Array" instead of "ArrayList"
        // Error in Q2: "Integer" instead of "String"
        QUESTIONS.put(0, new String[][]
                {
                        {"[Annotation]",
                                "// Declare an ArrayList of Strings",
                                "// Fix the class name or type parameter"
                        },
                        {"[Question-1]", "Array",     "<", "String", ">", "list", "=", "new", "ArrayList", "<>", ";"},
                        {"[Question-2]", "ArrayList", "<", "Integer",">", "list", "=", "new", "ArrayList", "<>", ";"},

                        {"[Options-1-1]",  "ArrayList", "Array",     "List"},
                        {"[Options-1-2]",  "<",          ">",         "("},
                        {"[Options-1-3]",  "String",     "Integer",   "Double"},
                        {"[Options-1-4]",  ">",          "<",         ")"},
                        {"[Options-1-5]",  "list",       "List",      "LIST"},
                        {"[Options-1-6]",  "=",          "==",        ":"},
                        {"[Options-1-7]",  "new",        "New",       "NEW"},
                        {"[Options-1-8]",  "ArrayList",  "Array",     "List"},
                        {"[Options-1-9]",  "<>",         "<String>",  ""},
                        {"[Options-1-10]", ";",          ",",         "."},

                        {"[Options-2-1]",  "ArrayList", "Array",     "List"},
                        {"[Options-2-2]",  "<",          ">",         "("},
                        {"[Options-2-3]",  "String",     "Integer",   "Double"},
                        {"[Options-2-4]",  ">",          "<",         ")"},
                        {"[Options-2-5]",  "list",       "List",      "LIST"},
                        {"[Options-2-6]",  "=",          "==",        ":"},
                        {"[Options-2-7]",  "new",        "New",       "NEW"},
                        {"[Options-2-8]",  "ArrayList",  "Array",     "List"},
                        {"[Options-2-9]",  "<>",         "<String>",  ""},
                        {"[Options-2-10]", ";",          ",",         "."},

                        {"[Answer-1]",
                                "ArrayList<String> list = new ArrayList<>();"
                        }
                        {"[Answer-2]",
                                "ArrayList<String> list = new ArrayList<>();"
                        }
                });

        // ── QUESTION 1 ──────────────────────────────────────────────────────
        // HashMap<String, Integer> map = new HashMap<>();
        // Error in Q1: "Map" instead of "HashMap"
        // Error in Q2: "Integer, String" (swapped) instead of "String, Integer"
        QUESTIONS.put(1, new String[][]
                {
                        {"[Annotation]",
                                "// Declare a HashMap with String keys and Integer values",
                                "// Fix the class name or type parameters"
                        },
                        {"[Question-1]", "Map",     "<", "String", ",", "Integer", ">", "map", "=", "new", "HashMap", "<>", ";"},
                        {"[Question-2]", "HashMap", "<", "Integer",",", "String",  ">", "map", "=", "new", "HashMap", "<>", ";"},

                        {"[Options-1-1]",  "HashMap",  "Map",      "TreeMap"},
                        {"[Options-1-2]",  "<",         ">",        "("},
                        {"[Options-1-3]",  "String",    "Integer",  "Double"},
                        {"[Options-1-4]",  ",",         ";",        "."},
                        {"[Options-1-5]",  "Integer",   "String",   "Double"},
                        {"[Options-1-6]",  ">",         "<",        ")"},
                        {"[Options-1-7]",  "map",       "Map",      "MAP"},
                        {"[Options-1-8]",  "=",         "==",       ":"},
                        {"[Options-1-9]",  "new",       "New",      "NEW"},
                        {"[Options-1-10]", "HashMap",   "Map",      "TreeMap"},
                        {"[Options-1-11]", "<>",        "<String>", ""},
                        {"[Options-1-12]", ";",         ",",        "."},

                        {"[Options-2-1]",  "HashMap",  "Map",      "TreeMap"},
                        {"[Options-2-2]",  "<",         ">",        "("},
                        {"[Options-2-3]",  "String",    "Integer",  "Double"},
                        {"[Options-2-4]",  ",",         ";",        "."},
                        {"[Options-2-5]",  "Integer",   "String",   "Double"},
                        {"[Options-2-6]",  ">",         "<",        ")"},
                        {"[Options-2-7]",  "map",       "Map",      "MAP"},
                        {"[Options-2-8]",  "=",         "==",       ":"},
                        {"[Options-2-9]",  "new",       "New",      "NEW"},
                        {"[Options-2-10]", "HashMap",   "Map",      "TreeMap"},
                        {"[Options-2-11]", "<>",        "<String>", ""},
                        {"[Options-2-12]", ";",         ",",        "."},

                        {"[Answer-1]",
                                "HashMap<String, Integer> map = new HashMap<>();"
                        }
                        {"[Answer-2]",
                                "HashMap<String, Integer> map = new HashMap<>();"
                        }
                });

        // ── QUESTION 2 ──────────────────────────────────────────────────────
        // list.forEach(x -> System.out.println(x));
        // Error in Q1: "=>" instead of "->" (lambda arrow)
        // Error in Q2: "x" missing — "i" used as param instead
        QUESTIONS.put(2, new String[][]
                {
                        {"[Annotation]",
                                "// Use a lambda to print each element in a list",
                                "// Fix the lambda arrow or parameter"
                        },
                        {"[Question-1]", "list", ".", "forEach", "(", "x", "=>", "System", ".", "out", ".", "println", "(", "x", ")", ")", ";"},
                        {"[Question-2]", "list", ".", "forEach", "(", "i", "->", "System", ".", "out", ".", "println", "(", "x", ")", ")", ";"},

                        {"[Options-1-1]",  "list",    "List",   "LIST"},
                        {"[Options-1-2]",  ".",        "::",     ","},
                        {"[Options-1-3]",  "forEach", "for",    "forAll"},
                        {"[Options-1-4]",  "(",        "{",      "["},
                        {"[Options-1-5]",  "x",        "i",      "item"},
                        {"[Options-1-6]",  "->",       "=>",     "--"},
                        {"[Options-1-7]",  "System",  "system", "SYSTEM"},
                        {"[Options-1-8]",  ".",        "::",     ","},
                        {"[Options-1-9]",  "out",      "in",     "err"},
                        {"[Options-1-10]", ".",        "::",     ","},
                        {"[Options-1-11]", "println",  "print",  "printf"},
                        {"[Options-1-12]", "(",        "{",      "["},
                        {"[Options-1-13]", "x",        "i",      "item"},
                        {"[Options-1-14]", ")",        "}",      "]"},
                        {"[Options-1-15]", ")",        "}",      "]"},
                        {"[Options-1-16]", ";",        ",",      "."},

                        {"[Options-2-1]",  "list",    "List",   "LIST"},
                        {"[Options-2-2]",  ".",        "::",     ","},
                        {"[Options-2-3]",  "forEach", "for",    "forAll"},
                        {"[Options-2-4]",  "(",        "{",      "["},
                        {"[Options-2-5]",  "x",        "i",      "item"},
                        {"[Options-2-6]",  "->",       "=>",     "--"},
                        {"[Options-2-7]",  "System",  "system", "SYSTEM"},
                        {"[Options-2-8]",  ".",        "::",     ","},
                        {"[Options-2-9]",  "out",      "in",     "err"},
                        {"[Options-2-10]", ".",        "::",     ","},
                        {"[Options-2-11]", "println",  "print",  "printf"},
                        {"[Options-2-12]", "(",        "{",      "["},
                        {"[Options-2-13]", "x",        "i",      "item"},
                        {"[Options-2-14]", ")",        "}",      "]"},
                        {"[Options-2-15]", ")",        "}",      "]"},
                        {"[Options-2-16]", ";",        ",",      "."},

                        {"[Answer-1]",
                                "list.forEach(x -> System.out.println(x));"
                        }
                        {"[Answer-2]",
                                "list.forEach(x -> System.out.println(x));"
                        }
                });

        // ── QUESTION 3 ──────────────────────────────────────────────────────
        // try { ... } catch (Exception e) { ... }
        // Error in Q1: "catch" missing — "handle" instead
        // Error in Q2: "Exception" replaced with "Error"
        QUESTIONS.put(3, new String[][]
                {
                        {"[Annotation]",
                                "// Basic try-catch block structure",
                                "// Fix the exception handling keyword or type"
                        },
                        {"[Question-1]", "try", "{", "...", "}", "handle",   "(", "Exception", "e", ")", "{", "...", "}"},
                        {"[Question-2]", "try", "{", "...", "}", "catch",    "(", "Error",     "e", ")", "{", "...", "}"},

                        {"[Options-1-1]",  "try",       "Try",     "TRY"},
                        {"[Options-1-2]",  "{",          "(",       "["},
                        {"[Options-1-3]",  "...",        "",        "code"},
                        {"[Options-1-4]",  "}",          ")",       "]"},
                        {"[Options-1-5]",  "catch",      "handle",  "except"},
                        {"[Options-1-6]",  "(",          "{",       "["},
                        {"[Options-1-7]",  "Exception",  "Error",   "Throwable"},
                        {"[Options-1-8]",  "e",          "ex",      "err"},
                        {"[Options-1-9]",  ")",          "}",       "]"},
                        {"[Options-1-10]", "{",          "(",       "["},
                        {"[Options-1-11]", "...",        "",        "code"},
                        {"[Options-1-12]", "}",          ")",       "]"},

                        {"[Options-2-1]",  "try",       "Try",     "TRY"},
                        {"[Options-2-2]",  "{",          "(",       "["},
                        {"[Options-2-3]",  "...",        "",        "code"},
                        {"[Options-2-4]",  "}",          ")",       "]"},
                        {"[Options-2-5]",  "catch",      "handle",  "except"},
                        {"[Options-2-6]",  "(",          "{",       "["},
                        {"[Options-2-7]",  "Exception",  "Error",   "Throwable"},
                        {"[Options-2-8]",  "e",          "ex",      "err"},
                        {"[Options-2-9]",  ")",          "}",       "]"},
                        {"[Options-2-10]", "{",          "(",       "["},
                        {"[Options-2-11]", "...",        "",        "code"},
                        {"[Options-2-12]", "}",          ")",       "]"},

                        {"[Answer-1]",
                                "try { ... } catch (Exception e) { ... }"
                        }
                        {"[Answer-2]",
                                "try { ... } catch (Exception e) { ... }"
                        }
                });

        // ── QUESTION 4 ──────────────────────────────────────────────────────
        // throw new IllegalArgumentException("Invalid input");
        // Error in Q1: "throws" instead of "throw"
        // Error in Q2: "RuntimeException" instead of "IllegalArgumentException"
        QUESTIONS.put(4, new String[][]
                {
                        {"[Annotation]",
                                "// Throw an exception for invalid input",
                                "// Fix the throw keyword or exception type"
                        },
                        {"[Question-1]", "throws", "new", "IllegalArgumentException", "(", "\"Invalid input\"", ")", ";"},
                        {"[Question-2]", "throw",  "new", "RuntimeException",         "(", "\"Invalid input\"", ")", ";"},

                        {"[Options-1-1]", "throw",                    "throws",                  "Throw"},
                        {"[Options-1-2]", "new",                      "New",                     "NEW"},
                        {"[Options-1-3]", "IllegalArgumentException",  "RuntimeException",        "Exception"},
                        {"[Options-1-4]", "(",                         "{",                       "["},
                        {"[Options-1-5]", "\"Invalid input\"",         "Invalid input",           "'Invalid input'"},
                        {"[Options-1-6]", ")",                         "}",                       "]"},
                        {"[Options-1-7]", ";",                         ",",                       "."},

                        {"[Options-2-1]", "throw",                    "throws",                  "Throw"},
                        {"[Options-2-2]", "new",                      "New",                     "NEW"},
                        {"[Options-2-3]", "IllegalArgumentException",  "RuntimeException",        "Exception"},
                        {"[Options-2-4]", "(",                         "{",                       "["},
                        {"[Options-2-5]", "\"Invalid input\"",         "Invalid input",           "'Invalid input'"},
                        {"[Options-2-6]", ")",                         "}",                       "]"},
                        {"[Options-2-7]", ";",                         ",",                       "."},

                        {"[Answer-1]",
                                "throw new IllegalArgumentException(\"Invalid input\");"
                        }
                        {"[Answer-2]",
                                "throw new IllegalArgumentException(\"Invalid input\");"
                        }
                });
    }
}