package ma.clicktrack.dto;

import ma.clicktrack.model.Publisher;

public record PublisherResponse(
        int id,
        String nom,
        String prenom,
        String email,
        String category
) {
    public static PublisherResponse from(Publisher publisher) {
        return new PublisherResponse(
                publisher.getId(),
                publisher.getLastname(),
                publisher.getFirstname(),
                publisher.getEmail(),
                publisher.getCategory().name()
        );
    }
}