package ma.clicktrack.repository.implementation;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PublisherRepositoryImplTest {

    private PublisherRepositoryImpl repository;
    private Publisher mohamed;
    private Publisher sara;

    @BeforeEach
    void setUp() {
        repository = new PublisherRepositoryImpl();

        mohamed = new Publisher(
                1, "Mohamed", "El Masghati",
                "mohamed@example.com", Category.INFLUENCER
        );

        sara = new Publisher(
                2, "Sara", "Amrani",
                "sara@example.com", Category.YOUTUBER
        );
    }

    @Test
    void findAllShouldReturnEmptyListInitially() {
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void saveShouldAddPublisher() {
        repository.save(mohamed);

        assertEquals(1, repository.findAll().size());
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void saveShouldNotAddDuplicateId() {
        repository.save(mohamed);
        repository.save(new Publisher(
                1, "Autre", "Nom",
                "autre@example.com", Category.BLOGUER
        ));

        assertEquals(1, repository.findAll().size());
        assertEquals(
                "Mohamed",
                repository.findById(1L).orElseThrow().getFirstname()
        );
    }

    @Test
    void findByIdShouldReturnPublisherWhenItExists() {
        repository.save(mohamed);

        Optional<Publisher> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Mohamed", result.get().getFirstname());
    }

    @Test
    void findByIdShouldReturnEmptyWhenPublisherDoesNotExist() {
        assertTrue(repository.findById(999L).isEmpty());
    }

    @Test
    void findAllShouldReturnAllPublishers() {
        repository.save(mohamed);
        repository.save(sara);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void findAllShouldReturnDefensiveCopy() {
        repository.save(mohamed);

        List<Publisher> result = repository.findAll();
        result.clear();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void updateShouldReplaceExistingPublisher() {
        repository.save(mohamed);

        Publisher updated = new Publisher(
                1, "Mohamed", "Architect",
                "updated@example.com", Category.INFLUENCER
        );

        repository.update(updated);

        assertEquals(
                "Architect",
                repository.findById(1L).orElseThrow().getLastname()
        );
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void updateShouldDoNothingWhenPublisherDoesNotExist() {
        repository.update(mohamed);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteShouldRemovePublisherById() {
        repository.save(mohamed);
        repository.save(sara);

        repository.delete(1L);

        assertTrue(repository.findById(1L).isEmpty());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void deleteShouldDoNothingWhenPublisherDoesNotExist() {
        repository.save(mohamed);

        repository.delete(999L);

        assertEquals(1, repository.findAll().size());
    }
}