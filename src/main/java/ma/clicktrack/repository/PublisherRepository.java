package ma.clicktrack.repository;

import ma.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    Optional<Publisher> findById(long id);

    List<Publisher> findAll();

    void save(Publisher publisher);

    void update(Publisher publisher);

    void delete(long id);
}
