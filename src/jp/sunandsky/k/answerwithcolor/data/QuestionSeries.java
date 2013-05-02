
package jp.sunandsky.k.answerwithcolor.data;

import java.util.ArrayList;

public class QuestionSeries {
    private static final String TAG = QuestionSeries.class.getSimpleName();

    private int mQuestionNumber;
    private int mLevel;
    private boolean finished;
    private ArrayList<Question> arrayQuestion = new ArrayList<Question>();

    public QuestionSeries(int level) {
        this.mLevel = level;
        switch (level) {
            case 0:
                mQuestionNumber = 2;
                break;
            default:
                mQuestionNumber = 10;
                break;
        }
    }

    public void addNewQuestion() {
        Question newQuestion = new Question();
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

    public boolean isFinalQuestion() {
        if (arrayQuestion.size() > mQuestionNumber) {
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
