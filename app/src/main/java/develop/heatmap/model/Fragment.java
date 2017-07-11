
package develop.heatmap.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fragment {

    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("interval")
    @Expose
    private Integer interval;
    @SerializedName("events")
    @Expose
    private List<List<Event>> events = null;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public List<List<Event>> getEvents() {
        return events;
    }

    public void setEvents(List<List<Event>> events) {
        this.events = events;
    }

}
