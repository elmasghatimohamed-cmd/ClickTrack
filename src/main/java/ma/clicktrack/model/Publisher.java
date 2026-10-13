package ma.clicktrack.model;

public class Publisher {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private Category categoty;

    public Publisher(int id, String firstname, String lastname, String email, Category categoty) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.categoty = categoty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Category getCategoty() {
        return categoty;
    }

    public void setCategoty(Category categoty) {
        this.categoty = categoty;
    }
}
