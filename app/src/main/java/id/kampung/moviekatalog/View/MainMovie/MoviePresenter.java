package id.kampung.moviekatalog.View.MainMovie;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Objects;

import id.kampung.moviekatalog.Model.BaseResponse;
import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter {

    private  MovieInterface movieInterface;

    MoviePresenter(MovieInterface movieInterface) {
        this.movieInterface = movieInterface;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getData(String api,String lng,String search){
        ApiService.getInstance()
                .dataService()
                .cariMovie(api,lng,search)
                .enqueue(new Callback<BaseResponse<MovieModel>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<MovieModel>> call, Response<BaseResponse<MovieModel>> response) {
                        Log.d("##",response.errorBody()+"");
                        if(response.isSuccessful() ){
                            if(Objects.requireNonNull(response.body()).getTotal_results() != 0){
                                if(response.body() != null)
                                    movieInterface.cariMovie(Objects.requireNonNull(response.body()).getResults());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<MovieModel>> call, Throwable t) {

                    }
                });
    }
}
