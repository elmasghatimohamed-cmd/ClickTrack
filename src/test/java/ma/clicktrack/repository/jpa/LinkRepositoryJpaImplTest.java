package ma.clicktrack.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ma.clicktrack.model.Link;
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
class LinkRepositoryJpaImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Link> linkQuery;

    private LinkRepositoryJpaImpl repository;
    private Link link;
    private Publisher publisher;

    @BeforeEach
    void setUp() throws Exception {
        repository = new LinkRepositoryJpaImpl();

        Field field = LinkRepositoryJpaImpl.class
                .getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(repository, entityManager);

        publisher = new Publisher();
        publisher.setId(1);

        link = new Link(
                1,
                "Offre Java",
                "https://example.com/java",
                0L,
                publisher
        );
    }

    @Test
    void findByIdShouldReturnLinkWhenFound() {
        when(entityManager.find(Link.class, 1)).thenReturn(link);

        Optional<Link> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Offre Java", result.get().getOffre());
        verify(entityManager).find(Link.class, 1);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(entityManager.find(Link.class, 99)).thenReturn(null);

        Optional<Link> result = repository.findById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllShouldReturnLinks() {
        when(entityManager.createQuery(
                "SELECT l FROM Link l",
                Link.class
        )).thenReturn(linkQuery);

        when(linkQuery.getResultList()).thenReturn(List.of(link));

        List<Link> result = repository.findAll();

        assertEquals(1, result.size());
        assertEquals("Offre Java", result.get(0).getOffre());
    }

    @Test
    void findByPublisherShouldReturnPublisherLinks() {
        when(entityManager.createQuery(
                "SELECT l FROM Link l WHERE l.publisher.id = :publisherId",
                Link.class
        )).thenReturn(linkQuery);

        when(linkQuery.setParameter("publisherId", 1))
                .thenReturn(linkQuery);

        when(linkQuery.getResultList()).thenReturn(List.of(link));

        List<Link> result = repository.findByPublisher(publisher);

        assertEquals(1, result.size());
        verify(linkQuery).setParameter("publisherId", 1);
    }

    @Test
    void saveShouldPersistNewLink() {
        link.setId(null);

        repository.save(link);

        verify(entityManager).persist(link);
        verify(entityManager, never()).merge(link);
    }

    @Test
    void saveShouldMergeExistingLink() {
        repository.save(link);

        verify(entityManager).merge(link);
        verify(entityManager, never()).persist(link);
    }

    @Test
    void deleteShouldRemoveLinkWhenFound() {
        when(entityManager.find(Link.class, 1)).thenReturn(link);

        repository.delete(link);

        verify(entityManager).remove(link);
    }

    @Test
    void deleteShouldDoNothingWhenLinkNotFound() {
        when(entityManager.find(Link.class, 1)).thenReturn(null);

        repository.delete(link);

        verify(entityManager, never()).remove(any());
    }

    @Test
    void incrementClicksShouldIncrementExistingLink() {
        link.setClicks(4L);
        when(entityManager.find(Link.class, 1)).thenReturn(link);

        Optional<Link> result = repository.incrementClicks(1);

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().getClicks());
    }

    @Test
    void incrementClicksShouldReturnEmptyWhenLinkNotFound() {
        when(entityManager.find(Link.class, 99)).thenReturn(null);

        Optional<Link> result = repository.incrementClicks(99);

        assertTrue(result.isEmpty());
    }
}