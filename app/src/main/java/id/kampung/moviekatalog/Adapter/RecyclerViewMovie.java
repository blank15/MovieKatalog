package id.kampung.moviekatalog.Adapter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;
import id.kampung.moviekatalog.View.Detail.DetailMovie;
import id.kampung.moviekatalog.View.Home.HomeActivity;
import id.kampung.moviekatalog.broadcaster.AlarmReceiver;
import id.kampung.moviekatalog.db.FavoriteHelper;

import static id.kampung.moviekatalog.db.DbContract.CONTENT_URI;

public class RecyclerViewMovie extends RecyclerView.Adapter<RecyclerViewMovie.ViewHolder> {

    private Context context;
    private ArrayList<MovieModel> modelList;
    private final String BASE_PATH;
    private FavoriteHelper helper;
    private ArrayList<MovieModel> modelFavorite;
    private Cursor cursor;
    public RecyclerViewMovie(Context context, ArrayList<MovieModel> modelList) {
        this.context = context;
        this.modelList = modelList;
        helper = new FavoriteHelper(context);
        helper.open();
        modelFavorite = helper.query();
        helper.close();
        BASE_PATH = "http://image.tmdb.org/t/p/w185/";
    }

    public void setListNotes(Cursor lisMovie) {
        this.cursor = lisMovie;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_movie,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movieModel = new MovieModel() ;
        if (modelList != null){
            movieModel = modelList.get(position);
        }

        Glide.with(context).load(BASE_PATH+movieModel.getUrlGambar()).into(holder.imageViewCover);
        holder.textViewNama.setText(movieModel.getTitle());

        holder.textViewDeskripsi.setText(movieModel.getDeskripsi());
        holder.textViewRilis.setText(movieModel.getRilis());
        setReleaseAlarm(movieModel.getRilis(),movieModel.getTitle());
        boolean favorite = false;
        for (int i=0;i<modelFavorite.size();i++){
            if(movieModel.getId() == modelFavorite.get(i).getId()){
                favorite = true;
                break;
            }
            favorite = false;
            Log.d("id",movieModel.getId()+ " " + modelFavorite.get(i).getId());
        }
        final MovieModel finalMovieModel = movieModel;
        final boolean finalFavorite = favorite;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovie.class);
                Gson gson = new Gson();
                String data = gson.toJson(finalMovieModel);
                intent.putExtra("data",data);
                intent.putExtra("FavoriteWidget", finalFavorite);
                Uri uri = Uri.parse(CONTENT_URI + "/" + finalMovieModel.getId());
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }

    private void setReleaseAlarm(String rilis,String judul) {
        PendingIntent mPendingIntent;
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        if(rilis.equals(formattedDate)){
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            alarmIntent.putExtra("title",judul);
            alarmIntent.putExtra("pesan","Hari ini " + judul + " release");
            alarmIntent.putExtra("id",2);
            mPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND,00);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, mPendingIntent);
        }
    }

    public void addItem(ArrayList<MovieModel> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public void removeAllItem(){
        this.modelList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNama;
        TextView textViewDeskripsi;
        TextView textViewRilis;
        ImageView imageViewCover;
        ConstraintLayout layout;
        ViewHolder(View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.text_nama);
            textViewDeskripsi = itemView.findViewById(R.id.text_deskripsi);
            textViewRilis = itemView.findViewById(R.id.text_rilis);
            imageViewCover = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
