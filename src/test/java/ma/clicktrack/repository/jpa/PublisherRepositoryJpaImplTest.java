package ma.clicktrack.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ma.clicktrack.model.Category;
import ma.clicktrack.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherRepositoryJpaImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Publisher> publisherQuery;

    private PublisherRepositoryJpaImpl repository;

    private Publisher publisher;

    @BeforeEach
    void setUp() throws Exception {
        repository = new PublisherRepositoryJpaImpl();

        Field field = PublisherRepositoryJpaImpl.class
                .getDeclaredField("entityManager");

        field.setAccessible(true);
        field.set(repository, entityManager);

        publisher = new Publisher(
                1,
                "Mohamed",
                "El Masghati",
                "mohamed@example.com",
                Category.YOUTUBER
        );
    }

    @Test
    void findByIdShouldReturnPublisherWhenFound() {
        when(entityManager.find(Publisher.class, 1))
                .thenReturn(publisher);

        Optional<Publisher> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Mohamed", result.get().getFirstname());

        verify(entityManager).find(Publisher.class, 1);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(entityManager.find(Publisher.class, 99))
                .thenReturn(null);

        Optional<Publisher> result = repository.findById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllShouldReturnPublishers() {
        when(entityManager.createQuery(
                "SELECT p FROM Publisher p",
                Publisher.class
        )).thenReturn(publisherQuery);

        when(publisherQuery.getResultList())
                .thenReturn(List.of(publisher));

        List<Publisher> result = repository.findAll();

        assertEquals(1, result.size());
        assertEquals("Mohamed", result.get(0).getFirstname());
    }

    @Test
    void saveShouldPersistPublisher() {
        repository.save(publisher);

        verify(entityManager).persist(publisher);
    }

    @Test
    void updateShouldMergePublisher() {
        repository.update(publisher);

        verify(entityManager).merge(publisher);
    }

    @Test
    void deleteShouldRemovePublisherWhenFound() {
        when(entityManager.find(Publisher.class, 1))
                .thenReturn(publisher);

        repository.delete(1);

        verify(entityManager).remove(publisher);
    }

    @Test
    void deleteShouldDoNothingWhenPublisherNotFound() {
        when(entityManager.find(Publisher.class, 99))
                .thenReturn(null);

        repository.delete(99);

        verify(entityManager, never()).remove(any());
    }
}