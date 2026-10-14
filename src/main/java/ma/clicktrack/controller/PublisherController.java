
package ma.clicktrack.controller;

import ma.clicktrack.dto.CreateLinkRequest;
import ma.clicktrack.model.Link;
import ma.clicktrack.model.Publisher;
import ma.clicktrack.service.LinkService;
import ma.clicktrack.service.PublisherService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    private final LinkService linkService;

    public PublisherController(PublisherService publisherService, LinkService linkService) {
        this.publisherService = publisherService;
        this.linkService = linkService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Publisher> findAll(){
        return publisherService.findAll();
    }

    @GetMapping(value = "/{id}/offres", produces = MediaType.APPLICATION_JSON_VALUE)
    public List <Map<String,Object>> getOffres(@PathVariable("id") long id){
        return publisherService.getOffres(id);
    }

    @PostMapping(value = "/{id}/liens",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createLink(@PathVariable("id") Integer id,
            @RequestBody CreateLinkRequest request) {

        try {
            Link created = linkService.create(id, request.getOffre(), request.getUrlDestination());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(created);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}/liens", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Link>> getLiens(@PathVariable("id") Integer id) {

        return publisherService.findById(id.longValue())
                .map(publisher -> ResponseEntity.ok(linkService.findByPublisher(publisher)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
