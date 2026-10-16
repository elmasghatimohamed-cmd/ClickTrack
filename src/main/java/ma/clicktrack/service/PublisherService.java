package ma.clicktrack.service;

import ma.clicktrack.model.Publisher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PublisherService {

    Optional<Publisher> findById(long id);

    List<Publisher> findAll();

    void save(Publisher publisher);

    void update(Publisher publisher);

    void delete(long id);

    List<Map<String, Object>> getOffres(long publisherId);
}
