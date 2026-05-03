package com.team30.syntaxrunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Parses and stores one raw question set returned by a QuestionBank.
 * A raw question set is a String[][]
 * that may contain:
 * [Annotation],
 * [Question-x],
 * [Options-x-y],
 * and [Answer-x] rows.
 */
public class Question implements QuestionInterface
{
    /** Stores all annotation lines of this question set. */
    private final ArrayList<String> annotationLines;

    /** Stores all parsed sub-questions in insertion order. */
    private final ArrayList<SubQuestion> subQuestions;

    /** Maps a question number to its corresponding SubQuestion object. */
    private final HashMap<Integer, SubQuestion> subQuestionMap;

    /** Counts how many sub-questions have been successfully parsed. */
    private int questionCount;

    /**
     * Creates a Question object from a raw String[][] question set.
     * If the given raw question is null, this object remains empty.
     *
     * @param rawQuestion the raw question set to parse
     */
    public Question(String[][] rawQuestion)
    {
        annotationLines = new ArrayList<>();
        subQuestions = new ArrayList<>();
        subQuestionMap = new HashMap<>();
        questionCount = 0;

        if (rawQuestion != null)
        {
            parseRawQuestion(rawQuestion);
        }
    }

    /**
     * Parses the whole raw question set.
     * This method first parses annotation and question rows,
     * then parses option and answer rows, so they can be assigned
     * to already-created sub-question objects.
     *
     * @param rawQuestion the raw question set to parse
     */
    private void parseRawQuestion(String[][] rawQuestion)
    {
        for (String[] row : rawQuestion)
        {
            if (row == null || row.length == 0)
            {
                continue;
            }// Defensive programming -- Etienne Gu

            String label = row[0];

            if ("[Annotation]".equals(label))
            {
                parseAnnotationRow(row);
            }
            else if (label.startsWith("[Question-"))
            {
                parseQuestionRow(row);
            }
        }

        for (String[] row : rawQuestion)
        {
            if (row == null || row.length == 0)
            {
                continue;
            }

            String label = row[0];

            if (label.startsWith("[Options-"))
            {
                parseOptionsRow(row);
            }
            else if (label.startsWith("[Answer-"))
            {
                parseAnswerRow(row);
            }
        }
    }

    /**
     * Parses one annotation row and stores all annotation lines.
     * The first element is the label [Annotation], so actual content
     * starts from index 1.
     *
     * @param row the annotation row to parse
     */
    private void parseAnnotationRow(String[] row)
    {
        for (int i = 1; i < row.length; i++)
        {
            annotationLines.add(row[i]);
        }
    }

    /**
     * Parses one [Question-x] row, creates a SubQuestion object,
     * stores its tokens, and updates the question counter.
     *
     * @param row the question row to parse
     */
    private void parseQuestionRow(String[] row)
    {
        int questionNumber = extractSingleNumber(row[0], "[Question-", "]");
        if (questionNumber == -1)
        {
            return;
        }

        SubQuestion subQuestion = new SubQuestion(questionNumber);
        String[] tokens = new String[row.length - 1];

        for (int i = 1; i < row.length; i++)
        {
            tokens[i - 1] = row[i];
        }

        subQuestion.setTokens(tokens);
        subQuestions.add(subQuestion);
        subQuestionMap.put(questionNumber, subQuestion);
        questionCount++;
    }

    /**
     * Parses one [Options-x-y] row and stores its options
     * into the correct SubQuestion and token position.
     *
     * @param row the options row to parse
     */
    private void parseOptionsRow(String[] row)
    {
        int[] numbers = extractTwoNumbers(row[0], "[Options-", "]");
        if (numbers == null)
        {
            return;
        }

        int questionNumber = numbers[0];
        int tokenNumber = numbers[1];

        SubQuestion subQuestion = subQuestionMap.get(questionNumber);
        if (subQuestion == null)
        {
            return;
        }

        String[] options = new String[row.length - 1];
        for (int i = 1; i < row.length; i++)
        {
            options[i - 1] = row[i];
        }

        subQuestion.setTokenOptions(tokenNumber - 1, options);
    }

    /**
     * Parses one [Answer-x] row and stores its accepted answers
     * into the correct SubQuestion.
     *
     * @param row the answer row to parse
     */
    private void parseAnswerRow(String[] row)
    {
        int questionNumber = extractSingleNumber(row[0], "[Answer-", "]");
        if (questionNumber == -1)
        {
            return;
        }

        SubQuestion subQuestion = subQuestionMap.get(questionNumber);
        if (subQuestion == null)
        {
            return;
        }

        String[] answers = new String[row.length - 1];
        for (int i = 1; i < row.length; i++)
        {
            answers[i - 1] = row[i];
        }

        subQuestion.setAnswers(answers);
    }

    /**
     * Extracts one integer from a label such as [Question-2] or [Answer-1].
     * If the label format is invalid, -1 is returned.
     *
     * @param text the label text to examine
     * @param prefix the expected label prefix
     * @param suffix the expected label suffix
     * @return the extracted number, or -1 if parsing fails
     */
    private int extractSingleNumber(String text, String prefix, String suffix)
    {
        if (!text.startsWith(prefix) || !text.endsWith(suffix))
        {
            return -1;
        }

        String middle = text.substring(prefix.length(), text.length() - suffix.length());

        try
        {
            return Integer.parseInt(middle);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    /**
     * Extracts two integers from a label such as [Options-2-17].
     * If the label format is invalid, null is returned.
     *
     * @param text the label text to examine
     * @param prefix the expected label prefix
     * @param suffix the expected label suffix
     * @return an int array containing the two extracted numbers, or null if parsing fails
     */
    private int[] extractTwoNumbers(String text, String prefix, String suffix)
    {
        if (!text.startsWith(prefix) || !text.endsWith(suffix))
        {
            return null;
        }

        String middle = text.substring(prefix.length(), text.length() - suffix.length());
        String[] parts = middle.split("-");

        if (parts.length != 2)
        {
            return null;
        }

        try
        {
            int first = Integer.parseInt(parts[0]);
            int second = Integer.parseInt(parts[1]);
            return new int[]{first, second};
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Returns the number of parsed sub-questions in this question set.
     *
     * @return the number of sub-questions
     */
    @Override
    public int getQuestionCount()
    {
        return questionCount;
    }

    /**
     * Returns all annotation lines in this question set.
     * If there is no annotation, an empty list is returned.
     *
     * @return the annotation line list
     */
    @Override
    public List<String> getAnnotationLines()
    {
        return annotationLines;
    }

    /**
     * Returns the SubQuestion object with the given question number.
     *
     * @param questionNumber the question number to find
     * @return the matching SubQuestion, or null if not found
     */
    @Override
    public SubQuestion getSubQuestion(int questionNumber)
    {
        return subQuestionMap.get(questionNumber);
    }

    /**
     * Returns all parsed sub-questions in this question set.
     *
     * @return the sub-question list
     */
    @Override
    public List<SubQuestion> getAllSubQuestions()
    {
        return subQuestions;
    }
}
