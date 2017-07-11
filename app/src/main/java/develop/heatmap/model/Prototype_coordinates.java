package develop.heatmap.model;

/**
 * Created by Aynur on 19.01.2017.
 */

public class Prototype_coordinates {
    private int id;
    private String url;
    private String coodinates;
    private String user_id;


    public Prototype_coordinates() {
    }

    public Prototype_coordinates(String coodinates) {
        this.coodinates = coodinates;
    }

    public Prototype_coordinates(String url, String name, String coodinates) {
        this.url = url;
        this.user_id = name;
        this.coodinates = coodinates;
    }

    public Prototype_coordinates(int id, String url, String name, String coodinates) {
        this.id = id;
        this.url = url;
        this.user_id = name;
        this.coodinates = coodinates;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoodinates() {
        return coodinates;
    }

    public void setCoodinates(String coodinates) {
        this.coodinates = coodinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
