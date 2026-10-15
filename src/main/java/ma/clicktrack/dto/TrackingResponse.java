
package ma.clicktrack.dto;

public class TrackingResponse {

    private String urlRedirection;
    private String publisher;
    private String offre;
    private Long clicks;

    public TrackingResponse() {
    }

    public TrackingResponse(
            String urlRedirection,
            String publisher,
            String offre,
            Long clicks) {
        this.urlRedirection = urlRedirection;
        this.publisher = publisher;
        this.offre = offre;
        this.clicks = clicks;
    }

    public String getUrlRedirection() {
        return urlRedirection;
    }

    public void setUrlRedirection(String urlRedirection) {
        this.urlRedirection = urlRedirection;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOffre() {
        return offre;
    }

    public void setOffre(String offre) {
        this.offre = offre;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }
}
