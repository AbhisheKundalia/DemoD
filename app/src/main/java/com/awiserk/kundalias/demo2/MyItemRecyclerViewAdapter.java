package com.awiserk.kundalias.demo2;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
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

import org.w3c.dom.Text;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static android.R.id.list;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Item> items;

    public MyItemRecyclerViewAdapter(Context mContext, List<Item> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Glide.with(mContext).load(items.get(position).getImageUrl())
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
        holder.cost.setText(mContext.getString(R.string.unit_item_price)+" "+items.get(position).getCost());
        holder.id.setText(items.get(position).getName().toUpperCase());
        holder.size.setText(mContext.getString(R.string.size_item_label)+" "+getDelimittedString(items.get(position).getAvailableSizes()).toUpperCase());
    }

    private String getDelimittedString(List<String> availableSizes) {

        return org.apache.commons.lang3.StringUtils.join(availableSizes,", ");
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder Class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id, cost, size;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_image_progress);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
            id = (TextView) itemView.findViewById(R.id.id);
            cost = (TextView) itemView.findViewById(R.id.cost);
            size = (TextView) itemView.findViewById(R.id.size);
        }
    }
}
