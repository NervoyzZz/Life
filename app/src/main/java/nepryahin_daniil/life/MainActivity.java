package nepryahin_daniil.life;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/*
    D. Nepryahin
    Realization of Life
    This app's based on the article http://megadarja.blogspot.ru/2008/10/android-1-android.html
 */

public class MainActivity extends AppCompatActivity {

    //variables for error check
    public static final int Error_Non = 0;
    public static final int Error_Rows = 1;
    public static final int Error_Column = 2;
    public static final int Error_Cell = 3;
    //correct values
    private static final int Cor_Row_Min = 5;
    private static final int Cor_Row_Max = 25;
    private static final int Cor_Column_Min = 5;
    private static final int Cor_Column_Max = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        creation menu (button on the ActionBar)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onbtnRunClick(View view) {
        // get id of EditTexts
        EditText edtxtRow = (EditText)findViewById(R.id.edtxt_RowCount);
        EditText edtxtColumn = (EditText)findViewById(R.id.edtxt_ColumnCount);
        EditText edtxtCell = (EditText)findViewById(R.id.edtxt_CellCount);
        //get values in EditTexts
        int RowCount = Integer.parseInt(edtxtRow.getText().toString());
        int ColumnCount = Integer.parseInt(edtxtColumn.getText().toString());
        int CellCount = Integer.parseInt(edtxtCell.getText().toString());
        //error code
        int Error_Code = CheckInput(RowCount, ColumnCount, CellCount);
        //tell about error
        if (Error_Code != Error_Non){
            Intent toDialogError = new Intent();
            toDialogError.setClass(MainActivity.this, ErrorDialogActivity.class);
            toDialogError.putExtra(ErrorDialogActivity.str_Extra_Error_Type, Error_Code);
            startActivity(toDialogError);
        }
        else {
            //go to RunActivity with values
            Intent toRunActivity = new Intent();
            toRunActivity.setClass(MainActivity.this, RunActivity.class);
            toRunActivity.putExtra(RunActivity.str_Extra_Rows, RowCount);
            toRunActivity.putExtra(RunActivity.str_Extra_Columns, ColumnCount);
            toRunActivity.putExtra(RunActivity.str_Extra_Cells, CellCount);
            startActivity(toRunActivity);
            finish();
        }
    }

    /*
            Check input
         */
    private int CheckInput(int rows, int columns, int cells){

        if (rows < Cor_Row_Min || rows > Cor_Row_Max) {
            return Error_Rows;
        }
        else{
            if (columns < Cor_Column_Min || columns > Cor_Column_Max){
                return Error_Column;
            }
            else {
                if (cells > rows * columns){
                    return Error_Cell;
                }
                else {
                    return Error_Non;
                }
            }
        }
    }

    /*
        Listener for button on the ActionBar
     */
   public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == R.id.menuInfobtn){
           Intent toInfoActivity = new Intent();
           toInfoActivity.setClass(MainActivity.this, InfoDialogActivity.class);
           startActivity(toInfoActivity);
           return true;
       }
       else{
           return super.onOptionsItemSelected(item);
       }
   }

}
