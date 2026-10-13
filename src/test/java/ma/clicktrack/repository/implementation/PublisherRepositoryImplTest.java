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
    private Publisher publisher;

    @BeforeEach
    void setUp() {
        repository = new PublisherRepositoryImpl();

        publisher = new Publisher(
                1,
                "Mohamed",
                "El Masghati",
                "mohamed@example.com",
                Category.YOUTUBER
        );
    }

    @Test
    void shouldSavePublisher() {
        repository.save(publisher);

        assertEquals(1, repository.findAll().size());
        assertTrue(repository.findById(1).isPresent());
    }

    @Test
    void shouldNotSaveDuplicatePublisher() {
        repository.save(publisher);
        repository.save(publisher);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldFindPublisherById() {
        repository.save(publisher);

        Optional<Publisher> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Mohamed", result.get().getFirstname());
    }

    @Test
    void shouldReturnEmptyWhenPublisherDoesNotExist() {
        assertTrue(repository.findById(99).isEmpty());
    }

    @Test
    void shouldReturnAllPublishers() {
        repository.save(publisher);

        Publisher secondPublisher = new Publisher(
                2,
                "Ali",
                "Amrani",
                "ali@example.com",
                Category.INFLUENCER
        );

        repository.save(secondPublisher);

        List<Publisher> publishers = repository.findAll();

        assertEquals(2, publishers.size());
    }

    @Test
    void shouldUpdateExistingPublisher() {
        repository.save(publisher);

        Publisher updatedPublisher = new Publisher(
                1,
                "Mohamed",
                "Updated",
                "updated@example.com",
                Category.BLOGUER
        );

        repository.update(updatedPublisher);

        Optional<Publisher> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().getLastname());
        assertEquals("updated@example.com", result.get().getEmail());
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldNotUpdateNonExistingPublisher() {
        repository.update(publisher);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void shouldDeletePublisher() {
        repository.save(publisher);

        repository.delete(1);

        assertTrue(repository.findAll().isEmpty());
        assertTrue(repository.findById(1).isEmpty());
    }

    @Test
    void shouldNotDeleteOtherPublishers() {
        repository.save(publisher);

        repository.delete(99);

        assertEquals(1, repository.findAll().size());
    }
}
