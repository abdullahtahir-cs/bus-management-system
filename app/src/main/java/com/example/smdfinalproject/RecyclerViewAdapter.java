package com.example.smdfinalproject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<MyBus> MyBusList;
    ImageView imageViewBus;

    int lay=0;

    private OnItemClickListener mListener;

    public RecyclerViewAdapter(ArrayList<MyBus> myBusList) {
        MyBusList = myBusList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_bus, parent, false);
        ViewHolder obj = new ViewHolder(view);
        imageViewBus = view.findViewById(R.id.imageviewbus);
        return obj;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class DownloadImage extends AsyncTask<String,Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                InputStream inputstream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                return bitmap;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        MyBus myBus =MyBusList.get(position);
        holder.numberPlate.setText("NumberPlate: " + myBus.getNumberPlate());
        holder.from.setText("From: " + myBus.getFrom());
        holder.to.setText("To: " + myBus.getTo());
        holder.price.setText("Price: " + myBus.getPrice());
        holder.availableSeats.setText("AvailableSeats: " + myBus.getAvailableSeats());
        DownloadImage obj = new DownloadImage();
        try {
            Bitmap bitmap = obj.execute("https://th.bing.com/th/id/OIP.0G0Q6tZwq6F9leFP1-6N_AHaFX?pid=ImgDet&rs=1").get();
            imageViewBus.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return MyBusList.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = (OnItemClickListener) listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView numberPlate, from, to, price, availableSeats;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numberPlate = itemView.findViewById(R.id.txtview_numberplate);
            from = itemView.findViewById(R.id.txtview_from);
            to = itemView.findViewById(R.id.txtview_to);
            price = itemView.findViewById(R.id.txtview_price);
            availableSeats = itemView.findViewById(R.id.txtview_available_seats);
        }
    }
}
