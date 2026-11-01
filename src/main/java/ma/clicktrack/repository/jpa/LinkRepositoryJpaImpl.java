
package ma.clicktrack.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.LinkRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class LinkRepositoryJpaImpl implements LinkRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Link> findAll() {
        return entityManager
                .createQuery("SELECT l FROM Link l", Link.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Link> findByPublisher(Publisher publisher) {
        return entityManager
                .createQuery(
                        "SELECT l FROM Link l WHERE l.publisher.id = :publisherId",
                        Link.class
                )
                .setParameter("publisherId", publisher.getId())
                .getResultList();
    }

    @Override
    public void save(Link link) {
        if (link.getId() == null) {
            entityManager.persist(link);
        } else {
            entityManager.merge(link);
        }
    }

    @Override
    public void delete(Link link) {
        Link managedLink = entityManager.find(Link.class, link.getId());

        if (managedLink != null) {
            entityManager.remove(managedLink);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Link> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Link.class, id));
    }

    @Override
    public Optional<Link> incrementClicks(Integer id) {
        Link link = entityManager.find(Link.class, id);

        if (link == null) {
            return Optional.empty();
        }

        long currentClicks = link.getClicks() == null ? 0L : link.getClicks();
        link.setClicks(currentClicks + 1);

        return Optional.of(link);
    }
}
