package mhgr.no.sudoku;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SudokuActivity extends ActionBarActivity {

    private final int BOARD_SIZE = 9;
    private GridView gameBoard;
    private TextView viewInFocus;
    private int positionInFocus = -1;
    private static SudokuCell[] content;

    private String seedPuzzle =
              "978312645"
            + "312645978"
            + "645978312"
            + "789123456"
            + "123456789"
            + "456789123"
            + "897231564"
            + "231564897"
            + "564897231";

    private String startBoardSimple =
              "080020673"
            + "307105200"
            + "024073010"
            + "602010408"
            + "108006052"
            + "700842090"
            + "410208007"
            + "050760900"
            + "076400581";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        genContent();
        gameBoard = (GridView) findViewById(R.id.sudokuGrid);

        GridAdapter adapter = new GridAdapter(this,
                R.layout.grid_cell_layout, content);
        gameBoard.setAdapter(adapter);

        gameBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
               //         Toast.LENGTH_SHORT).show();
                if (viewInFocus != null) {
                    viewInFocus.setBackgroundColor(getResources().getColor(R.color.abc_background_cache_hint_selector_material_light));
                }
                viewInFocus = (TextView) view;
                positionInFocus = position;
                //((TextView) view).setText("A");
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }
        });
    }

    private void genContent() {
 /*       for (int i = 0; i < content.length; i++ ) {
            content[i] = (i % 9)+1 + "";
        }*/
        content = fromPuzzleString(startBoardSimple);
    }

    /** Convert a puzzle string into an array */
    static protected SudokuCell[] fromPuzzleString(String string) {
        SudokuCell[] puz = new SudokuCell[string.length()];
        for (int i = 0; i < puz.length; i++) {
            //String value = Character.toString(string.charAt(i));
            int value = string.charAt(i) - '0';
            boolean isInitial = true;
            if (value == 0) {
                //value = "";
                isInitial = false;
            }
            puz[i] = new SudokuCell(value, isInitial);
        }
        return puz;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sudoku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setValueInSelectedView(int value) {
        if (positionInFocus != -1) {
            content[positionInFocus].setValue(value);
            /*if (value == 0) {
                content[positionInFocus].setValue("");
            } else {
                content[positionInFocus].setValue(String.valueOf(value));
            }*/
            ((ArrayAdapter) gameBoard.getAdapter()).notifyDataSetChanged();
        }
    }

    public void setValue1(View v) {
        setValueInSelectedView(1);
    }
    public void setValue2(View v) {
        setValueInSelectedView(2);
    }
    public void setValue3(View v) {
        setValueInSelectedView(3);
    }
    public void setValue4(View v) {
        setValueInSelectedView(4);
    }
    public void setValue5(View v) {
        setValueInSelectedView(5);
    }
    public void setValue6(View v) {
        setValueInSelectedView(6);
    }
    public void setValue7(View v) {
        setValueInSelectedView(7);
    }
    public void setValue8(View v) {
        setValueInSelectedView(8);
    }
    public void setValue9(View v) {
        setValueInSelectedView(9);
    }
    public void setValueClear(View v) {
        setValueInSelectedView(0);
    }

    public void checkSolution(View v) {
        boolean solved = true;

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!traverseHorizontal(i) || !traverseVertical(i) || !traverseSquareGroups(i)) {
                solved = false;
                break;
            }
        }
        if (solved) {
            Toast.makeText(getApplicationContext(), "You did it! Congrats!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Nope... Not correct.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean traverseHorizontal(int line) {
        int startPoint;
        if (line == 0) {
            startPoint = 0;
        } else {
            startPoint= line*BOARD_SIZE;
        }
        int endPoint = startPoint+(BOARD_SIZE);
        Set<SudokuCell> hElements = new HashSet<>();
        hElements.addAll(Arrays.asList(content).subList(startPoint, endPoint));

        return !(hElements.contains(new SudokuCell(0, false)) || hElements.size() != BOARD_SIZE);
    }

    private boolean traverseVertical(int column) {

        Set<SudokuCell> vElements = new HashSet<>();
        for (int i = column; i < content.length; i += BOARD_SIZE) {
            vElements.add(content[i]);
        }
        return !(vElements.contains(new SudokuCell(0, false)) || vElements.size() != BOARD_SIZE);
    }

    private boolean traverseSquareGroups(int group) {
        Set<SudokuCell> gElements = new HashSet<>();
        int groupIndex = ((((9*group)/(27))*27)+((group%3)*3));
        //System.out.println(groupIndex);

        gElements.add(content[groupIndex]);
        gElements.add(content[groupIndex+1]);
        gElements.add(content[groupIndex+2]);

        gElements.add(content[(groupIndex)+BOARD_SIZE]);
        gElements.add(content[(groupIndex)+BOARD_SIZE+1]);
        gElements.add(content[(groupIndex)+BOARD_SIZE+2]);

        gElements.add(content[(groupIndex)+(BOARD_SIZE*2)]);
        gElements.add(content[(groupIndex)+(BOARD_SIZE*2)+1]);
        gElements.add(content[(groupIndex)+(BOARD_SIZE*2)+2]);

        return !(gElements.contains(new SudokuCell(0, false)) || gElements.size() != BOARD_SIZE);
    }
}
