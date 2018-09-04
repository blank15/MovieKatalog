package id.kampung.moviekatalog.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static String TABLE_FAVORITE = "Favorite";

    public static final class FavoriteColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RILIS = "rilis";
        public static String RATING = "rating";
        public static String IMAGE_COVER = "cover";
        public static String IMAGE_POSTR = "poster";
    }

    public static final String AUTHORITY = "id.kampung.moviekatalog";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

}
