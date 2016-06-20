package io.androidblog.twitterclient.images.ui;


import java.util.List;

import io.androidblog.twitterclient.entities.Image;

public interface ImagesView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void onImagesError(String error);
    void setImages(List<Image> items);
}
