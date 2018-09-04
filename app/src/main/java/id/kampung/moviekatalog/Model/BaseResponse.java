package id.kampung.moviekatalog.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BaseResponse<T> {

    @SerializedName("page")
    @Expose
    int page;

    @SerializedName("total_results")
    @Expose
    private
    int total_results;

    @SerializedName("total_pages")
    @Expose
    int total_pages;

    @SerializedName("results")
    @Expose
    private
    ArrayList<T> results;



    public int getTotal_results() {
        return total_results;
    }



    public ArrayList<T> getResults() {
        return results;
    }

}
