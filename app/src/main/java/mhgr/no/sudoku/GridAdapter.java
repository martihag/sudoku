package mhgr.no.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by mh on 22.03.15.
 */
public class GridAdapter extends ArrayAdapter<SudokuCell> {

    public GridAdapter(Context context, int textViewResId, SudokuCell[] cells) {
        super(context, textViewResId, cells);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return !(this.getItem(position)).isInitialValue();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (this.getItem(position).isInitialValue()) {
                view.setBackgroundColor(Color.LTGRAY);
        }
        if (this.getItem(position).isHighlighted()) {
            ((TextView) view).setTextColor(Color.RED);
        } else {
            ((TextView) view).setTextColor(Color.BLACK);
        }

        return view;
    }
}
