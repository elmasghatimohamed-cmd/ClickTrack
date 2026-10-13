package ma.clicktrack.repository.implementation;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;

public class LinkRepositoryImpl implements LinkRepository {

    private final List<Link> links = new ArrayList<>();

    @Override
    public List<Link> findAll() {
        return new ArrayList<>(links);
    }

    @Override
    public List<Link> findByPublisher(Publisher publisher) {
        return links.stream()
                .filter(link -> link.getPublisher().equals(publisher))
                .toList();
    }

    @Override
    public void save(Link link) {
        boolean exists = links.stream()
                .anyMatch(existingLink -> existingLink.getId().equals(link.getId()));
        if (!exists) {
            links.add(link);
        }
    }


    @Override
    public void delete(Link link) {
        links.removeIf(existing ->
                existing.getId().equals(link.getId()));
    }

}
