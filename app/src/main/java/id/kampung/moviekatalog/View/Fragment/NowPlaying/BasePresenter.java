package id.kampung.moviekatalog.View.Fragment.NowPlaying;

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

public class BasePresenter
{
    private BaseInterface playingInterface;

    BasePresenter(BaseInterface playingInterface) {
        this.playingInterface = playingInterface;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getDataPlaying(String API_KEY,String LNG){
        ApiService.getInstance()
                .dataService()
                .playingMovie(API_KEY,LNG)
                .enqueue(new Callback<BaseResponse<MovieModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponse<MovieModel>> call, @NonNull Response<BaseResponse<MovieModel>> response) {
                        if(response.isSuccessful()){
                            if(Objects.requireNonNull(response.body()).getTotal_results() != 0){
                                Log.d("##", Objects.requireNonNull(response.body()).getResults().size()+"");
                                playingInterface.movie(Objects.requireNonNull(response.body()).getResults());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponse<MovieModel>> call, @NonNull Throwable t) {

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getDataUpComing(String API_KEY,String LNG){
        ApiService.getInstance()
                .dataService()
                .upComingMovie(API_KEY,LNG)
                .enqueue(new Callback<BaseResponse<MovieModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponse<MovieModel>> call, @NonNull Response<BaseResponse<MovieModel>> response) {
                        if(response.isSuccessful()){
                            if(Objects.requireNonNull(response.body()).getTotal_results() != 0){
                                Log.d("##", Objects.requireNonNull(response.body()).getResults().size()+"");
                                playingInterface.movie(Objects.requireNonNull(response.body()).getResults());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponse<MovieModel>> call, @NonNull Throwable t) {

                    }
                });
    }

}
