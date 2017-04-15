package com.awiserk.kundalias.demo2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awiserk.kundalias.demo2.Data.DataProvider;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private Context c;
    private List<DataProvider.Item> items;

    public MyItemRecyclerViewAdapter(Context c, List<DataProvider.Item> items) {
        this.c = c;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(items.get(position).getThumbnail());
        holder.cost.setText(items.get(position).getCost());
        holder.id.setText(items.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView id, cost;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
            id = (TextView) itemView.findViewById(R.id.id);
            cost = (TextView) itemView.findViewById(R.id.cost);
        }
    }
}
