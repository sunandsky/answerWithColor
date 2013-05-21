
package jp.sunandsky.k.answerwithcolor.activity;

import java.util.ArrayList;

import jp.sunandsky.k.answerwithcolor.R;
import jp.sunandsky.k.answerwithcolor.data.QuestionSeries;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int DURATION_BETWEEN_QUESTIONS = 300;

    private Context mContext = this;
    private Handler mHandler;
    private int mSelectedLevel = QuestionSeries.LEVEL_3;
    private QuestionSeries mQuestionSeries;
    private Runnable mNextQuestionTask = new Runnable() {
        @Override
        public void run() {
            hideResult();
            initCampus();
            if (mQuestionSeries.getCurrentQuestion().correctOrNot()) {
                setNextQuestion();
            }
        }
    };
    private long mStartTime;
    private long mEndTime;
    private long mElapsedTime;
    private Runnable mCounterTask = new Runnable() {
        @Override
        public void run() {
            mEndTime = System.currentTimeMillis();
            mElapsedTime = mEndTime - mStartTime;
            mTextCounter.setText(getElapsedTimeFormat(mElapsedTime));
            if (mQuestionSeries.isFinished()) {
                showFinishedDialog();
            }
            else {
                mHandler.postDelayed(mCounterTask, 10);
            }
        }
    };

    TextView mTextQuestion;
    private ArrayList<ImageButton> mArrayPallet = new ArrayList<ImageButton>();
    private ImageButton mButton1;
    private ImageButton mButton2;
    private ImageButton mButton3;
    private ImageButton mButton4;
    private ImageButton mButton5;
    private LinearLayout mImageCampus;
    private TextView mTextCounter;
    private static final String[] colorPallet = {
            "red", "green", "blue"
    };
    private static final String[] colorPalletDefault = {
            "red", "green", "blue"
    };
    private static final int[] resourcePalletDefault = {
            R.drawable.red, R.drawable.green, R.drawable.blue
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParts();
        mHandler = new Handler();

        initQuestion();
        showStartDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
                paintCampus(((Integer) v.getTag()).intValue());
                break;
            case R.id.buttonEraser:
                initCampus();
                break;
            case R.id.buttonPaint:
                if (mQuestionSeries.getCurrentQuestion().checkPlayerAnswer(
                        ((Integer) (mImageCampus.getTag())).intValue())) {
                    showResultAndNextQuestion(true);
                }
                else {
                    showResultAndNextQuestion(false);
                }
                break;
            default:
                break;
        }
    }

    private void paintCampus(int color) {
        int colorCampus = ((Integer) (mImageCampus.getTag())).intValue();
        if (colorCampus == Color.parseColor("white")) {
            colorCampus = color;
        }
        else {
            colorCampus |= color;
        }
        mImageCampus.setTag(Integer.valueOf(colorCampus));
        mImageCampus.setBackgroundColor(colorCampus);
    }

    private void initParts() {
        mTextQuestion = (TextView) findViewById(R.id.textQuestion);

        mButton1 = (ImageButton) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mArrayPallet.add(mButton1);
        mButton2 = (ImageButton) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
        mArrayPallet.add(mButton2);
        mButton3 = (ImageButton) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);
        mArrayPallet.add(mButton3);

        mButton4 = (ImageButton) findViewById(R.id.button4);
        mButton4.setOnClickListener(this);
        mButton5 = (ImageButton) findViewById(R.id.button5);
        mButton5.setOnClickListener(this);

        ImageButton buttonEraser = (ImageButton) findViewById(R.id.buttonEraser);
        buttonEraser.setOnClickListener(this);
        ImageButton buttonPaint = (ImageButton) findViewById(R.id.buttonPaint);
        buttonPaint.setOnClickListener(this);

        mImageCampus = (LinearLayout) findViewById(R.id.linearLayoutCampus);
        initCampus();
        mTextCounter = (TextView) findViewById(R.id.textCounter);
    }

    private String getElapsedTimeFormat(long time) {
        long min = time / 1000 / 60;
        long sec = time / 1000 % 60;
        long mSec = (time % 1000) / 10;
        return String.format("%02d'%02d''%02d", min, sec, mSec);
    }

    private void showResult(boolean result) {
        ImageView imageResult;
        imageResult = (ImageView) findViewById(R.id.imageResult);
        if (result == true) {
            imageResult.setImageResource(R.drawable.good);
        }
        else {
            imageResult.setImageResource(R.drawable.bad);
        }
        imageResult.setVisibility(View.VISIBLE);
    }

    private void hideResult() {
        ImageView imageResult;
        imageResult = (ImageView) findViewById(R.id.imageResult);
        imageResult.setVisibility(View.GONE);
    }

    private void setNextQuestionTimer() {
        mHandler.postDelayed(mNextQuestionTask, DURATION_BETWEEN_QUESTIONS);
    }

    private void showResultAndNextQuestion(boolean result) {
        showResult(result);
        if (result && mQuestionSeries.isFinalQuestion()) {
            mQuestionSeries.setFinished(true);
        }
        else {
            setNextQuestionTimer();
        }
    }

    private void initCampus() {
        mImageCampus.setTag(Color.parseColor("white"));
        mImageCampus.setBackgroundColor(Color.parseColor("white"));
    }

    private void initCounter() {
        mElapsedTime = 0;
        mTextCounter.setText(getElapsedTimeFormat(0));
    }

    private void initQuestion() {
        mTextQuestion.setText("");

        hideResult();
        initCampus();
        initCounter();
    }

    private void setQuestion() {
        mQuestionSeries = new QuestionSeries(this.mSelectedLevel);
        setNextQuestion();
        showPallet();
        mHandler.post(mCounterTask);
    }

    private void setNextQuestion() {
        mQuestionSeries.addNewQuestion();
        if (mQuestionSeries.isFirstQuestion()) {
            mStartTime = System.currentTimeMillis();
            initCounter();
        }
        mTextQuestion.setText(mQuestionSeries.getCurrentQuestion().getStringQuestion());
        mTextQuestion.setTextColor(mQuestionSeries.getCurrentQuestion().getColorQuestion());
        updatePallet();
    }

    private void showPallet() {
        for (ImageButton button : mArrayPallet) {
            button.setVisibility(View.VISIBLE);
        }
    }

    private void updatePallet() {
        if (mQuestionSeries.getCurrentQuestion().mShuffle) {
            shufflePallet();
        }
        else {
            if (mQuestionSeries.isFirstQuestion()) {
                fixPallet();
            }
        }
    }

    private int getButtonResource(ImageButton button, String color) {
        int i = 0;
        for (int resource : resourcePalletDefault) {
            if (color.equals(colorPalletDefault[i])) {
                return resource;
            }
            i++;
        }
        return 0;
    }

    private void fixPallet() {
        setPallet(colorPalletDefault);
    }

    private static <T> void shuffle(T[] array) {
        for (int i = 0; i < array.length; i++) {
            int dst = (int) Math.floor(Math.random() * (i + 1));
            swap(array, i, dst);
        }
    }

    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void setPallet(String[] colorArray) {
        int i = 0;
        for (ImageButton button : mArrayPallet) {
            button.setTag(Integer.valueOf(Color.parseColor(colorArray[i])));
            button.setBackgroundResource(getButtonResource(button, colorArray[i]));
            i++;
        }
    }

    private void shufflePallet() {
        shuffle(colorPallet);
        setPallet(colorPallet);
    }

    private void showStartDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("よーい");
        alertDialogBuilder.setMessage("れべる " + this.mSelectedLevel);
        alertDialogBuilder.setPositiveButton("すたーと", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setQuestion();
            }
        });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showFinishedDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("けっか");
        alertDialogBuilder.setMessage(getElapsedTimeFormat(mElapsedTime));
        alertDialogBuilder.setPositiveButton("もういっかい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initQuestion();
                showStartDialog();
            }
        });
        alertDialogBuilder.setNegativeButton("やめる", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
