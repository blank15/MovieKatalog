package id.kampung.moviekatalog.View.Detail;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.Objects;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;

import static android.provider.BaseColumns._ID;
import static id.kampung.moviekatalog.db.DbContract.CONTENT_URI;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.DESCRIPTION;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_COVER;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_POSTR;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RATING;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RILIS;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.TITLE;

public class DetailPresenter {
    private DetailInterface detailInterface;
    private Context context;

    DetailPresenter(DetailInterface detailInterface, Context context) {
        this.detailInterface = detailInterface;
        this.context = context;
    }

    public void saveFavorite( MovieModel movieModel){

        ContentValues initialValues =  new ContentValues();
        initialValues.put(_ID,movieModel.getId());
        initialValues.put(TITLE, movieModel.getTitle());
        initialValues.put(DESCRIPTION, movieModel.getDeskripsi());
        initialValues.put(RILIS,movieModel.getRilis());
        initialValues.put(IMAGE_POSTR,movieModel.getUrlGambar());
        initialValues.put(IMAGE_COVER,movieModel.getUrlGambarSampul());
        initialValues.put(RATING,movieModel.getRating());
        context.getContentResolver().insert(CONTENT_URI, initialValues);
        detailInterface.onChangeFavorite(true);
    }
    public void deleteFavorite(Uri uri){
        context.getContentResolver().delete(uri,null,null);
        Log.d("item",uri.getLastPathSegment());
        detailInterface.onChangeFavorite(false);
    }
}
