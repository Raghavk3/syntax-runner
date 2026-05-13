package com.team30.syntaxrunner;

public class BankValidatorRunner {
    public static void main(String[] args) {
        BankValidator.validate("Level1", new Level1QuestionBank());
        BankValidator.validate("Level2", new Level2QuestionBank());
        BankValidator.validate("Level3", new Level3QuestionBank());
        BankValidator.validate("Level4", new Level4QuestionBank());
        BankValidator.validate("Level5", new Level5QuestionBank());
        BankValidator.validate("Level6", new Level6QuestionBank());
    }
}
