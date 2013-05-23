
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLevel3:
            case R.id.buttonLevel2:
            case R.id.buttonLevel1:
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
        Button buttonLevel1 = (Button) findViewById(R.id.buttonLevel1);
        buttonLevel1.setOnClickListener(this);
        Button buttonLevel2 = (Button) findViewById(R.id.buttonLevel2);
        buttonLevel2.setOnClickListener(this);
        Button buttonLevel3 = (Button) findViewById(R.id.buttonLevel3);
        buttonLevel3.setOnClickListener(this);
    }
}
