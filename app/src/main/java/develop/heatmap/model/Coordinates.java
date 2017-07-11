package develop.heatmap.model;

/**
 * Created by Aynur on 11.06.2017.
 */

public class Coordinates {
    String url;
    String coordinates;
    String user_id;

    public Coordinates() {
    }

    public Coordinates(String url, String coordinates, String user_id) {
        this.url = url;
        this.coordinates = coordinates;
        this.user_id = user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
