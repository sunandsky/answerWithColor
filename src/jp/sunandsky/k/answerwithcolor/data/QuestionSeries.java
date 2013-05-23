
package jp.sunandsky.k.answerwithcolor.data;

import java.util.ArrayList;

public class QuestionSeries {
    private static final String TAG = QuestionSeries.class.getSimpleName();
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;

    protected int mLevel;
    protected int mQuestionNumber;

    private boolean finished;
    private ArrayList<Question> arrayQuestion = new ArrayList<Question>();

    public QuestionSeries(int level) {
        this.mLevel = level;
        this.mQuestionNumber = getQuestionNumber(level);
    }

    private int getQuestionNumber(int level) {
        switch (this.mLevel) {
            case 1:
                return QuestionLevel1.getNumber();
            case 2:
                return QuestionLevel2.getNumber();
            case 3:
            default:
                return QuestionLevel3.getNumber();
        }
    }

    public void addNewQuestion() {
        Question newQuestion;
        switch (this.mLevel) {
            case 1:
                newQuestion = new QuestionLevel1();
                break;
            case 2:
                newQuestion = new QuestionLevel2();
                break;
            case 3:
            default:
                newQuestion = new QuestionLevel3();
                break;
        }
        arrayQuestion.add(newQuestion);
    }

    public void addQuestion(Question newQuestion) {

    }

    public Question getCurrentQuestion() {
        if (arrayQuestion.size() <= 0) {
            return null;
        }
        return arrayQuestion.get(arrayQuestion.size() - 1);
    }

    public boolean isFirstQuestion() {
        if (arrayQuestion.size() == 1) {
            return true;
        }
        return false;
    }

    public boolean isFinalQuestion() {
        if (arrayQuestion.size() >= mQuestionNumber) {
            return true;
        }
        return false;
    }

    public final boolean isFinished() {
        return finished;
    }

    public final void setFinished(boolean finished) {
        this.finished = finished;
    }
}
