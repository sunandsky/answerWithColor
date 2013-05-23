
package jp.sunandsky.k.answerwithcolor.activity;

import jp.sunandsky.k.answerwithcolor.R;
import jp.sunandsky.k.answerwithcolor.data.QuestionSeries;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TitleActivity extends Activity implements OnClickListener {
    private static final String TAG = "TitleActivity";

    private Button mButtonLevel1;
    private Button mButtonLevel2;
    private Button mButtonLevel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        initParts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.activity_title, menu);
        // return true;
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        resetParts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLevel3:
            case R.id.buttonLevel2:
            case R.id.buttonLevel1:
                v.setSelected(true);
                int level = 0;
                if (v.getId() == R.id.buttonLevel1)
                    level = QuestionSeries.LEVEL_1;
                else if (v.getId() == R.id.buttonLevel2)
                    level = QuestionSeries.LEVEL_2;
                else if (v.getId() == R.id.buttonLevel3)
                    level = QuestionSeries.LEVEL_3;
                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                intent.putExtra("IntentLevel", level);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initParts() {
        mButtonLevel1 = (Button) findViewById(R.id.buttonLevel1);
        mButtonLevel1.setOnClickListener(this);
        mButtonLevel2 = (Button) findViewById(R.id.buttonLevel2);
        mButtonLevel2.setOnClickListener(this);
        mButtonLevel3 = (Button) findViewById(R.id.buttonLevel3);
        mButtonLevel3.setOnClickListener(this);
    }

    private void resetParts() {
        mButtonLevel1.setSelected(false);
        mButtonLevel2.setSelected(false);
        mButtonLevel3.setSelected(false);
    }
}
