package ma.clicktrack.service;

import ma.clicktrack.repository.PublisherRepository;

public class PublisherService {

    private final PublisherRepository repository;

    public PublisherService(PublisherRepository repository){
        this.repository = repository;
    }


}
