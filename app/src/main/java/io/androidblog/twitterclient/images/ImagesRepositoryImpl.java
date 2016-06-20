package io.androidblog.twitterclient.images;


import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.androidblog.twitterclient.api.CustomTwitterApiClient;
import io.androidblog.twitterclient.entities.Image;
import io.androidblog.twitterclient.images.events.ImagesEvent;
import io.androidblog.twitterclient.lib.base.EventBus;

public class ImagesRepositoryImpl implements  ImagesRepository {

    private final static int TWEET_COUNT = 100;


    public ImagesRepositoryImpl(EventBus eventBus, CustomTwitterApiClient client) {
        this.eventBus = eventBus;
        this.client = client;
    }

    private EventBus eventBus;
    private CustomTwitterApiClient client;

    @Override
    public void getImages() {
        client.getTimeLineService().homeTimeLine(TWEET_COUNT, true, true, true, true,
                new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        List<Image> items = new ArrayList<Image>();
                        for (Tweet tweet : result.data) {
                            if (containsImage(tweet)) {
                                Image tweetModel = new Image();

                                tweetModel.setId(tweet.idStr);
                                tweetModel.setFavoriteCount(tweet.favoriteCount);

                                String tweetText = tweet.text;
                                int index = tweetText.indexOf("http");
                                if (index > 0) {
                                    tweetText = tweetText.substring(0, index);
                                }
                                tweetModel.setTweetText(tweetText);

                                MediaEntity currentPhoto = tweet.entities.media.get(0);
                                String imageURL = currentPhoto.mediaUrl;
                                tweetModel.setImageUrl(imageURL);

                                items.add(tweetModel);
                            }
                        }
                        Collections.sort(items, new Comparator<Image>() {
                            public int compare(Image t1, Image t2) {
                                return t2.getFavoriteCount() - t1.getFavoriteCount();
                            }
                        });
                        post(items);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        post(e.getMessage());
                    }
                }
        );

    }

    private boolean containsImage(Tweet tweet) {
        return  tweet.entities != null &&
                tweet.entities.media != null &&
                !tweet.entities.media.isEmpty();
    }

    private void post(List<Image> items){
        post(items, null);
    }

    private void post(String error){
        post(null, error);
    }

    private void post(List<Image> items, String error){
        ImagesEvent event = new ImagesEvent();
        event.setError(error);
        event.setImages(items);
        eventBus.post(event);
    }
}
