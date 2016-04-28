package nepryahin_daniil.life;

/*
    D. Nepryahin
 */

import android.util.Log;

import java.util.Random;

public class LifeLogic {
    //some useful consts
    private static final int CellIsLiving = 1;
    private static final int CellIsDead = 0;
    private static final int NeighboursMinCount = 2;        //min neighbours to live
    private static final int NeighboursMaxCount = 3;        //max neighbors to live
    private static final int NeighboursBornCount = 3;       // neighbours to new cell

    //some useful variables
    private static int RowCount;        //count of rows
    private static int ColumnCount;     //count of columns
    private int [][] ArrCells;          //array of cells (dead or alive)

    /*
        constructor of the class
     */
    public LifeLogic(int row, int column, int cellNumber){
        RowCount = row;
        ColumnCount = column;
        ArrCells = new int[RowCount][ColumnCount];
        InitCellValues(cellNumber);
    }

    /*
        initializing our cells for the first time
     */
    private void InitCellValues(int cellNumber){
        //loop variables
        int i, j;
        for (i = 0; i < RowCount; ++i){
            for (j = 0; j < ColumnCount; ++j){
                ArrCells[i][j] = CellIsDead;
            }
        }

        Random rand = new Random(System.currentTimeMillis());
        for (i = 0; i < cellNumber; ++i){
            int cRow;
            int cColumn;

            do {
                cRow = rand.nextInt(RowCount);
                cColumn = rand.nextInt(ColumnCount);
            }
            while (isCellAlive(cRow, cColumn));
            ArrCells[cRow][cColumn] = CellIsLiving;
        }
    }

    /*
        Is cell living?
     */
    public boolean isCellAlive(int row, int column){
        return CellAt(row, column) == CellIsLiving;
    }
    /*
        one more time
        position = row * ColumnsCount + column
     */
    public boolean isCellAlive(int position){
        int row = position / ColumnCount;
        int colummn = position % ColumnCount;

        return isCellAlive(row, colummn);
    }

    /*
        return cell at position [row][column]
     */
    private int CellAt(int row, int column){
        // if row and column are correct
        if (row < 0 || row >= RowCount || column < 0 || column >= ColumnCount){
            return 0;
        } else {
            return ArrCells[row][column];
        }
    }

    /*
        next generation
     */
    public void NextGeneration(){
        //without try-block app crashes sometimes
        try {
            int [][] tmpArr = new int[RowCount][ColumnCount];
            int i, j;
            for (i = 0; i < RowCount; ++i) {
                for (j = 0; j < ColumnCount; ++j) {
                    //compute neighbours count
                    int NeighbourCount = CellAt(i - 1, j - 1) + CellAt(i - 1, j) +
                            CellAt(i, j - 1) + CellAt(i + 1, j + 1) +
                            CellAt(i + 1, j) + CellAt(i, j + 1) +
                            CellAt(i - 1, j + 1) + CellAt(i + 1, j - 1);
                    tmpArr[i][j] = ArrCells[i][j];
                    //condition to kill cell
                    if (isCellAlive(i, j)) {
                        if (NeighbourCount < NeighboursMinCount || NeighbourCount > NeighboursMaxCount) {
                            tmpArr[i][j] = CellIsDead;
                        }
                    } else {
                        //condition to born cell
                        if (NeighbourCount == NeighboursBornCount) {
                            tmpArr[i][j] = CellIsLiving;
                        }
                    }
                }
            }
            ArrCells = tmpArr;
        }
        catch (Exception excptn){
            Log.e(excptn.toString(), excptn.getLocalizedMessage());
        }
    }

    /*
        return square of field
     */
    public int getFieldSquare(){
        return RowCount * ColumnCount;
    }
}
