
package jp.sunandsky.k.answerwithcolor.data;

public class QuestionLevel2 extends Question {
    private static final String TAG = Question.class.getSimpleName();

    private static final int mNumber = 2;

    private static final String[] stringMember = {
            Question.RED, Question.GREEN, Question.BLUE, Question.MAGENTA, Question.YELLOW,
            Question.CYAN
    };
    private static final String[] colorMember = {
            "red", "green", "blue", "magenta", "yellow", "cyan"
    };

    public QuestionLevel2() {
        super(stringMember, colorMember);
        this.mLevel = 2;
    }

    public static int getNumber() {
        return mNumber;
    }
}