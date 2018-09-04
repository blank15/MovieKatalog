package id.kampung.moviekatalog.widget;

import android.appwidget.AppWidgetManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;
import id.kampung.moviekatalog.db.FavoriteHelper;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private ArrayList<MovieModel> models = new ArrayList<>();
    private String  BASE_PATH = "http://image.tmdb.org/t/p/w185/";
    private int mAppWidgetId;

    public StackRemoteViewFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.coba));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.coba));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.coba));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.coba));
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        getFavoriteMoview(mContext);
        Binder.restoreCallingIdentity(identityToken);
    }

    private void getFavoriteMoview(Context mContext) {
        FavoriteHelper helper = new FavoriteHelper(mContext) ;
        helper.open();
         models= helper.query();


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {


        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;

        try {
            bmp = Glide.with(mContext)
                    .load(BASE_PATH+models.get(position).getUrlGambarSampul())
                    .asBitmap()
                    .error(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary)))
                    .into(200,250).get();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("kesalahan",e.getMessage());
        }


        Log.d("url",bmp+"");
        rv.setImageViewBitmap(R.id.imageView, bmp);
        rv.setTextViewText(R.id.banner_text,models.get(position).getTitle());
        rv.setTextColor(R.id.banner_text,mContext.getResources().getColor(R.color.white));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
