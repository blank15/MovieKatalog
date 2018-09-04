package id.kampung.moviekatalog.View.MainMovie;

import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.kampung.moviekatalog.Adapter.MovieAdapter;
import id.kampung.moviekatalog.BuildConfig;
import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;

public class MainActivity extends AppCompatActivity implements MovieInterface, View.OnClickListener {


    ListView listViewMovie;
    EditText editTextMovie;
    Button buttonCari;
    private  final String API_KEY = BuildConfig.MOVIE_API_KEY;
    private  final String LNG = "en-US";
    private final String DATA = "data";
    MovieAdapter adapter;
    MoviePresenter presenter;
    String query="data";
    ArrayList<MovieModel> listmovie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.edit_movie);
        buttonCari = findViewById(R.id.button_cari);
        listViewMovie = findViewById(R.id.list_movie);

        presenter = new MoviePresenter(this);

//        cari(default_query);
        adapter = new MovieAdapter(this);
        listViewMovie.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        buttonCari.setOnClickListener(this);


    }

    @Override
    public void cariMovie(ArrayList<MovieModel> modelList) {
        listmovie = modelList;
        adapter.setData(listmovie);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_cari:
//                cari();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void cari(String kataKunci) {
         query = kataKunci;
        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(query)){
            isEmptyFields = true;
            editTextMovie.setError("Field ini tidak boleh kosong");
        }

        if(!isEmptyFields){
            presenter.getData(API_KEY,LNG,query);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

//        outState.putString(DATA, query);
        outState.putParcelableArrayList(DATA,new ArrayList<Parcelable>(listmovie));
        super.onSaveInstanceState(outState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        listmovie = savedInstanceState.getParcelableArrayList(DATA);

        adapter.setData(listmovie);
        adapter.notifyDataSetChanged();
        Log.d("isi data",listmovie +"");
//        query = savedInstanceState.getString(DATA);
//        presenter.getData(API_KEY,LNG,query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                cari(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
