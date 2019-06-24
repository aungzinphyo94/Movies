package com.azp.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azp.movies.R;
import com.azp.movies.model.TopRatedItem;
import com.azp.movies.utils.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>{

    private List<TopRatedItem> topRatedItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TopRatedAdapter(List<TopRatedItem> topRatedItems, OnItemClickListener onItemClickListener) {
        this.topRatedItems = topRatedItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TopRatedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_top_rated, viewGroup, false);
        return new TopRatedViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedViewHolder topRatedViewHolder, int position) {

        final TopRatedViewHolder holder = topRatedViewHolder;
        TopRatedItem model = topRatedItems.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(topRatedViewHolder.backDrop.getContext())
                .load(ApiUtils.getBasePosterPath(model.getPosterPath()))
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
                .into(holder.backDrop);

//        Glide.with(topRatedViewHolder.backDrop.getContext())
//                .asBitmap()
//                .load(ApiUtils.getBasePosterPath(model.getPosterPath()))
//                .apply(requestOptions)
//                .into(new BitmapImageViewTarget(holder.backDrop){
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        super.onResourceReady(resource, transition);
////                        Palette.from(resource).generate(palette -> )
//
//                    }
//                });

        holder.movieTitle.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d("movies", topRatedItems.size()+"");
        return topRatedItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class TopRatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movieTitle;

        ImageView backDrop;
        ProgressBar progressBar;

        OnItemClickListener onItemClickListener;

        public TopRatedViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            movieTitle = itemView.findViewById(R.id.title);
            backDrop = itemView.findViewById(R.id.img_backdrop);
            progressBar = itemView.findViewById(R.id.progress_load_photo);

            itemView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view, this.getAdapterPosition());
        }
    }
}
