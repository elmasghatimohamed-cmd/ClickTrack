package ma.clicktrack.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ma.clicktrack.model.Publisher;

import java.util.List;
import java.util.Optional;

public class PublisherRepositoryJpaImpl implements ma.clicktrack.repository.PublisherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Publisher> findById(long id) {
        return Optional.ofNullable(
                entityManager.find(Publisher.class, (int) id)
        );
    }

    @Override
    public List<Publisher> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Publisher p", Publisher.class)
                .getResultList();
    }

    @Override
    public void save(Publisher publisher) {
        entityManager.persist(publisher);
    }

    @Override
    public void update(Publisher publisher) {
        entityManager.merge(publisher);
    }

    @Override
    public void delete(long id) {
        Publisher publisher = entityManager.find(Publisher.class, (int) id);

        if (publisher != null) {
            entityManager.remove(publisher);
        }
    }
}
