package ma.clicktrack.repository.implementation;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkRepositoryImplTest {

    private LinkRepositoryImpl repository;
    private Publisher mohamed;
    private Publisher sara;
    private Link nike;
    private Link adidas;

    @BeforeEach
    void setUp() {
        repository = new LinkRepositoryImpl();

        mohamed = new Publisher(
                1, "Mohamed", "El Masghati",
                "mohamed@example.com", Category.INFLUENCER
        );

        sara = new Publisher(
                2, "Sara", "Amrani",
                "sara@example.com", Category.YOUTUBER
        );

        nike = new Link(
                1, "Nike", "https://www.nike.com", 10L, mohamed
        );

        adidas = new Link(
                2, "Adidas", "https://www.adidas.com", 7L, mohamed
        );
    }

    @Test
    void findAllShouldReturnEmptyListInitially() {
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void saveShouldAddLink() {
        repository.save(nike);

        assertEquals(1, repository.findAll().size());
        assertTrue(repository.findById(1).isPresent());
    }

    @Test
    void saveShouldNotAddDuplicateId() {
        repository.save(nike);
        repository.save(new Link(
                1, "Autre offre", "https://example.com", 5L, mohamed
        ));

        assertEquals(1, repository.findAll().size());
        assertEquals(
                "Nike",
                repository.findById(1).orElseThrow().getOffre()
        );
    }

    @Test
    void findByIdShouldReturnEmptyWhenLinkDoesNotExist() {
        assertTrue(repository.findById(999).isEmpty());
    }

    @Test
    void findByPublisherShouldReturnOnlyPublisherLinks() {
        repository.save(nike);
        repository.save(adidas);
        repository.save(new Link(
                3, "Samsung", "https://www.samsung.com", 20L, sara
        ));

        List<Link> result = repository.findByPublisher(mohamed);

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .allMatch(link -> link.getPublisher() == mohamed));
    }

    @Test
    void findByPublisherShouldReturnEmptyListWhenNoLinksExist() {
        assertTrue(repository.findByPublisher(mohamed).isEmpty());
    }

    @Test
    void findAllShouldReturnDefensiveCopy() {
        repository.save(nike);

        List<Link> result = repository.findAll();
        result.clear();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteShouldRemoveLinkById() {
        repository.save(nike);
        repository.save(adidas);

        repository.delete(nike);

        assertTrue(repository.findById(1).isEmpty());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void generateIdShouldReturnOneWhenRepositoryIsEmpty() {
        assertEquals(1, repository.generateId());
    }

    @Test
    void generateIdShouldReturnNextIdAfterHighestExistingId() {
        repository.save(nike);
        repository.save(adidas);

        assertEquals(3, repository.generateId());
    }

    @Test
    void generateIdShouldFollowHighestIdNotNumberOfLinks() {
        repository.save(nike);
        repository.save(new Link(
                10, "Adidas", "https://www.adidas.com", 7L, mohamed
        ));

        assertEquals(11, repository.generateId());
    }
}