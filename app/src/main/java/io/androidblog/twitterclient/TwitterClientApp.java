package io.androidblog.twitterclient;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.androidblog.twitterclient.images.di.DaggerImagesComponent;
import io.androidblog.twitterclient.images.di.ImagesComponent;
import io.androidblog.twitterclient.images.di.ImagesModule;
import io.androidblog.twitterclient.images.ui.ImagesView;
import io.androidblog.twitterclient.images.ui.adapters.OnItemClickListener;
import io.androidblog.twitterclient.lib.di.LibsModule;
import io.fabric.sdk.android.Fabric;

public class TwitterClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFrabric();
    }

    private void initFrabric() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    public ImagesComponent getImagesComponent(Fragment fragment, ImagesView view, OnItemClickListener clickListener){
        return DaggerImagesComponent
                .builder()
                .libsModule(new LibsModule(fragment))
                .imagesModule(new ImagesModule(view, clickListener))
                .build();
    }
}
