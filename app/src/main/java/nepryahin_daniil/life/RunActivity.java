package nepryahin_daniil.life;

/*
    D. Nepryahin
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class RunActivity extends AppCompatActivity{

    // String keys for Intent
    public static final String str_Extra_Rows = "rows";
    public static final String str_Extra_Columns = "columns";
    public static final String str_Extra_Cells = "cells";

    private GridView varLifeGrid;
    private LifeAdapter varAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        //get extras
        Bundle ExtrasFromMainact = getIntent().getExtras();
        int RowCount = ExtrasFromMainact.getInt(str_Extra_Rows);
        int ColumnCount = ExtrasFromMainact.getInt(str_Extra_Columns);
        int CellCount = ExtrasFromMainact.getInt(str_Extra_Cells);

        //options for GridView
        //some of them doesn't work in xml file
        varAdapter = new LifeAdapter(this, RowCount, ColumnCount, CellCount);
        varLifeGrid = (GridView)findViewById(R.id.grdv_Life);
        varLifeGrid.setAdapter(varAdapter);
        varLifeGrid.setNumColumns(ColumnCount);
        varLifeGrid.setEnabled(false);
        varLifeGrid.setStretchMode(GridView.NO_STRETCH);
        varLifeGrid.setPadding(1, 1, 1, 1);
        varLifeGrid.setColumnWidth(20);
        varLifeGrid.setHorizontalSpacing(1);
        varLifeGrid.setVerticalSpacing(1);

        //to update field
        Timer varTimer;
        varTimer = new Timer("LifeTimer");
        varTimer.scheduleAtFixedRate(new SendMessageTask(), 0, 500);
    }

    /*
        class for Timer
     */
    public class SendMessageTask extends TimerTask{
        @Override
        public void run() {
            Message message = new Message();
            RunActivity.this.updateGridHandler.sendMessage(message);
        }
    }
    /*
        update field
     */
    Handler updateGridHandler = new Handler()
    {
       public void handleMessage(Message message){
           varAdapter.NextGeneration();
           varLifeGrid.setAdapter(varAdapter);
           super.handleMessage(message);
       }
    };

    /*
        Go to main activity to repeat process
     */
    public void onbtnRepeatClick(View view) {
        Intent toMainActivity = new Intent();
        toMainActivity.setClass(RunActivity.this, MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }

    /*
        class for Grid
     */
    public class LifeAdapter extends BaseAdapter{
        private Context varContext;
        private LifeLogic varLifeLogic;

        public LifeAdapter(Context context, int rows, int columns, int cells){
            varContext = context;
            varLifeLogic = new LifeLogic(rows, columns, cells);
        }

        public void NextGeneration(){
            varLifeLogic.NextGeneration();
        }

        /*
            count of elements in grid
         */
        @Override
        public int getCount() {
            return varLifeLogic.getFieldSquare();
        }

        /*
            return object in position
         */
        @Override
        public Object getItem(int position){
            return varLifeLogic.isCellAlive(position);
        }

        /*
            return id of element in position
         */
        @Override
        public long getItemId(int position){
            return position;
        }

        /*
           return view on position
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView view;
            if (convertView == null){
                view = new ImageView(varContext);
                view.setLayoutParams(new GridView.LayoutParams(20, 20));
                view.setAdjustViewBounds(false);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setPadding(1,1,1,1);
            }
            else{
                view = (ImageView)convertView;
            }
            //show our cell
            view.setImageResource(varLifeLogic.isCellAlive(position) ? R.drawable.cell : R.drawable.empty);

            return view;
        }
    }
}
