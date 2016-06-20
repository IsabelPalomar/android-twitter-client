package io.androidblog.twitterclient.images;

public class ImagesInteractorImpl implements ImagesInteractor {
    private ImagesRepository repository;

    public ImagesInteractorImpl(ImagesRepository repository){
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getImages();
    }
}
