package mhgr.no.sudoku;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mh on 22.03.15.
 */
public class SudokuGridViewItem extends TextView {

    public SudokuGridViewItem(Context context) {
        super(context);
    }

    public SudokuGridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuGridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
