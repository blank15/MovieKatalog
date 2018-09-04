package id.kampung.moviekatalog.View.Fragment.NowPlaying;


import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.kampung.moviekatalog.Adapter.RecyclerViewMovie;
import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;

import static id.kampung.moviekatalog.BuildConfig.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment implements BaseInterface {


    RecyclerView recyclerView;
    BasePresenter presenter;
    ArrayList<MovieModel> movieModel = new ArrayList<>();
    RecyclerViewMovie adapter;
    private String DATA = "data";
    public UpComingFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_up_coming, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter = new BasePresenter(this);

        adapter = new RecyclerViewMovie(getContext(),movieModel);
        recyclerView.setAdapter(adapter);

        String LNG = "en-US";
        presenter.getDataUpComing(MOVIE_API_KEY, LNG);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            movieModel = savedInstanceState.getParcelableArrayList(DATA);
            adapter.addItem(movieModel);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(DATA,new ArrayList<Parcelable>(movieModel));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);



    }

    @Override
    public void movie(ArrayList<MovieModel> modelList) {
        Log.d("###",modelList.get(1).getTitle()+"");
        movieModel = modelList;
        adapter.addItem(movieModel);
        adapter.notifyDataSetChanged();
    }
}