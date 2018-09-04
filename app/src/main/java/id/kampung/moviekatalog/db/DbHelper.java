package id.kampung.moviekatalog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static id.kampung.moviekatalog.db.DbContract.TABLE_FAVORITE;

public class DbHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmovieapp";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY ," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL,"+
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE,
            DbContract.FavoriteColumns._ID,
            DbContract.FavoriteColumns.TITLE,
            DbContract.FavoriteColumns.DESCRIPTION,
            DbContract.FavoriteColumns.RILIS,
            DbContract.FavoriteColumns.RATING,
            DbContract.FavoriteColumns.IMAGE_POSTR,
            DbContract.FavoriteColumns.IMAGE_COVER
    );

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITE);
        onCreate(db);
    }
}
