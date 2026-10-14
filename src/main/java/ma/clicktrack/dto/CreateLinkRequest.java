
package ma.clicktrack.dto;

public class CreateLinkRequest {

    private String offre;
    private String urlDestination;

    public CreateLinkRequest() {
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
}
