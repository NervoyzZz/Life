package nepryahin_daniil.life;

/*
    D. Nepryahin
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ErrorDialogActivity extends Activity{
    //String key
    public static final String str_Extra_Error_Type = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_dialog);
        //get type of error
        Bundle ExtrasFromMainact = getIntent().getExtras();
        int ErrorType = ExtrasFromMainact.getInt(str_Extra_Error_Type);
        //set text of TextView
        final TextView Err_Message = (TextView)findViewById(R.id.txtv_Error_Dialog);
        switch (ErrorType){
            case MainActivity.Error_Rows:
                Err_Message.setText(getString(R.string.Error_Rows));
                break;
            case MainActivity.Error_Column:
                Err_Message.setText(getString(R.string.Error_Columns));
                break;
            case MainActivity.Error_Cell:
                Err_Message.setText(getString(R.string.Error_Cells));
                break;
            default:
                break;
        }

    }

    //finish this activity
    public void onbtnOkClick_dialogact(View view) {
        finish();
    }
}
