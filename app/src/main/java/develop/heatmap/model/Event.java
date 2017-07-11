
package develop.heatmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("windowFaceDistribution")
    @Expose
    private WindowFaceDistribution windowFaceDistribution;
    @SerializedName("windowMeanScores")
    @Expose
    private WindowMeanScores windowMeanScores;

    public WindowFaceDistribution getWindowFaceDistribution() {
        return windowFaceDistribution;
    }

    public void setWindowFaceDistribution(WindowFaceDistribution windowFaceDistribution) {
        this.windowFaceDistribution = windowFaceDistribution;
    }

    public WindowMeanScores getWindowMeanScores() {
        return windowMeanScores;
    }

    public void setWindowMeanScores(WindowMeanScores windowMeanScores) {
        this.windowMeanScores = windowMeanScores;
    }

}
