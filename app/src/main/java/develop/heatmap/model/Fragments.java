package develop.heatmap.model;

import java.util.ArrayList;


public class Fragments {
    private ArrayList<Events> events;
    private String start;
    private String duration;
    private String interval;

    public ArrayList<Events> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Events> events) {
        this.events = events;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
