package com.qulix.gifsearcher.view.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.qulix.gifsearcher.R;
import com.bumptech.glide.Glide;

public class GifRecyclerViewHolder extends RecyclerView.ViewHolder{

    private ImageView gifImageView;

    public GifRecyclerViewHolder(View itemView) {
        super(itemView);
        gifImageView = (ImageView)itemView.findViewById(R.id.image_view_gif);
    }

    public void setGifImageView(Context context, String gifUrl) {
        try {
            Glide.with(context)
                    .load(gifUrl)
                    .into(gifImageView);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }
    }
}
