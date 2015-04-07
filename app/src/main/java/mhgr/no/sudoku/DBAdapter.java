package mhgr.no.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mh on 03.04.15.
 */
public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_BOARD_DATA = "sudoku_board";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "SudokuDb";
    static final String DATABASE_TABLE1 = "simple";
    static final String DATABASE_TABLE2 = "medium";
    static final String DATABASE_TABLE3 = "hard";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE1 = "create table " + DATABASE_TABLE1 + "(" + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_NAME + " text unique not null, " +
            KEY_BOARD_DATA + " text not null);";
    static final String DATABASE_CREATE2 = "create table " + DATABASE_TABLE2 + "(" + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_NAME + " text unique not null, " +
            KEY_BOARD_DATA + " text not null);";
    static final String DATABASE_CREATE3 = "create table " + DATABASE_TABLE3 + "(" + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_NAME + " text unique not null, " +
            KEY_BOARD_DATA + " text not null);";
    static final String DATABASE_INSERT1 = "insert into " + DATABASE_TABLE1 + " values('default', 'Simple 1', " +
            "'080020673307105200024073010602010408108006052700842090410208007050760900076400581')";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
                db.execSQL(DATABASE_CREATE3);
                db.execSQL(DATABASE_INSERT1);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgraing database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    private long insertSimpleBoard(String name, String board) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_BOARD_DATA, board);
        return db.insert(DATABASE_TABLE1,  null, initialValues);
    }

    private long insertMediumBoard(String name, String board) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_BOARD_DATA, board);
        return db.insert(DATABASE_TABLE2,  null, initialValues);
    }

    private long insertHardBoard(String name, String board) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_BOARD_DATA, board);
        return db.insert(DATABASE_TABLE3,  null, initialValues);
    }

    public Cursor getAllSimple() {
        return db.query(DATABASE_TABLE1, new String[]{KEY_ROWID, KEY_NAME, KEY_BOARD_DATA}, null, null, null, null, null, null);
    }
    public Cursor getAllMedium() {
        return db.query(DATABASE_TABLE2, new String[]{KEY_ROWID, KEY_NAME, KEY_BOARD_DATA}, null, null, null, null, null, null);
    }
    public Cursor getAllHard() {
        return db.query(DATABASE_TABLE3, new String[]{KEY_ROWID, KEY_NAME, KEY_BOARD_DATA}, null, null, null, null, null, null);
    }
}
