
package jp.sunandsky.k.answerwithcolor.activity;

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

    private LinearLayout mImageCampus;
    private TextView mTextCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setParts();
        mHandler = new Handler();

        initQuestion();
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

    private void setParts() {
        ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button1.setTag(Integer.valueOf(Color.parseColor("red")));
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button2.setTag(Integer.valueOf(Color.parseColor("green")));
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button3.setTag(Integer.valueOf(Color.parseColor("blue")));
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button4.setVisibility(View.GONE);
        ImageButton button5 = (ImageButton) findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button5.setVisibility(View.GONE);

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
        hideResult();
        initCampus();
        initCounter();
        mQuestionSeries = new QuestionSeries(0);
        setNextQuestion();
        mHandler.post(mCounterTask);
    }

    private void setNextQuestion() {
        mQuestionSeries.addNewQuestion();
        if (mQuestionSeries.isFirstQuestion()) {
            mStartTime = System.currentTimeMillis();
            initCounter();
        }
        TextView textQuestion = (TextView) findViewById(R.id.textQuestion);
        textQuestion.setText(mQuestionSeries.getCurrentQuestion().getStringQuestion());
        textQuestion.setTextColor(mQuestionSeries.getCurrentQuestion().getColorQuestion());
    }

    private void showFinishedDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("けっか");
        alertDialogBuilder.setMessage(getElapsedTimeFormat(mElapsedTime));
        alertDialogBuilder.setPositiveButton("もういっかい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initQuestion();
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
