
package jp.sunandsky.k.answerwithcolor.data;

public class QuestionLevel1 extends Question {
    private static final String TAG = Question.class.getSimpleName();

    private static final int mNumber = 2;

    private static final String[] stringMember = {
            Question.RED, Question.GREEN, Question.BLUE
    };
    private static final String[] colorMember = {
            "red", "green", "blue"
    };

    public QuestionLevel1() {
        super(stringMember, colorMember);
        this.mLevel = 1;
        this.mShuffle = false;
    }

    public static int getNumber() {
        return mNumber;
    }
}
