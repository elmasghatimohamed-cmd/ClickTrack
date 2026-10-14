package ma.clicktrack.repository.implementation;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinkRepositoryImpl implements LinkRepository {

    private final List<Link> links = new ArrayList<>();

    public int generateId() {
        return links.stream()
                .mapToInt(Link::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public List<Link> findAll() {
        return new ArrayList<>(links);
    }

    @Override
    public Optional<Link> findById(Integer id) {
        return links.stream()
                .filter(link -> link.getId().equals(id))
                .findFirst();
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

    @Override
    public synchronized Optional<Link> incrementClicks(Integer id) {
        Optional<Link> optionalLink = findById(id);

        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();

            long currentClicks = link.getClicks() == null ? 0L : link.getClicks();

            link.setClicks(currentClicks + 1);
        }

        return optionalLink;
    }

}
