package com.awiserk.kundalias.demo2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awiserk.kundalias.demo2.data.Item;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private Context c;
    private List<Item> items;

    public MyItemRecyclerViewAdapter(Context c, List<Item> items) {
        this.c = c;
        this.items = items;
    }

    public void addItemIsAdapter(List<Item> items)
    {
        //add list to current arraylist of data
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Glide.with(c).load(items.get(position).getImageUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
        holder.cost.setText(c.getString(R.string.unit_item_price)+" "+items.get(position).getCost());
        holder.id.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id, cost;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_image_progress);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
            id = (TextView) itemView.findViewById(R.id.id);
            cost = (TextView) itemView.findViewById(R.id.cost);
        }
    }
}
