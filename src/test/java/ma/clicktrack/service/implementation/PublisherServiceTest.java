package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.implementation.LinkRepositoryImpl;
import ma.clicktrack.repository.implementation.PublisherRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PublisherServiceTest {

    private PublisherRepositoryImpl publisherRepository;
    private LinkServiceImpl linkService;
    private PublisherServiceImpl publisherService;

    private Publisher mohamed;
    private Publisher sara;

    @BeforeEach
    void setUp() {
        publisherRepository = new PublisherRepositoryImpl();

        LinkRepositoryImpl linkRepository = new LinkRepositoryImpl();

        linkService = new LinkServiceImpl(
                linkRepository,
                publisherRepository);

        publisherService = new PublisherServiceImpl(
                publisherRepository,
                linkService);

        mohamed = new Publisher(
                1, "Mohamed", "El Masghati",
                "mohamed@example.com", Category.INFLUENCER);

        sara = new Publisher(
                2, "Sara", "Amrani",
                "sara@example.com", Category.YOUTUBER);

        publisherService.save(mohamed);
        publisherService.save(sara);
    }

    @Test
    void findByIdShouldReturnPublisherWhenItExists() {
        Optional<Publisher> result = publisherService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Mohamed", result.get().getFirstname());
    }

    @Test
    void findByIdShouldReturnEmptyWhenPublisherDoesNotExist() {
        assertTrue(publisherService.findById(999L).isEmpty());
    }

    @Test
    void findAllShouldReturnAllPublishers() {
        assertEquals(2, publisherService.findAll().size());
    }

    @Test
    void saveShouldAddPublisher() {
        Publisher youssef = new Publisher(
                3, "Youssef", "Alaoui",
                "youssef@example.com", Category.BLOGUER);

        publisherService.save(youssef);

        assertTrue(publisherService.findById(3L).isPresent());
    }

    @Test
    void updateShouldModifyExistingPublisher() {
        Publisher updated = new Publisher(
                1, "Mohamed", "Architect",
                "updated@example.com", Category.INFLUENCER);

        publisherService.update(updated);

        assertEquals(
                "Architect",
                publisherService.findById(1L).orElseThrow().getLastname());
    }

    @Test
    void deleteShouldRemovePublisher() {
        publisherService.delete(1L);

        assertTrue(publisherService.findById(1L).isEmpty());
        assertEquals(1, publisherService.findAll().size());
    }

    @Test
    void getOffresShouldReturnEmptyListWhenPublisherDoesNotExist() {
        assertTrue(publisherService.getOffres(999L).isEmpty());
    }

    @Test
    void getOffresShouldReturnEmptyListWhenPublisherHasNoLinks() {
        assertTrue(publisherService.getOffres(1L).isEmpty());
    }

    @Test
    void getOffresShouldGroupLinksAndSumClicks() {
        linkService.save(new Link(
                1, "Nike", "https://www.nike.com", 10L, mohamed));
        linkService.save(new Link(
                2, "Nike", "https://www.nike.com/running", 15L, mohamed));
        linkService.save(new Link(
                3, "Adidas", "https://www.adidas.com", 7L, mohamed));

        List<Map<String, Object>> result = publisherService.getOffres(1L);

        assertEquals(2, result.size());

        Map<String, Object> nike = result.stream()
                .filter(offre -> offre.get("offre").equals("Nike"))
                .findFirst()
                .orElseThrow();

        assertEquals(2, nike.get("nombreliens"));
        assertEquals(25L, nike.get("clicks"));

        Map<String, Object> adidas = result.stream()
                .filter(offre -> offre.get("offre").equals("Adidas"))
                .findFirst()
                .orElseThrow();

        assertEquals(1, adidas.get("nombreliens"));
        assertEquals(7L, adidas.get("clicks"));
    }

    @Test
    void getOffresShouldNotIncludeLinksFromAnotherPublisher() {
        linkService.save(new Link(
                1, "Nike", "https://www.nike.com", 10L, mohamed));
        linkService.save(new Link(
                2, "Nike", "https://www.nike.com", 20L, sara));

        List<Map<String, Object>> result = publisherService.getOffres(1L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).get("clicks"));
    }

    @Test
    void getOffresShouldTreatNullClicksAsZero() {
        linkService.save(new Link(
                1, "Nike", "https://www.nike.com", null, mohamed));

        List<Map<String, Object>> result = publisherService.getOffres(1L);

        assertEquals(0L, result.get(0).get("clicks"));
    }
}