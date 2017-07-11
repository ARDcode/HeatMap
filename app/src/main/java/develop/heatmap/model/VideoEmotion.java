package develop.heatmap.model;

/**
 * Created by Aynur on 11.06.2017.
 */

public class VideoEmotion {
    private int id;
    private String name;
    private String url;
    private String json;

    public VideoEmotion() {
    }

    public VideoEmotion(String name, String url, String json) {
        this.name = name;
        this.url = url;
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
