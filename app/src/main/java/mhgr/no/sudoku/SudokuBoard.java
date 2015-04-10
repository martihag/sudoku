package mhgr.no.sudoku;

/**
 * Created by mh on 10.04.15.
 */
public class SudokuBoard {
    int _id;
    String name;
    String sudoku_board;

    public SudokuBoard() {

    }

    public SudokuBoard(int id, String name, String board) {
        this._id = id;
        this.name = name;
        this.sudoku_board = board;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSudoku_board() {
        return sudoku_board;
    }

    public void setSudoku_board(String sudoku_board) {
        this.sudoku_board = sudoku_board;
    }

    @Override
    public String toString() {
        return name;
    }
}
