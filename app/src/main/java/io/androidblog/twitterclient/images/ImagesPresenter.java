package io.androidblog.twitterclient.images;

import io.androidblog.twitterclient.images.events.ImagesEvent;

public interface ImagesPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getImageTweets();
    void onEventMainThread(ImagesEvent event);
}
