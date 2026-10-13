package ma.clicktrack.repository;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface LinkRepository {

    List<Link> findAll();

    List<Link> findByPublisher(Publisher publisher);

    void save(Link link);

    void delete(Link link);
}
