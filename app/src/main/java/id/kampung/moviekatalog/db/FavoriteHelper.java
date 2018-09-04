package id.kampung.moviekatalog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import id.kampung.moviekatalog.Model.MovieModel;

import static android.provider.BaseColumns._ID;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.DESCRIPTION;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_COVER;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_POSTR;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RATING;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RILIS;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.TITLE;
import static id.kampung.moviekatalog.db.DbContract.TABLE_FAVORITE;

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;
    private DbHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException{
        dataBaseHelper = new DbHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return  this;
    }
    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<MovieModel> query() {
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        MovieModel movie;
        if (cursor.getCount()>0) {
            do {
                movie = new MovieModel();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setRilis(cursor.getString(cursor.getColumnIndexOrThrow(RILIS)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setUrlGambar(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_POSTR)));
                movie.setUrlGambarSampul(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_COVER)));
                String rating =cursor.getString(cursor.getColumnIndexOrThrow(RATING));
                float ratingValue = Float.valueOf(rating.trim());
                movie.setRating(ratingValue);
                arrayList.add(movie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
