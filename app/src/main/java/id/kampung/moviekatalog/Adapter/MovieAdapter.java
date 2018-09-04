package id.kampung.moviekatalog.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import id.kampung.moviekatalog.Model.MovieModel;
import id.kampung.moviekatalog.R;
import id.kampung.moviekatalog.View.Detail.DetailMovie;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieModel> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieAdapter( Context context) {
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieModel> items){
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mData == null) return  0;
        return  mData.size();
    }

    @Override
    public MovieModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_movie,null);
            holder.textViewNama = convertView.findViewById(R.id.text_nama);
            holder.textViewDeskripsi = convertView.findViewById(R.id.text_deskripsi);
            holder.textViewRilis = convertView.findViewById(R.id.text_rilis);
            holder.imageViewCover = convertView.findViewById(R.id.imageView);
            holder.layout = convertView.findViewById(R.id.layout);
            convertView.setTag(holder);

        }else {
            holder =(ViewHolder) convertView.getTag();
        }
        String deskripsi;
        if(mData.get(position).getDeskripsi().length() <=30){
            deskripsi = mData.get(position).getDeskripsi();
        }else {
            deskripsi = mData.get(position).getDeskripsi().substring(0,30 )+ " ....";
        }
        holder.textViewNama.setText(mData.get(position).getTitle());
        holder.textViewDeskripsi.setText(deskripsi);
        holder.textViewRilis.setText(mData.get(position).getRilis());

        String BASE_PATH = "http://image.tmdb.org/t/p/w185/";
        Glide.with(context).load(BASE_PATH +mData.get(position).getUrlGambar()).into(holder.imageViewCover);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovie.class);
                Gson gson = new Gson();
                String data = gson.toJson(mData.get(position));
                intent.putExtra("data",data);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView textViewNama;
        TextView textViewDeskripsi;
        TextView textViewRilis;
        ImageView imageViewCover;
        ConstraintLayout layout;
    }
}

