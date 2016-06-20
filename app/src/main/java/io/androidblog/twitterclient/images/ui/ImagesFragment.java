package io.androidblog.twitterclient.images.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.androidblog.twitterclient.R;
import io.androidblog.twitterclient.TwitterClientApp;
import io.androidblog.twitterclient.entities.Image;
import io.androidblog.twitterclient.images.ImagesPresenter;
import io.androidblog.twitterclient.images.di.ImagesComponent;
import io.androidblog.twitterclient.images.ui.adapters.ImagesAdapter;
import io.androidblog.twitterclient.images.ui.adapters.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagesFragment extends Fragment implements ImagesView, OnItemClickListener {
    @Inject
    ImagesAdapter adapter;
    @Inject
    ImagesPresenter presenter;

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);

        setupInjection();
        setupRecyclerView();
        presenter.getImageTweets();

        return view;
    }

    private void setupInjection() {
        TwitterClientApp app = (TwitterClientApp) getActivity().getApplication();
        ImagesComponent imagesComponent = app.getImagesComponent(this, this, this);
        imagesComponent.inject(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onImagesError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setImages(List<Image> items) {
        adapter.setItems(items);
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Image tweet) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweet.getTweetUrl()));
        startActivity(intent);
    }
}

