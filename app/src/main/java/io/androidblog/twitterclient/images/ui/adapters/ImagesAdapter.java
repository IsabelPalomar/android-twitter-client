package io.androidblog.twitterclient.images.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.androidblog.twitterclient.R;
import io.androidblog.twitterclient.entities.Image;
import io.androidblog.twitterclient.lib.base.ImageLoader;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder>{

    private List<Image> dataset;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public ImagesAdapter(List<Image> dataset, ImageLoader imageLoader, OnItemClickListener clickListener) {
        this.dataset = dataset;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image imageTweet = dataset.get(position);
        holder.setClickListener(imageTweet, clickListener);
        holder.txtTweet.setText(imageTweet.getTweetText());
        imageLoader.load(holder.imgMedia, imageTweet.getImageUrl());
    }

    public void setItems(List<Image> newItems) {
        newItems.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtTweet)
        TextView txtTweet;
        @Bind(R.id.imgMedia)
        ImageView imgMedia;

        private View view;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }

        public void setClickListener(final Image tweet, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(tweet);
                }
            });

        }
    }

}
