
package jp.sunandsky.k.answerwithcolor.data;

public class QuestionLevel3 extends Question {
    private static final String TAG = Question.class.getSimpleName();

    private static final int mNumber = 2;

    private static final String[] stringMember = {
            Question.RED, Question.GREEN, Question.BLUE, Question.MAGENTA, Question.YELLOW,
            Question.CYAN, Question.WHITE
    };
    private static final String[] colorMember = {
            "red", "green", "blue", "magenta", "yellow", "cyan", "white"
    };

    public QuestionLevel3() {
        super(stringMember, colorMember);
        this.mLevel = 3;
    }

    public static int getNumber() {
        return mNumber;
    }
}
