package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.LinkRepository;
import ma.clicktrack.service.LinkService;

import java.util.List;

public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public List<Link> findByPublisher(Publisher publisher){
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

}
