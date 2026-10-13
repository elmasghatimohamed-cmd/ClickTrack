package ma.clicktrack.service;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;

import java.util.List;

public interface LinkService {

    List<Link> findAll();

    List<Link> findByPublisher(Publisher publisher);

    void save(Link link);

    void delete(Link link);
}
