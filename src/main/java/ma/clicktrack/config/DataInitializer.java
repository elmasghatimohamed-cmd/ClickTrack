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
                publisherService.save(new Publisher(
                                1, "Mohamed", "El Masghati",
                                "mohamed@example.com", Category.INFLUENCER));

                publisherService.save(new Publisher(
                                2, "Sara", "Amrani",
                                "sara@example.com", Category.YOUTUBER));

                publisherService.save(new Publisher(
                                3, "Youssef", "Alaoui",
                                "youssef@example.com", Category.BLOGUER));

        }
}