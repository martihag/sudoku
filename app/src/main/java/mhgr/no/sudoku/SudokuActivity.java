package mhgr.no.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
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
    private DBAdapter db;

    private String board = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        board = getIntent().getStringExtra("sudoku_board");
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
        content = fromPuzzleString(board);
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

    private String toPuzzleString() {
        StringBuilder buf = new StringBuilder();
        for (SudokuCell cell : content) {
            buf.append(cell.getValue());
        }
        return buf.toString();
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
        switch  (item.getItemId()) {
            case R.id.action_save:
                openSaveDialog();
                return true;
            case R.id.action_settings:
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

    public void markValue(View v) {
        if (positionInFocus != -1) {
            boolean isMarked = content[positionInFocus].isHighlighted();
            content[positionInFocus].setHighlighted(!isMarked);
            ((ArrayAdapter) gameBoard.getAdapter()).notifyDataSetChanged();
        }
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
        int startPoint = line*BOARD_SIZE;
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

    private void openSaveDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Save Board");

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View v = inflater.inflate(R.layout.save_view, null);

        dialog.setView(v);

        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameText = ((EditText)v.findViewById(R.id.editText)).getText().toString();
                String category = ((Spinner)v.findViewById(R.id.spinner)).getSelectedItem().toString();
                if (!nameText.isEmpty() || !category.isEmpty()) {
                    db = new DBAdapter(SudokuActivity.this);
                    db.open();
                    db.insert(category, nameText, toPuzzleString());
                    db.close();
                }

                //Log.d("SAVE!", nameText + " " + category);
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("save!", "CANCEL!");
            }
        });

        dialog.show();

        Spinner spinner = (Spinner)v.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private boolean traverseSquareGroups(int group) {
        Set<SudokuCell> gElements = new HashSet<>();
        int groupIndex = ((((9*group)/(27))*27)+((group%3)*3));

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
