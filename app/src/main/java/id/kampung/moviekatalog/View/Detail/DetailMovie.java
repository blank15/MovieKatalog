package id.kampung.moviekatalog.View.Detail;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.Objects;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;
import id.kampung.moviekatalog.View.MainMovie.MainActivity;

import static android.provider.BaseColumns._ID;
import static id.kampung.moviekatalog.db.DbContract.CONTENT_URI;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.DESCRIPTION;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_COVER;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_POSTR;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RATING;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RILIS;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.TITLE;

public class DetailMovie extends AppCompatActivity implements DetailInterface {

    ImageView imageViewCover;
    ImageView imageViewPoster;
    TextView textViewDeskripsi;
    TextView textViewRilis;
    TextView textViewRating;
    TextView textViewJudul;
    MovieModel movieModel;
    FloatingActionButton fabFavorite;
    boolean favorite ;
    DetailPresenter presenter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageViewCover = findViewById(R.id.image_cover);
        imageViewPoster = findViewById(R.id.image_poster);
        textViewDeskripsi = findViewById(R.id.text_deskripsi);
        textViewJudul = findViewById(R.id.text_judul);
        textViewRating = findViewById(R.id.text_rating);
        textViewRilis = findViewById(R.id.text_rilis);
        fabFavorite =  findViewById(R.id.fab_fav);

        final Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {

                if (cursor.moveToFirst()) movieModel = new MovieModel(cursor);
                cursor.close();
            }
        }
        Gson gson = new Gson();
        favorite = getIntent().getBooleanExtra("FavoriteWidget",false);
        if (!favorite){
            fabFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);

        }
        String data = getIntent().getStringExtra("data");
        movieModel = gson.fromJson(data,MovieModel.class);
        presenter = new DetailPresenter(this,this);
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite){
                    presenter.deleteFavorite(uri);
                }else
                    presenter.saveFavorite(movieModel);

            }
        });
        setUI();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void setUI() {
        String BASE_PATH = "http://image.tmdb.org/t/p/w500/";
        Glide.with(this).load(BASE_PATH +movieModel.getUrlGambarSampul()).into(imageViewCover);
        Glide.with(this).load(BASE_PATH +movieModel.getUrlGambar()).into(imageViewPoster);

        Objects.requireNonNull(getSupportActionBar()).setTitle(movieModel.getTitle());

        textViewJudul.setText(movieModel.getTitle());
        textViewRilis.setText(movieModel.getRilis());
        textViewRating.setText("Rating :" + movieModel.getRating());
        textViewDeskripsi.setText(movieModel.getDeskripsi());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onChangeFavorite(boolean isFavorite) {
        if (favorite){

            fabFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        }else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);

        }
    }
}
