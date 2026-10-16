package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.repository.LinkRepository;
import ma.clicktrack.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private PublisherRepository publisherRepository;

    private LinkServiceImpl linkService;
    private Publisher mohamed;

    @BeforeEach
    void setUp() {
        linkService = new LinkServiceImpl(
                linkRepository,
                publisherRepository
        );

        mohamed = new Publisher(
                1,
                "Mohamed",
                "El Masghati",
                "mohamed@example.com",
                Category.INFLUENCER
        );
    }

    @Test
    void findAllShouldReturnAllLinks() {
        List<Link> links = List.of(
                new Link(1, "Nike", "https://www.nike.com", 10L, mohamed),
                new Link(2, "Adidas", "https://www.adidas.com", 5L, mohamed)
        );

        when(linkRepository.findAll()).thenReturn(links);

        assertEquals(2, linkService.findAll().size());
        verify(linkRepository).findAll();
    }

    @Test
    void findByPublisherShouldReturnPublisherLinks() {
        List<Link> links = List.of(
                new Link(1, "Nike", "https://www.nike.com", 10L, mohamed)
        );

        when(linkRepository.findByPublisher(mohamed)).thenReturn(links);

        List<Link> result = linkService.findByPublisher(mohamed);

        assertEquals(1, result.size());
        assertEquals("Nike", result.get(0).getOffre());
        verify(linkRepository).findByPublisher(mohamed);
    }

    @Test
    void saveShouldDelegateToRepository() {
        Link link = new Link(
                1, "Nike", "https://www.nike.com", 0L, mohamed
        );

        linkService.save(link);

        verify(linkRepository).save(link);
    }

    @Test
    void deleteShouldDelegateToRepository() {
        Link link = new Link(
                1, "Nike", "https://www.nike.com", 0L, mohamed
        );

        linkService.delete(link);

        verify(linkRepository).delete(link);
    }

    @Test
    void createShouldCreateLinkWithoutAssigningId() {
        when(publisherRepository.findById(1L))
                .thenReturn(Optional.of(mohamed));

        Link created = linkService.create(
                1, "Nike", "https://www.nike.com"
        );

        assertNull(created.getId());
        assertEquals("Nike", created.getOffre());
        assertEquals("https://www.nike.com", created.getUrlDestination());
        assertEquals(0L, created.getClicks());
        assertSame(mohamed, created.getPublisher());

        verify(linkRepository).save(created);
    }

    @Test
    void createShouldThrowExceptionWhenPublisherDoesNotExist() {
        when(publisherRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> linkService.create(
                        999, "Nike", "https://www.nike.com"
                )
        );

        verify(linkRepository, never()).save(any(Link.class));
    }

    @Test
    void clickTrackShouldDelegateToRepository() {
        Link link = new Link(
                1, "Nike", "https://www.nike.com", 1L, mohamed
        );

        when(linkRepository.incrementClicks(1))
                .thenReturn(Optional.of(link));

        Optional<Link> result = linkService.clickTrack(1);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getClicks());
        verify(linkRepository).incrementClicks(1);
    }

    @Test
    void clickTrackShouldReturnEmptyWhenLinkDoesNotExist() {
        when(linkRepository.incrementClicks(999))
                .thenReturn(Optional.empty());

        assertTrue(linkService.clickTrack(999).isEmpty());
    }
}