package develop.heatmap.model;

public class PrototypeUser {
    private int id;
    private String bio;
    private String name;
    private String date_created;
    private String last_addrec;
    private int prototype_id;

    public PrototypeUser() {
    }

    public PrototypeUser(int id, String bio, String name, String date_created, String last_addrec) {
        this.id = id;
        this.bio = bio;
        this.name = name;
        this.date_created = date_created;
        this.last_addrec = last_addrec;
    }

    public PrototypeUser(String bio, String name, String date_created, String last_addrec) {
        this.bio = bio;
        this.name = name;
        this.date_created = date_created;
        this.last_addrec = last_addrec;
    }
    public PrototypeUser(String bio, String name, String date_created, String last_addrec, int prototype_id) {
        this.bio = bio;
        this.name = name;
        this.date_created = date_created;
        this.last_addrec = last_addrec;
        this.prototype_id = prototype_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getLast_addrec() {
        return last_addrec;
    }

    public void setLast_addrec(String last_addrec) {
        this.last_addrec = last_addrec;
    }

    public int getPrototype_id() {
        return prototype_id;
    }

    public void setPrototype_id(int prototype_id) {
        this.prototype_id = prototype_id;
    }
}
