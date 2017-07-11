package develop.heatmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Emotions {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("timescale")
    @Expose
    private Integer timescale;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("framerate")
    @Expose
    private Double framerate;
    @SerializedName("rotation")
    @Expose
    private Integer rotation;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("fragments")
    @Expose
    private List<Fragment> fragments = null;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getTimescale() {
        return timescale;
    }

    public void setTimescale(Integer timescale) {
        this.timescale = timescale;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Double getFramerate() {
        return framerate;
    }

    public void setFramerate(Double framerate) {
        this.framerate = framerate;
    }

    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

}

