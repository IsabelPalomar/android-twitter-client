package io.androidblog.twitterclient.images.di;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.androidblog.twitterclient.api.CustomTwitterApiClient;
import io.androidblog.twitterclient.entities.Image;
import io.androidblog.twitterclient.images.ImagesInteractor;
import io.androidblog.twitterclient.images.ImagesInteractorImpl;
import io.androidblog.twitterclient.images.ImagesPresenter;
import io.androidblog.twitterclient.images.ImagesPresenterImpl;
import io.androidblog.twitterclient.images.ImagesRepository;
import io.androidblog.twitterclient.images.ImagesRepositoryImpl;
import io.androidblog.twitterclient.images.ui.ImagesView;
import io.androidblog.twitterclient.images.ui.adapters.ImagesAdapter;
import io.androidblog.twitterclient.images.ui.adapters.OnItemClickListener;
import io.androidblog.twitterclient.lib.base.EventBus;
import io.androidblog.twitterclient.lib.base.ImageLoader;

@Module
public class ImagesModule {
    private ImagesView view;
    private OnItemClickListener clickListener;

    public ImagesModule(ImagesView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    List<Image> provideItems() {
        return new ArrayList<Image>();
    }

    @Provides
    @Singleton
    OnItemClickListener provideClickListener() {
        return this.clickListener;
    }

    @Provides
    ImagesAdapter provideAdapter(List<Image> items, OnItemClickListener clickListener, ImageLoader imageLoader) {
        return new ImagesAdapter(items, imageLoader, clickListener);
    }

    @Provides
    @Singleton
    ImagesView provideImagesView() {
        return this.view;
    }

    @Provides
    @Singleton
    ImagesPresenter provideImagesPresenter(ImagesView view, ImagesInteractor interactor, EventBus eventBus) {
        return new ImagesPresenterImpl(view, interactor, eventBus);
    }

    @Provides
    @Singleton
    ImagesInteractor provideImagesInteractor(ImagesRepository repository) {
        return new ImagesInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ImagesRepository provideImagesRepository(CustomTwitterApiClient client, EventBus eventBus) {
        return new ImagesRepositoryImpl(eventBus, client );
    }

    @Provides
    @Singleton
    CustomTwitterApiClient provideTwitterApiClient(TwitterSession session) {
        return new CustomTwitterApiClient(session);
    }

    @Provides
    @Singleton
    TwitterSession provideTwitterSession() {
        return Twitter.getSessionManager().getActiveSession();
    }
}
