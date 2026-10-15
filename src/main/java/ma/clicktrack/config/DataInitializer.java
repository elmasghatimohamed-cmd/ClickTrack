package ma.clicktrack.config;

import ma.clicktrack.model.Category;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.service.PublisherService;

public class DataInitializer {

        private final PublisherService publisherService;

        public DataInitializer(PublisherService publisherService) {
                this.publisherService = publisherService;
        }

        public void init() {
                if (publisherService.findAll().isEmpty()) {
                        publisherService.save(new Publisher(
                                null, "Mohamed", "El Masghati",
                                "mohamed@example.com", Category.INFLUENCER));

                        publisherService.save(new Publisher(
                                null, "Sara", "Amrani",
                                "sara@example.com", Category.YOUTUBER));

                        publisherService.save(new Publisher(
                                null, "Youssef", "Alaoui",
                                "youssef@example.com", Category.BLOGUER));
                }
        }
}