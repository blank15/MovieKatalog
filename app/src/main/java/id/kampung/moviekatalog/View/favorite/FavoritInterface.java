package id.kampung.moviekatalog.View.favorite;

import android.database.Cursor;

import java.util.ArrayList;

import id.kampung.moviekatalog.Model.MovieModel;

public interface FavoritInterface {
    void setData(ArrayList<MovieModel> movieModels);
    void onEmpety();
}
