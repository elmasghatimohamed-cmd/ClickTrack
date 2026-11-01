package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.LinkRepository;
import ma.clicktrack.repository.PublisherRepository;
import ma.clicktrack.service.LinkService;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final PublisherRepository publisherRepository;

    public LinkServiceImpl(LinkRepository linkRepository, PublisherRepository publisherRepository) {
        this.linkRepository = linkRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> findByPublisher(Publisher publisher) {
        return linkRepository.findByPublisher(publisher);
    }

    @Override
    public void save(Link link) {
        linkRepository.save(link);
    }

    @Override
    public void delete(Link link) {
        linkRepository.delete(link);
    }

    @Override
    public Link create(Integer publisherId, String offre, String urlDestination) {
        Publisher publisher = publisherRepository.findById(publisherId.longValue())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Publisher introuvable : " + publisherId
                        ));

        Link link = new Link(null, offre, urlDestination, 0L, publisher);

        linkRepository.save(link);

        return link;
    }

    @Override
    public Optional<Link> clickTrack(Integer id) {
        return linkRepository.incrementClicks(id);
    }
}
