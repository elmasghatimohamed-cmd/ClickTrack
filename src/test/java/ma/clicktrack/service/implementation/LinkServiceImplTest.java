package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.implementation.LinkRepositoryImpl;
import ma.clicktrack.repository.implementation.PublisherRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkServiceImplTest {

    private LinkRepositoryImpl linkRepository;
    private PublisherRepositoryImpl publisherRepository;
    private LinkServiceImpl linkService;
    private Publisher mohamed;

    @BeforeEach
    void setUp() {
        linkRepository = new LinkRepositoryImpl();
        publisherRepository = new PublisherRepositoryImpl();

        linkService = new LinkServiceImpl(
                linkRepository,
                publisherRepository
        );

        mohamed = new Publisher(
                1, "Mohamed", "El Masghati",
                "mohamed@example.com", Category.INFLUENCER
        );

        publisherRepository.save(mohamed);
    }

    @Test
    void findAllShouldReturnAllLinks() {
        linkService.save(new Link(
                1, "Nike", "https://www.nike.com", 10L, mohamed
        ));
        linkService.save(new Link(
                2, "Adidas", "https://www.adidas.com", 5L, mohamed
        ));

        assertEquals(2, linkService.findAll().size());
    }

    @Test
    void findByPublisherShouldReturnPublisherLinks() {
        linkService.save(new Link(
                1, "Nike", "https://www.nike.com", 10L, mohamed
        ));

        List<Link> result = linkService.findByPublisher(mohamed);

        assertEquals(1, result.size());
        assertEquals("Nike", result.get(0).getOffre());
    }

    @Test
    void saveShouldDelegateToRepository() {
        Link link = new Link(
                1, "Nike", "https://www.nike.com", 0L, mohamed
        );

        linkService.save(link);

        assertTrue(linkRepository.findById(1).isPresent());
    }

    @Test
    void deleteShouldRemoveLink() {
        Link link = new Link(
                1, "Nike", "https://www.nike.com", 0L, mohamed
        );
        linkService.save(link);

        linkService.delete(link);

        assertTrue(linkRepository.findById(1).isEmpty());
    }

    @Test
    void createShouldCreateLinkWithGeneratedIdAndZeroClicks() {
        Link created = linkService.create(
                1, "Nike", "https://www.nike.com"
        );

        assertEquals(1, created.getId());
        assertEquals("Nike", created.getOffre());
        assertEquals("https://www.nike.com", created.getUrlDestination());
        assertEquals(0L, created.getClicks());
        assertSame(mohamed, created.getPublisher());
        assertEquals(1, linkService.findAll().size());
    }

    @Test
    void createShouldGenerateNextId() {
        linkService.create(1, "Nike", "https://www.nike.com");

        Link second = linkService.create(
                1, "Adidas", "https://www.adidas.com"
        );

        assertEquals(2, second.getId());
    }

    @Test
    void createShouldThrowExceptionWhenPublisherDoesNotExist() {
        assertThrows(
                IllegalArgumentException.class,
                () -> linkService.create(
                        999, "Nike", "https://www.nike.com"
                )
        );

        assertTrue(linkService.findAll().isEmpty());
    }
}