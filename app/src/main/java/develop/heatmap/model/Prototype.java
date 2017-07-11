package develop.heatmap.model;

/**
 * Created by Aynur on 27.02.2017.
 */

public class Prototype {
    private int id;
    private String url;
    private String name;
    private String description;
    private String date_created;
    private String users;

    public Prototype() {
    }
    public Prototype(String url, String name, String description, String date_created, String users) {
        this.url = url;
        this.name = name;
        this.description = description;
        this.date_created = date_created;
        this.users = users;
    }
    public Prototype(int id, String url, String name, String description, String date_created, String users) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.description = description;
        this.date_created = date_created;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
