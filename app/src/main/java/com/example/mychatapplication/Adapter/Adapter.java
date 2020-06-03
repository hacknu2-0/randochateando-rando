package com.example.mychatapplication.Adapter;

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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mychatapplication.Model.Article;
import com.example.mychatapplication.R;
import com.example.mychatapplication.Utils;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Article> articles;
    private Context context;
    private onItemClickListener onItemClickListener;
    ImageView g;
    TextView a,b,c,d,e,f;
    AlertDialog dialog;

    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final MyViewHolder myViqewHolder=holder;
        Article model=articles.get(position);

        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(context)
        .load(model.getUrlToImage())
        .apply(requestOptions)
        .listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        })
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(holder.img);
        holder.title.setText(model.getTitle());
        holder.author.setText(model.getAuthor());
        holder.desc.setText(model.getDescription());
        holder.date.setText(model.getPublishedAt());
        holder.source.setText(model.getSource().getName());
        holder.time.setText("\u2022"+ Utils.DateToTimeFormat(model.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(View v, int pos);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView title,desc,source,date,time,author;
        ImageView img;
        ProgressBar progressBar;
        onItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView, onItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=itemView.findViewById(R.id.title);
            source=itemView.findViewById(R.id.source);
            date=itemView.findViewById(R.id.publishedAt);
            author=itemView.findViewById(R.id.author);
            desc=itemView.findViewById(R.id.desc);
            time=itemView.findViewById(R.id.time);
            img=itemView.findViewById(R.id.img);
            progressBar=itemView.findViewById(R.id.progress_load_photo);
            title=itemView.findViewById(R.id.title);
            this.onItemClickListener= onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());

        }
    }
}
