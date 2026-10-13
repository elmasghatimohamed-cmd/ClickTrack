package ma.clicktrack.controller;


import ma.clicktrack.model.Publisher;
import ma.clicktrack.service.PublisherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Publisher> findAll(){
        return publisherService.findAll();
    }
}


