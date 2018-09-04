package id.kampung.moviekatalog.View.favorite;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.kampung.moviekatalog.Adapter.RecyclerViewMovie;
import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;
import id.kampung.moviekatalog.View.MainMovie.MainActivity;


public class FavoriteMovie extends AppCompatActivity implements FavoritInterface {

    private ArrayList<MovieModel> movieModel = new ArrayList<>();
    private RecyclerViewMovie adapter;
    private RecyclerView recyclerView;
    private FavoritPresenter presenter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        presenter = new FavoritPresenter(this,this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewMovie(this,movieModel);
        recyclerView.setAdapter(adapter);
        presenter.loadData();



    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void setData(ArrayList<MovieModel> movieModels) {
        adapter.addItem(movieModels);
    }

    @Override
    public void onEmpety() {
        adapter.removeAllItem();
        Toast.makeText(this, " Belum ada Movie yang di FavoriteWidget kan", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
