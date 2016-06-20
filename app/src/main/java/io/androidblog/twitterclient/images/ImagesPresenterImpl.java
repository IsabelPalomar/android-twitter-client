package io.androidblog.twitterclient.images;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import io.androidblog.twitterclient.entities.Image;
import io.androidblog.twitterclient.images.events.ImagesEvent;
import io.androidblog.twitterclient.images.ui.ImagesView;
import io.androidblog.twitterclient.lib.base.EventBus;

public class ImagesPresenterImpl implements ImagesPresenter {
    private EventBus eventBus;
    private ImagesView imagesView;
    private final ImagesInteractor imagesInteractor;

    public ImagesPresenterImpl(ImagesView imagesView, ImagesInteractor imagesInteractor, EventBus eventBus) {
        this.eventBus = eventBus;
        this.imagesView = imagesView;
        this.imagesInteractor = imagesInteractor;
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.imagesView = null;
    }

    @Override
    public void getImageTweets(){
        if (this.imagesView != null){
            imagesView.hideList();
            imagesView.showProgress();
        }

        this.imagesInteractor.execute();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ImagesEvent event) {
        String errorMsg = event.getError();
        if (this.imagesView != null) {
            imagesView.showList();
            imagesView.hideProgress();
            if (errorMsg != null) {
                this.imagesView.onImagesError(errorMsg);
            } else {
                List<Image> items = event.getImages();
                if (items != null && !items.isEmpty()) {
                    this.imagesView.setImages(items);
                }
            }
        }
    }
}
