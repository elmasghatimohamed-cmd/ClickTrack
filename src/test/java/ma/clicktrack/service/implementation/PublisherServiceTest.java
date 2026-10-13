package ma.clicktrack.service.implementation;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class PublisherServiceTest {

    @Test
    void shouldInjectRepositoryIntoService() {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {

            PublisherService service =
                    context.getBean("publisherService", PublisherServiceImpl.class);

            Publisher publisher = new Publisher(
                    1,
                    "Mohamed",
                    "El Masghati",
                    "mohamed@example.com",
                    Category.YOUTUBER
            );

            service.save(publisher);

            assertTrue(service.findById(1).isPresent());
            assertEquals(1, service.findAll().size());
        }
    }
}
