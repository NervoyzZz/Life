package nepryahin_daniil.life;

/*
    D. Nepryahin
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class InfoDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dialog);
    }

    //finish activity
    public void onbtnOkClick_dialogact(View view) {
        finish();
    }
}
