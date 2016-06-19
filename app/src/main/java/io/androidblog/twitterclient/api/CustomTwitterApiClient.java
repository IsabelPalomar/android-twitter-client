package io.androidblog.twitterclient.api;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

public class CustomTwitterApiClient extends TwitterApiClient{

    public CustomTwitterApiClient(Session session) {
        super(session);
    }

    public  TimeLineService getTimeLineService(){
        return getService(TimeLineService.class);
    }
}
