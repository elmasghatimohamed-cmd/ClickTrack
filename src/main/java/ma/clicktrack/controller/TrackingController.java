
package ma.clicktrack.controller;

import ma.clicktrack.dto.TrackingResponse;
import ma.clicktrack.model.Link;
import ma.clicktrack.service.LinkService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackingController {

    private final LinkService linkService;

    public TrackingController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(
            value = "/t/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TrackingResponse> clickTrack(@PathVariable("id") Integer id) {

        return linkService.clickTrack(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private TrackingResponse toResponse(Link link) {
        String nomComplet = link.getPublisher().getFirstname()
                + " "
                + link.getPublisher().getLastname();

        return new TrackingResponse(
                link.getUrlDestination(),
                nomComplet,
                link.getOffre(),
                link.getClicks()
        );
    }
}
