package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.PublisherRepository;
import ma.clicktrack.service.PublisherService;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repository;

    public PublisherServiceImpl(PublisherRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Publisher> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Publisher> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Publisher publisher) {
        repository.save(publisher);
    }

    @Override
    public void update(Publisher publisher) {
        repository.save(publisher);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}
