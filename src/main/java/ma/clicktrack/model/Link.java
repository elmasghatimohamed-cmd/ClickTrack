package ma.clicktrack.model;

import jakarta.persistence.*;

@Entity
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String offre;
    @Column(nullable = false)
    private String urlDestination;
    @Column(nullable = false)
    private Long clicks = 0L;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    public Link() {
    }
    public Link(Integer id, String offre, String urlDestination, Long clicks, Publisher publisher) {
        this.id = id;
        this.offre = offre;
        this.urlDestination = urlDestination;
        this.clicks = clicks;
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public String getUrlDestination() {
        return urlDestination;
    }

    public void setUrlDestination(String urlDestination) {
        this.urlDestination = urlDestination;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
