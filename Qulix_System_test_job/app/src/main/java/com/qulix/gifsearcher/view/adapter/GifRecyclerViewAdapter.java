package com.qulix.gifsearcher.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.gifsearcher.R;
import com.qulix.gifsearcher.view.adapter.holder.GifRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GifRecyclerViewAdapter  extends RecyclerView.Adapter<GifRecyclerViewHolder>{

    private Context context;
    private List<String> listUrlsGif;

    public GifRecyclerViewAdapter(Context context, List<String> listUrlsGif){
        this.context = context;
        this.listUrlsGif = listUrlsGif;
    }

    public void addUrls(ArrayList<String> urls){
        if(urls != null){
            listUrlsGif=urls;
            notifyDataSetChanged();
        }
    }

    @Override
    public GifRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_gif_item,parent,false);
        return new GifRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GifRecyclerViewHolder holder, int position) {
        holder.setGifImageView(context,listUrlsGif.get(position));
    }

    @Override
    public int getItemCount() {
        return listUrlsGif.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
