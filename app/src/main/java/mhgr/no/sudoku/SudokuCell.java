package mhgr.no.sudoku;

/**
 * Created by mh on 22.03.15.
 */
public class SudokuCell {
    private int value;
    private boolean isInitialValue;

    public SudokuCell(int val, boolean isInitial) {
        this.value = val;
        this.isInitialValue = isInitial;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isInitialValue() {
        return isInitialValue;
    }

    @Override
    public String toString() {
        if (value == 0) {
            return "";
        } else {
            return String.valueOf(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuCell that = (SudokuCell) o;

        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
