package id.kampung.moviekatalog.View.favorite;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.db.FavoriteHelper;

import static id.kampung.moviekatalog.db.DbContract.CONTENT_URI;

public class FavoritPresenter {
    private FavoritInterface favoritInterface;
    private  FavoriteHelper helper ;
    Context context;
    FavoritPresenter(FavoritInterface favoritInterface, Context context) {
        this.favoritInterface = favoritInterface;
        this.context = context;
        helper = new FavoriteHelper(context);
    }

    public void loadData(){

        helper.open();
        ArrayList<MovieModel> models = helper.query();

        if(models.size() != 0){
            favoritInterface.setData(models);
        }else {
            favoritInterface.onEmpety();
        }
    }
}
