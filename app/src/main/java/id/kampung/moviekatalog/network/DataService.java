package id.kampung.moviekatalog.network;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.Model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {


    @GET("search/movie")
    Call<BaseResponse<MovieModel>> cariMovie(@Query("api_key") String api,
                                             @Query("language") String lng,
                                             @Query("query") String query);

    @GET("movie/now_playing")
    Call<BaseResponse<MovieModel>> playingMovie(@Query("api_key") String api,
                                                @Query("language") String lng);

    @GET("movie/upcoming")
    Call<BaseResponse<MovieModel>> upComingMovie(@Query("api_key") String api,
                                                @Query("language") String lng);
}
