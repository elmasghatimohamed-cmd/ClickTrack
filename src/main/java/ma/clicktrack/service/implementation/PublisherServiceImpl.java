package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.PublisherRepository;
import ma.clicktrack.service.LinkService;
import ma.clicktrack.service.PublisherService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repository;
    private final LinkService linkService;

    public PublisherServiceImpl(
            PublisherRepository repository,
            LinkService linkService) {
        this.repository = repository;
        this.linkService = linkService;
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
        repository.update(publisher);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    public List<Map<String, Object>> getOffres(long publisherId) {
        Optional<Publisher> existingPublisher = repository.findById(publisherId);

        if (existingPublisher.isEmpty()) {
            return new ArrayList<>();
        }

        Publisher publisher = existingPublisher.get();

        List<Link> links = linkService.findByPublisher(publisher);

        Map<String, Map<String, Object>> statistiques = new LinkedHashMap<>();

        for (Link link : links) {
            String offre = link.getOffre();

            Map<String, Object> statistique = statistiques.get(offre);

            if (statistique == null) {
                statistique = new LinkedHashMap<>();
                statistique.put("offre", offre);
                statistique.put("nombreliens", 0);
                statistique.put("clicks", 0L);

                statistiques.put(offre, statistique);
            }

            int nombreLiens = (int) statistique.get("nombreliens");

            long clicks = (long) statistique.get("clicks");

            statistique.put("nombreliens", nombreLiens + 1);

            long clicksDuLien = link.getClicks() == null ? 0L : link.getClicks();

            statistique.put("clicks", clicks + clicksDuLien);
        }

        return new ArrayList<>(statistiques.values());
    }
}
