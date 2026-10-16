package ma.clicktrack.model;

public class Link {

    private Integer id;
    private String offre;
    private String urlDestination;
    private Long clicks;
    private Publisher publisher;

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
