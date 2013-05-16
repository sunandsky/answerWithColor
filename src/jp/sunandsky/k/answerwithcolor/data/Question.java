
package jp.sunandsky.k.answerwithcolor.data;

import java.util.Random;

import android.graphics.Color;

public class Question {
    private static final String TAG = Question.class.getSimpleName();

    protected static final String RED = "あか";
    protected static final String GREEN = "みどり";
    protected static final String BLUE = "あお";
    protected static final String MAGENTA = "まじぇんだ";
    protected static final String YELLOW = "きいろ";
    protected static final String CYAN = "しあん";
    protected static final String WHITE = "しろ";

    protected int mLevel;
    protected int mNumber;

    private String stringQuestion;
    private int colorQuestion;
    private int correctAnswer;
    private int playerAnswer;

    public Question(String[] stringMember, String[] colorMember) {
        this.stringQuestion = stringMember[makeQuestionStringId(stringMember.length)];
        this.colorQuestion = Color.parseColor(colorMember[makeQuestionColorId(colorMember.length)]);
        this.correctAnswer = makeCorrectAnswer(this.stringQuestion, stringMember, colorMember);
    }

    private static int makeQuestionStringId(int number) {
        Random randam = new Random();
        return randam.nextInt(number);
    }

    private static int makeQuestionColorId(int number) {
        Random randam = new Random();
        return randam.nextInt(number);
    }

    private static int makeCorrectAnswer(String question, String[] stringMember,
            String[] colorMember) {
        int i = 0;
        for (String str : stringMember) {
            if (str.equals(question)) {
                return Color.parseColor(colorMember[i]);
            }
            i++;
        }
        return -1;
    }

    public final String getStringQuestion() {
        return stringQuestion;
    }

    public final int getColorQuestion() {
        return colorQuestion;
    }

    public boolean correctOrNot() {
        if (this.correctAnswer == this.playerAnswer) {
            return true;
        }
        return false;
    }

    public boolean checkPlayerAnswer(int color) {
        this.playerAnswer = color;
        return correctOrNot();
    }
}
