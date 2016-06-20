package io.androidblog.twitterclient.images.di;


import javax.inject.Singleton;

import dagger.Component;
import io.androidblog.twitterclient.images.ui.ImagesFragment;
import io.androidblog.twitterclient.lib.di.LibsModule;

@Singleton @Component(modules = {ImagesModule.class, LibsModule.class})
public interface ImagesComponent {
    void inject(ImagesFragment fragment);

}
