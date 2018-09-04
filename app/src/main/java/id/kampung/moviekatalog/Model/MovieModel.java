package id.kampung.moviekatalog.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.DESCRIPTION;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_COVER;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.IMAGE_POSTR;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RATING;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.RILIS;
import static id.kampung.moviekatalog.db.DbContract.FavoriteColumns.TITLE;

public class MovieModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("overview")
    @Expose
    private String deskripsi;

    @SerializedName("release_date")
    @Expose
    private String rilis;

    @SerializedName("poster_path")
    @Expose
    private String urlGambar;

    @SerializedName("backdrop_path")
    @Expose
    private String urlGambarSampul;

    @SerializedName("vote_average")
    @Expose
    private float rating;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public String getUrlGambarSampul() {
        return urlGambarSampul;
    }

    public void setUrlGambarSampul(String urlGambarSampul) {
        this.urlGambarSampul = urlGambarSampul;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public MovieModel() {
    }

    public MovieModel(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
        this.deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
        this.rilis = cursor.getString(cursor.getColumnIndexOrThrow(RILIS));
        this.urlGambar = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_POSTR));
        this.urlGambarSampul = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_COVER));
        String rating =cursor.getString(cursor.getColumnIndexOrThrow(RATING));
        float ratingValue = Float.valueOf(rating.trim());
        this.rating = ratingValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.deskripsi);
        dest.writeString(this.rilis);
        dest.writeString(this.urlGambar);
        dest.writeString(this.urlGambarSampul);
        dest.writeFloat(this.rating);
    }

    protected MovieModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.deskripsi = in.readString();
        this.rilis = in.readString();
        this.urlGambar = in.readString();
        this.urlGambarSampul = in.readString();
        this.rating = in.readFloat();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}

