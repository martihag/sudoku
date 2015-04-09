package mhgr.no.sudoku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


public class MainMenuActivity extends ActionBarActivity {

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    public void startNewGameSimple(View v) {
        startNewGame("simple");
    }

    public void startNewGameMedium(View v) {
        startNewGame("medium");
    }

    public void startNewGameHard(View v) {
        startNewGame("hard");
    }

    public void startNewGame(String difficulty) {
        //Intent i = new Intent(this, SudokuActivity.class);
        //startActivity(i);
        db = new DBAdapter(this);
        db.open();
        Cursor c;
        switch (difficulty) {
            case("simple"):
                c = db.getAllSimple();
                break;
            case("medium"):
                c = db.getAllMedium();
                break;
            case("hard"):
                c = db.getAllHard();
                break;
            default:
                c = db.getAllSimple();
        }
        //String[] columns = new String[]{c.getColumnName(0),c.getColumnName(1),c.getColumnName(2)};

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                MainMenuActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select a board to solve");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainMenuActivity.this,
                android.R.layout.select_dialog_singlechoice);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            //Log.d("ONCREATE", String.valueOf(c.getInt(c.getColumnIndex("name"))));
            //Log.d("ONCREATE", c.getString(c.getColumnIndex("name")));
            arrayAdapter.add(c.getString(c.getColumnIndex("name")));
            c.moveToNext();
        }
        //for (String col : columns) {
            //arrayAdapter.add(col);
        //}
        builderSingle.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                MainMenuActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
        db.close();
    }
}
