package com.example.rebelfoodschallenge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        List<Model> modelList;
        LayoutInflater layoutInflater;
        private Context context;


public MyAdapter(Context context , List<Model> modelList){
        this.modelList = modelList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.abv.setText(modelList.get(position).getAbv());

        if(modelList.get(position).getIbu().isEmpty())
            holder.ibu.setText("--");
        else
            holder.ibu.setText(modelList.get(position).getIbu());

        holder.name.setText(modelList.get(position).getName());
        holder.id.setText(String.valueOf(modelList.get(position).getId()));
        holder.ounces.setText(String.valueOf(modelList.get(position).getOunces()));
        holder.style.setText(modelList.get(position).getStyle());

        if(modelList.get(position).getImage() != null) {
            Picasso.get().load(modelList.get(position).getImage()).into(holder.imageView);
//            holder.progressBar.setVisibility(View.GONE);
        }
        else {
            holder.imageView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView abv, ibu, name, style, id, ounces;
        ImageView imageView;

        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            abv = itemView.findViewById(R.id.abv);
            ibu = itemView.findViewById(R.id.ibu);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            ounces = itemView.findViewById(R.id.ounce);
            style = itemView.findViewById(R.id.style);
            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.image);
//            progressBar = itemView.findViewById(R.id.progress);
        }
    }

}
