package ma.clicktrack.service;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface LinkService {

    List<Link> findAll();

    List<Link> findByPublisher(Publisher publisher);

    void save(Link link);

    void delete(Link link);

    Link create(Integer publisherId, String offre, String urlDestination);

    Optional<Link> clickTrack(Integer id);
}
