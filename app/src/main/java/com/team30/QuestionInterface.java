package com.team30.syntaxrunner;

import java.util.List;

/**
 * Represents a parsed question set in Syntax Runner.
 * A question set may contain annotation lines and multiple sub-questions.
 */
public interface QuestionInterface
{
    /**
     * Returns the number of parsed sub-questions in this question set.
     *
     * @return the number of sub-questions
     */
    int getQuestionCount();

    /**
     * Returns all annotation lines stored in this question set.
     * If no annotation exists, an empty list is returned.
     *
     * @return a list of annotation lines
     */
    List<String> getAnnotationLines();

    /**
     * Returns the sub-question with the given question number.
     * For example, question number 1 refers to [Question-1].
     *
     * @param questionNumber the question number to find
     * @return the matching sub-question, or null if not found
     */
    SubQuestion getSubQuestion(int questionNumber);

    /**
     * Returns all parsed sub-questions in this question set.
     *
     * @return a list of all sub-questions
     */
    List<SubQuestion> getAllSubQuestions();
}
