
package jp.sunandsky.k.answerwithcolor.data;

import java.util.Random;

import android.graphics.Color;

public class Question {
    private static final String TAG = Question.class.getSimpleName();

    private static final int stringIdNum = 3;
    private static final int colorIdNum = 3;
    private static final String[] stringMember = {
            "あか", "みどり", "あお"
    };
    private static final String[] colorMember = {
            "red", "green", "blue"
    };

    private String stringQuestion;
    private int colorQuestion;
    private int correctAnswer;
    private int playerAnswer;

    public Question() {
        this.stringQuestion = stringMember[makeQuestionStringId(stringMember.length)];
        this.colorQuestion = Color.parseColor(colorMember[makeQuestionColorId(colorMember.length)]);
        this.correctAnswer = makeCorrectAnswer(this.stringQuestion);
    }

    private static int makeQuestionStringId(int number) {
        Random randam = new Random();
        return randam.nextInt(number);
    }

    private static int makeQuestionColorId(int number) {
        Random randam = new Random();
        return randam.nextInt(number);
    }

    private static int makeCorrectAnswer(String question) {
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
